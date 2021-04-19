package br.com.gumillanf.cooperativa.result.web;

import br.com.gumillanf.cooperativa.result.Result;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static javax.swing.text.html.FormSubmitEvent.MethodType.GET;

@Component
public class ResultAssemblerSupport extends RepresentationModelAssemblerSupport<Result, Result> {

    private static final String FINAL_RESULT = "final-result";

    ResultAssemblerSupport() {
        super(ResultRestService.class, Result.class);
    }

    @SneakyThrows
    @Override
    public Result toModel(Result resource) {
        resource.add(linkTo(methodOn(ResultRestService.class).findByAgendaId(resource.getAgendaId())).withSelfRel().withType(GET.name()));
        resource.add(linkTo(methodOn(ResultRestService.class).findByFinalResult(resource.getFinalResult())).withRel(FINAL_RESULT).withType(GET.name()));
        return resource;
    }
}
