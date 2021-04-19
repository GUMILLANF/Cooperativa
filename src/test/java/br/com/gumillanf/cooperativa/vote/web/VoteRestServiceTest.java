package br.com.gumillanf.cooperativa.vote.web;

import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteCommand;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class VoteRestServiceTest extends TestSupport {

    @Mock
    private VoteCommand voteCommand;

    private VoteRestService voteRestService;

    @Override
    public void init() {
        voteRestService = new VoteRestService(voteCommand);
    }

    @Test
    public void should_create_vote() throws Throwable {
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Vote vote = Fixture.from(Vote.class).gimme(VALID.name());
        when(voteCommand.create(voteResource)).thenReturn(vote);

        assertEquals(vote, voteRestService.create(voteResource));

        InOrder inOrder = inOrder(voteCommand);
        inOrder.verify(voteCommand, times(1)).create(any());
        inOrder.verifyNoMoreInteractions();
    }

}
