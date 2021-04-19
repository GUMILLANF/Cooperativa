package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.agenda.AgendaQuery;
import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.commons.core.AppUtils;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.gumillanf.cooperativa.vote.web.VoteResource;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static br.com.gumillanf.cooperativa.vote.Vote.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static java.util.Optional.of;

public class VoteCommandTest extends TestSupport {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteQuery voteQuery;

    @Mock
    private AgendaQuery agendaQuery;

    @Mock
    private AppMessageSource messageSource;

    @Mock
    private AppUtils appUtils;

    private VoteCommand voteCommand;

    @Override
    public void init() {
        voteCommand = new VoteCommand(voteRepository, voteQuery, agendaQuery, messageSource, appUtils);
    }

    @Test
    public void should_created_vote() throws Throwable {
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Agenda agenda = Fixture.from(Agenda.class).gimme(STARTED.name());
        Vote vote = of(voteResource, agenda);
        when(agendaQuery.findOne(voteResource.getAgendaId())).thenReturn(agenda);
        when(voteRepository.save(any())).thenReturn(vote);
        when(appUtils.validateCpf(anyString())).thenReturn(true);

        assertEquals(vote, voteCommand.create(voteResource));

        InOrder inOrder = inOrder(agendaQuery, voteRepository);
        inOrder.verify(agendaQuery, times(1)).findOne(any(Long.class));
        inOrder.verify(voteRepository, times(1)).save(any(Vote.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ActionNotAllowedException.class)
    public void should_exception_agenda_is_not_open() throws Throwable {
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());

        when(agendaQuery.findOne(voteResource.getAgendaId())).thenReturn(agenda);

        voteCommand.create(voteResource);
    }

    @Test(expected = ActionNotAllowedException.class)
    public void should_exception_vote_already_taken_by_cpf() throws Throwable {
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());
        Vote vote = Fixture.from(Vote.class).gimme(VALID.name());

        when(agendaQuery.findOne(voteResource.getAgendaId())).thenReturn(agenda);
        when(voteQuery.findOne(any(Specification.class))).thenReturn(of(vote));

        voteCommand.create(voteResource);
    }

    @Test(expected = ActionNotAllowedException.class)
    public void should_exception_cpf_is_not_valid() throws Throwable {
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Agenda agenda = Fixture.from(Agenda.class).gimme(VALID.name());

        when(agendaQuery.findOne(voteResource.getAgendaId())).thenReturn(agenda);
        when(appUtils.validateCpf(anyString())).thenReturn(false);

        voteCommand.create(voteResource);
    }

}
