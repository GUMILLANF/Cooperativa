package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.result.ResultCommand;
import br.com.gumillanf.cooperativa.vote.VoteQuery;
import br.com.gumillanf.cooperativa.vote.VoteSpecification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.gumillanf.cooperativa.commons.uitls.AppBeanUtils.copyNonNullProperties;
import static br.com.gumillanf.cooperativa.agenda.AgendaStatus.WAITING;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AgendaCommand {

    private final AgendaRepository agendaRepository;

    private final AgendaQuery agendaQuery;

    private final VoteQuery voteQuery;

    private final ResultCommand resultCommand;

    private final AppMessageSource messageSource;

    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public void update(Long id, Agenda agenda) throws ResourceNotFoundException {
        Agenda savedAgenda = findById(id);
        copyNonNullProperties(agenda, savedAgenda);
        agendaRepository.save(savedAgenda);

    }

    public void start(Long id, Integer minutesToEnd) throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda agenda = findById(id);
        if (!WAITING.equals(agenda.getStatus())) {
            throw new ActionNotAllowedException(messageSource.getMessage("agenda.start-not-allowed",
                    new Object[] { agenda.getId(), agenda.getStatus().name() }));
        }
        agendaRepository.save(agenda.start(minutesToEnd));
    }

    public void finish() {
        List<Agenda> agendaList = agendaQuery.findToFinish().stream().map(Agenda::finish).collect(Collectors.toList());
        agendaList.forEach(a -> resultCommand.create(a, voteQuery.findAll(VoteSpecification.voteAgendaId(a))));
        agendaRepository.saveAll(agendaList);
    }

    public void delete(Long id) throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda savedAgenda = findById(id);

        if (!WAITING.equals(savedAgenda.getStatus())) {
            throw new ActionNotAllowedException(messageSource.getMessage("agenda.deletion-not-allowed",
                    new Object[] { savedAgenda.getId(), savedAgenda.getStatus().name() }));
        }

        agendaRepository.delete(savedAgenda);
    }

    private Agenda findById(Long id) throws ResourceNotFoundException {
        return agendaQuery.findOne(id);
    }

}