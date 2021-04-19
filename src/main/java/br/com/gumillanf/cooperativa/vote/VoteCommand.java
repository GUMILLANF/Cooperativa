package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.agenda.AgendaQuery;
import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.commons.core.AppUtils;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.config.exception.InvalidCpfException;
import br.com.gumillanf.cooperativa.vote.web.VoteResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gumillanf.cooperativa.vote.VoteSpecification.*;
import static br.com.gumillanf.cooperativa.vote.Vote.of;

@Service
@AllArgsConstructor
@Transactional
public class VoteCommand {

    private final VoteRepository voteRepository;

    private final VoteQuery voteQuery;

    private final AgendaQuery agendaQuery;

    private final AppMessageSource messageSource;

    private final AppUtils appUtils;

    public Vote create(VoteResource voteResource) throws Throwable {
        Agenda agenda = agendaQuery.findOne(voteResource.getAgendaId());
        Vote vote = of(voteResource, agenda);
        validations(vote, agenda);
        return voteRepository.save(vote);
    }

    private void validations(Vote vote, Agenda agenda) throws ActionNotAllowedException, InvalidCpfException {
        if (!agenda.isOpen()) {
            throw new ActionNotAllowedException(messageSource.getMessage("agenda.closed",
                    new Object[] { vote.getId() }));
        }
        if (voteQuery.findOne(voteAgendaId(vote.getId().getAgenda()).and(voteAssocietedCpf(vote.getId().getAssocietedCpf()))).isPresent()) {
            throw new ActionNotAllowedException(messageSource.getMessage("vote.cpf-already-voted",
                    new Object[] { vote.getId().getAssocietedCpf() }));
        }
        if (!appUtils.validateCpf(vote.getId().getAssocietedCpf())) {
            throw new ActionNotAllowedException(messageSource.getMessage("vote.cpf-not-allowed",
                    new Object[] { vote.getId().getAssocietedCpf() }));
        }
    }



}
