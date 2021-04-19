package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.agenda.integration.ResultSendMessage;
import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.gumillanf.cooperativa.vote.VoteQuery;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.List;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

public class AgendaCommandTest extends TestSupport {

    private static final Integer MINUTES_TO_END = 1;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private AgendaQuery agendaQuery;

    @Mock
    private VoteQuery voteQuery;

    @Mock
    private ResultSendMessage resultSendMessage;

    @Mock
    private AppMessageSource messageSource;

    private AgendaCommand agendaCommand;

    @Override
    public void init() {
        agendaCommand = new AgendaCommand(agendaRepository, agendaQuery, voteQuery,
                resultSendMessage, messageSource);
    }

    @Test
    public void should_create_agenda() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        when(agendaRepository.save(any())).thenReturn(agenda);

        assertEquals(agenda, agendaCommand.save(agenda));

        InOrder inOrder = inOrder(agendaRepository);
        inOrder.verify(agendaRepository, times(1)).save(agenda);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_update_agenda() throws ResourceNotFoundException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        Agenda agendaUpdate = Fixture.from(Agenda.class).gimme(VALID.name());

        when(agendaQuery.findOne(anyLong())).thenReturn(agenda);

        agendaCommand.update(agenda.getId(), agendaUpdate);

        InOrder inOrder = inOrder(agendaQuery, agendaRepository);
        inOrder.verify(agendaQuery, times(1)).findOne(anyLong());
        inOrder.verify(agendaRepository, times(1)).save(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_start_agenda() throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        when(agendaQuery.findOne(anyLong())).thenReturn(agenda);

        agendaCommand.start(agenda.getId(), MINUTES_TO_END);
        assertTrue(agenda.isOpen());

        InOrder inOrder = inOrder(agendaQuery, agendaRepository);
        inOrder.verify(agendaQuery, times(1)).findOne(anyLong());
        inOrder.verify(agendaRepository, times(1)).save(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ActionNotAllowedException.class)
    public void should_start_agenda_exception() throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(FINISHED.name());
        when(agendaQuery.findOne(anyLong())).thenReturn(agenda);

        agendaCommand.start(agenda.getId(), MINUTES_TO_END);
    }

    @Test
    public void should_finish_agendas() {
        List<Agenda> agendaList = Fixture.from(Agenda.class).gimme(5, VALID.name());
        when(agendaQuery.findToFinish()).thenReturn(agendaList);

        agendaCommand.finish();

        InOrder inOrder = inOrder(agendaQuery, agendaRepository, voteQuery, resultSendMessage);
        inOrder.verify(agendaQuery, times(1)).findToFinish();
        inOrder.verify(agendaRepository, times(1)).saveAll(agendaList);
        agendaList.forEach(a -> {
            inOrder.verify(voteQuery, times(2)).count(any());
            inOrder.verify(resultSendMessage, times(1)).sendMessage(any());
        });
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_delete_agenda() throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        when(agendaQuery.findOne(anyLong())).thenReturn(agenda);

        agendaCommand.delete(agenda.getId());

        InOrder inOrder = inOrder(agendaQuery, agendaRepository);
        inOrder.verify(agendaQuery, times(1)).findOne(anyLong());
        inOrder.verify(agendaRepository, times(1)).delete(agenda);
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ActionNotAllowedException.class)
    public void should_return_exception_delete_agenda() throws ResourceNotFoundException, ActionNotAllowedException {
        Agenda agenda = Fixture.from(Agenda.class).gimme(STARTED.name());
        when(agendaQuery.findOne(anyLong())).thenReturn(agenda);

        agendaCommand.delete(agenda.getId());
    }

}
