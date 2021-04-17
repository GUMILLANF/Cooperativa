package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import org.springframework.data.jpa.domain.Specification;

public class VoteSpecification {

    public static Specification voteAgendaId(final Agenda agenda) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Vote_.ID).get(VoteId_.AGENDA), agenda);
    }

    public static Specification voteAssocietedCpf(final String associetedCpf) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Vote_.ID).get(VoteId_.ASSOCIETED_CPF), associetedCpf);
    }

    public static Specification voteResponse(final VoteResponse voteResponse) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Vote_.RESPONSE), voteResponse);
    }

}
