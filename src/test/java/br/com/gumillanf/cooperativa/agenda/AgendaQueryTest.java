package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static br.com.gumillanf.cooperativa.agenda.AgendaSpecification.agendaDescription;
import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class AgendaQueryTest extends TestSupport {

    private static final String DESCRIPTION = "TESTE";

    private static final Long ID = 10L;

    @Mock
    private AgendaRepository repository;

    @Mock
    private AppMessageSource messageSource;

    private AgendaQuery agendaQuery;

    @Override
    public void init() {
        agendaQuery = new AgendaQuery(repository, messageSource);
    }

    @Test
    public void should_return_agenda_by_id() throws ResourceNotFoundException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        when(repository.findById(agenda.getId())).thenReturn(of(agenda));

        assertEquals(agenda, agendaQuery.findOne(agenda.getId()));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void find_one_should_throw_resource_not_found_exception_when_agenda_is_not_found() throws ResourceNotFoundException {
        agendaQuery.findOne(ID);
    }

    @Test
    public void should_return_all_specification() {
        List<Agenda> agendaList = Fixture.from(Agenda.class).gimme(5, VALID.name());
        when(repository.findAll(any(Specification.class))).thenReturn(agendaList);

        assertEquals(agendaList, agendaQuery.findAll(agendaDescription(DESCRIPTION)));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_all_finishi() {
        List<Agenda> agendaList = Fixture.from(Agenda.class).gimme(5, VALID.name());
        when(repository.findAll(any(Specification.class))).thenReturn(agendaList);

        assertEquals(agendaList, agendaQuery.findToFinish());

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

}
