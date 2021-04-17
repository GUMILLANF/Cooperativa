package br.com.gumillanf.cooperativa.commons.hateoas;

import org.springframework.data.domain.Page;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.util.UriComponents;

public class AppPagedResourcesAssembler<T> extends PagedResourcesAssembler<T> {

    public AppPagedResourcesAssembler(HateoasPageableHandlerMethodArgumentResolver resolver, UriComponents baseUri) {
        super(resolver, baseUri);
    }

    public <R extends RepresentationModel<?>> PagedModel<R> toResource(Page<T> page,
                                                                       RepresentationModelAssembler<T, R> assembler, Class<T> domain) {
        PagedModel<?> resources;
        if (page.isEmpty()) {
            resources = toEmptyModel(page, domain);
        }
        else {
            resources = toModel(page, assembler);
        }

        return (PagedModel<R>) resources;
    }

    public <R extends RepresentationModel<?>> PagedModel<R> toResource(Page<T> page, Class<T> domain) {
        PagedModel<?> resources;
        if (page.isEmpty()) {
            resources = toEmptyModel(page, domain);
        }
        else {
            resources = toModel(page);
        }
        return (PagedModel<R>) resources;
    }

}
