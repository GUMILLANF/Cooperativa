package br.com.gumillanf.cooperativa.commons.criteria;

import br.com.gumillanf.cooperativa.commons.hateoas.AppPagedResourcesAssembler;
import br.com.gumillanf.cooperativa.commons.uitls.AppDataUtils;
import br.com.gumillanf.cooperativa.commons.uitls.AppValidationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpQueryBuilder<E, R extends PagingAndSortingRepository<E, ?> & JpaSpecificationExecutor<E>> {

    private final R repository;

    private final Class<E> eClass;

    private Specification<E> filters;

    private Sort sort = Sort.unsorted();

    public static <E, R extends PagingAndSortingRepository<E, ?> & JpaSpecificationExecutor<E>> HttpQueryBuilder<E, R> builder(
            R repository, Class<E> eClass) {
        return new HttpQueryBuilder(repository, eClass);
    }

    public List<E> findAll(int page, int limit) {
        return new LinkedList<>(repository.findAll(filters, PageRequest.of(page, limit, sort)).getContent());
    }

    public <R extends RepresentationModel<?>> PagedModel<R> findAll(int page, int limit,
                                                                    AppPagedResourcesAssembler<E> pagedResourcesAssembler,
                                                                    RepresentationModelAssemblerSupport<E, R> assemblerSupport) {
        return pagedResourcesAssembler.toResource(repository.findAll(filters, PageRequest.of(page, limit, sort)),
                assemblerSupport, eClass);
    }

    public <R extends RepresentationModel<?>> PagedModel<R> findAll(int page, int limit,
                                                                    AppPagedResourcesAssembler<E> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toResource(repository.findAll(filters, PageRequest.of(page, limit, sort)),
                eClass);
    }

    public List<E> findAll() {
        return new LinkedList<E>(repository.findAll(filters));
    }

    public HttpQueryBuilder<E, R> filterBy(List<String> listFilters) {
        Optional<Specification<E>> opFilters = parse(listFilters);
        if (opFilters.isPresent()) {
            if (filters == null) {
                filters = Specification.where(opFilters.get());
            }
            else {
                filters = filters.and(opFilters.get());
            }
        }
        return this;
    }

    public HttpQueryBuilder<E, R> with(Specification<E> specification) throws Exception {
        if (isNull(filters))
            throw new Exception("with não pode ser chamado antes de filterBy");
        filters.and(specification);
        return this;
    }

    public HttpQueryBuilder<E, R> sortBy(String orderBy, String orderDir) {
        if (nonNull(orderBy) && !orderBy.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromOptionalString(orderDir).orElse(Sort.Direction.ASC), orderBy);
        }
        return this;
    }

    public <E> Optional<Specification<E>> parse(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return Optional.empty();
        }
        List<Specification> criterias = mapSpecifications(filters);
        if (criterias.isEmpty()) {
            return Optional.empty();
        }

        Specification<E> root = Specification.where(criterias.get(0));
        for (int index = 1; index < criterias.size(); index++) {
            root = Specification.where(root).and(criterias.get(index));
        }
        return Optional.of(root);
    }

    private <E> List<Specification> mapSpecifications(List<String> filters) {
        return filters.stream().map(str -> {
            for (SearchOperation op : SearchOperation.values()) {
                int index = str.indexOf(op.getOperationName());
                if (index > 0) {
                    String key = str.substring(0, index);
                    String value = str.substring(index + op.getOperationName().length());
                    List<Comparable> parsedList = parseList(key, value);
                    return nonNull(parsedList)
                            ? (Specification<E>) (root, query, cb) -> op.build(cb, root, key, parsedList) : null;
                }
            }
            return null;
        }).filter(s -> s != null).collect(Collectors.toList());
    }

    private List<Comparable> parseList(final String key, String value) {
        if (!AppValidationUtils.isHttpQueryValid(value))
            return null;

        String[] keys = key.split("\\.");

        String typeName = null;
        try {
            Class aClass = (Class) eClass;
            for (int i = 0; i < keys.length; i++) {
                typeName = aClass.getDeclaredField(keys[i]).getType().getName();
            }
            String finalTypeName = typeName;
            return Arrays.stream(value.split(",")).map(v -> parse(finalTypeName, v)).collect(Collectors.toList());

        }
        catch (Exception e) {
            log.error("[key={}, value={}, type={}, exception={}]", key, value, typeName, e.getMessage());
            return null;
        }
    }

    private Comparable parse(String typeName, String value) {
        if (typeName.toLowerCase().contains("boolean")) {
            return Boolean.valueOf(value);
        }
        else if (typeName.toLowerCase().contains("date")) {
            return AppDataUtils.parseDate(value);
        }
        else if (typeName.toLowerCase().contains("bigdecimal")) {
            return new BigDecimal(value);
        }
        else if (typeName.toLowerCase().contains("int")) {
            return Integer.valueOf(value);
        }
        else if (typeName.toLowerCase().contains("string")) {
            return value;
        }
        return value;
    }

}
