package br.com.gumillanf.cooperativa.commons.criteria;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class SpecificationBuilder<E> {

    private List<SearchCriteria> params;

    private List<Specification> specifications;

    private SpecificationBuilder() {
        params = new ArrayList<>();
        specifications = new ArrayList<>();
    }

    public static <E> SpecificationBuilder<E> builder() {
        return new SpecificationBuilder<E>();
    }

    public final SpecificationBuilder and(final String key, final SearchOperation operation,
                                          final Comparable... arguments) {
        params.add(SearchCriteria.and(key, operation, arguments));
        return this;
    }

    public final SpecificationBuilder or(final String key, final SearchOperation operation,
                                         final Comparable... arguments) {
        params.add(SearchCriteria.or(key, operation, arguments));
        return this;
    }

    public final SpecificationBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

    public final SpecificationBuilder<E> with(Specification<E> specification) {
        specifications.add(specification);
        return this;
    }

    public Specification<E> build() {
        Specification<E> result = null;
        if (!params.isEmpty()) {

            for (int index = 0; index < params.size(); index++) {
                final SearchCriteria searchCriteria = params.get(index);

                if (searchCriteria.getArguments().stream().anyMatch(a -> isNull(a))
                        && searchCriteria.isIgnoreIfNull()) {
                    continue;
                }

                if (isNull(result)) {
                    result = creteSpecification(searchCriteria);
                }
                else {
                    result = searchCriteria.isOrOperation() ? result.or(creteSpecification(searchCriteria))
                            : result.and(creteSpecification(searchCriteria));
                }
            }
        }
        if (!specifications.isEmpty()) {
            int index = 0;
            if (isNull(result)) {
                result = specifications.get(index++);
            }
            for (; index < specifications.size(); ++index) {
                result = result.and(specifications.get(index));
            }
        }
        return result;
    }

    private Specification<E> creteSpecification(final SearchCriteria criteria) {
        return (Specification<E>) (root, criteriaQuery, criteriaBuilder) -> criteria.getOperation()
                .build(criteriaBuilder, root, criteria.getKey(), criteria.getArguments());
    }

}
