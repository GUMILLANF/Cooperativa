package br.com.gumillanf.cooperativa.agenda.web;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.agenda.AgendaCommand;
import br.com.gumillanf.cooperativa.agenda.AgendaQuery;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class AgendaRestServiceTest extends TestSupport {

    private static final Long ID = 10L;

    private static final Integer MINUTES_TO_END = 1;

    private static final String DESCRIPTION = "TESTE";

    @Mock
    private AgendaCommand agendaCommand;

    @Mock
    private AgendaQuery agendaQuery;

    private AgendaRestService agendaRestService;

    @Override
    public void init() {
        agendaRestService = new AgendaRestService(agendaCommand, agendaQuery);
    }

    @Test
    public void should_create_agenda() {
        AgendaResource agendaResource = Fixture.from(AgendaResource.class).gimme(VALID.name());
        Agenda agenda = Agenda.of(agendaResource.getDescription());
        when(agendaCommand.save(agenda)).thenReturn(agenda);

        assertEquals(agenda, agendaRestService.create(agendaResource));

        InOrder inOrder = inOrder(agendaCommand);
        inOrder.verify(agendaCommand, times(1)).save(agenda);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_update_agenda() throws ResourceNotFoundException {
        AgendaResource agendaResource = Fixture.from(AgendaResource.class).gimme(VALID.name());
        Agenda agenda = Agenda.of(agendaResource.getDescription());

        agendaRestService.update(ID, agendaResource);

        InOrder inOrder = inOrder(agendaCommand);
        inOrder.verify(agendaCommand, times(1)).update(ID, agenda);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_start_agenda() throws ActionNotAllowedException, ResourceNotFoundException {
        agendaRestService.start(ID, MINUTES_TO_END);

        InOrder inOrder = inOrder(agendaCommand);
        inOrder.verify(agendaCommand, times(1)).start(ID, MINUTES_TO_END);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_get_agenda_all_description() {
        List<Agenda> agendaList = Fixture.from(Agenda.class).gimme(5, VALID.name());
        when(agendaQuery.findAll(any(Specification.class))).thenReturn(agendaList);

        assertEquals(ResponseEntity.ok(agendaList), agendaRestService.findAll(DESCRIPTION));

        InOrder inOrder = inOrder(agendaQuery);
        inOrder.verify(agendaQuery, times(1)).findAll(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_get_agenda_by_id() throws Throwable {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        when(agendaQuery.findOne(ID)).thenReturn(agenda);

        assertEquals(ResponseEntity.ok(agenda), agendaRestService.findOne(ID));

        InOrder inOrder = inOrder(agendaQuery);
        inOrder.verify(agendaQuery, times(1)).findOne(ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_delete_agenda() throws Throwable {
        agendaRestService.delete(ID);

        InOrder inOrder = inOrder(agendaCommand);
        inOrder.verify(agendaCommand, times(1)).delete(ID);
        inOrder.verifyNoMoreInteractions();
    }

}
