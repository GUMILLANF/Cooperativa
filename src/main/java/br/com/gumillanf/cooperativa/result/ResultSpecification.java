package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.agenda.Agenda_;
import org.springframework.data.jpa.domain.Specification;

public class ResultSpecification {

    public static Specification resultAgendaId(final Long agendaId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Result_.AGENDA).get(Agenda_.ID), agendaId);
    }

    public static Specification resultFinalResult(final FinalResult finalResult) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Result_.FINAL_RESULT), finalResult);
    }

}
