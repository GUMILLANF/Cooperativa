package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.gumillanf.cooperativa.agenda.AgendaSpecification.*;
import static br.com.gumillanf.cooperativa.agenda.AgendaStatus.*;

@Service
@AllArgsConstructor
public class AgendaQuery {

    private final AgendaRepository agendaRepository;

    private final AppMessageSource messageSource;

    public Agenda findOne(Long id) throws ResourceNotFoundException {
        return agendaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("agenda.not-found",
                        new Object[] { id })));
    }

    public List<Agenda> findAll(Specification<Agenda> specification) {
        return agendaRepository.findAll(specification);
    }

    public List<Agenda> findToFinish() {
        return agendaRepository.findAll(agendaStatus(VOTING).and(agendaEndDateLessNow(LocalDateTime.now())));
    }

}
