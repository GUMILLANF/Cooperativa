package br.com.gumillanf.cooperativa.agenda;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AgendaSpecification {

    private static final String like = "%";

    public static Specification agendaId(final Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Agenda_.ID), id);
    }

    public static Specification agendaDescription(final String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Agenda_.DESCRIPTION), like.concat(description).concat(like));
    }

    public static Specification agendaStatus(final AgendaStatus status) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Agenda_.STATUS), status);
    }

    public static Specification agendaEndDateLessNow(final LocalDateTime endDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(Agenda_.END_DATE_AGENDA), endDate);
    }

}
