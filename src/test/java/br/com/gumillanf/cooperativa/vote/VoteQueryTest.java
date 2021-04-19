package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static br.com.gumillanf.cooperativa.vote.VoteSpecification.*;

public class VoteQueryTest extends TestSupport {

    private static final String ASSOCIETED_CPF = "12345";

    @Mock
    private VoteRepository repository;

    private VoteQuery voteQuery;

    @Override
    public void init() {
        voteQuery = new VoteQuery(repository);
    }

    @Test
    public void should_return_vote() {
        Vote vote = Fixture.from(Vote.class).gimme(VALID.name());
        when(repository.findOne(any(Specification.class))).thenReturn(of(vote));

        assertEquals(of(vote), voteQuery.findOne(voteAgendaId(vote.getId().getAgenda())));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findOne(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_all_by_specification() {
        List<Vote> votes = Fixture.from(Vote.class).gimme(5, VALID.name());
        when(repository.findAll(any(Specification.class))).thenReturn(votes);

        assertEquals(votes, voteQuery.findAll(voteAssocietedCpf(ASSOCIETED_CPF)));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_count() {
        List<Vote> votes = Fixture.from(Vote.class).gimme(5, VALID.name());
        when(repository.count(any(Specification.class))).thenReturn((long) votes.size());

        assertEquals((Integer)votes.size(), voteQuery.count(voteResponse(VoteResponse.YES)));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).count(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

}
