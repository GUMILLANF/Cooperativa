package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.mockito.Mockito.times;

public class ResultCommandTest extends TestSupport {

    @Mock
    private ResultRepository repository;

    private ResultCommand resultCommand;

    @Override
    public void init() {
        resultCommand = new ResultCommand(repository);
    }

    @Test
    public void should_create_result() {
        Result result = Fixture.from(Result.class).gimme(VALID.name());

        resultCommand.create(result);

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).save(result);
        inOrder.verifyNoMoreInteractions();
    }

}
