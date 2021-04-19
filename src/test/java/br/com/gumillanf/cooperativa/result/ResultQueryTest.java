package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
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

public class ResultQueryTest extends TestSupport {

    private static final Long ID = 10L;

    @Mock
    private ResultRepository repository;

    @Mock
    private AppMessageSource messageSource;

    private ResultQuery resultQuery;

    @Override
    public void init() {
        resultQuery = new ResultQuery(repository, messageSource);
    }

    @Test
    public void should_return_result_by_agenda_id() throws Throwable {
        Result result = Fixture.from(Result.class).gimme(VALID.name());
        when(repository.findOne(any(Specification.class))).thenReturn(of(result));

        assertEquals(result, resultQuery.findByAgenda(result.getAgenda().getId()));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findOne(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void find_one_should_throw_resource_not_found_exception_when_result_is_not_found() throws Throwable {
        resultQuery.findByAgenda(ID);
    }

    @Test
    public void should_return_all_by_final_result() {
        List<Result> results = Fixture.from(Result.class).gimme(5, VALID.name());
        when(repository.findAll(any(Specification.class))).thenReturn(results);

        assertEquals(results, resultQuery.findByFinalResult(FinalResult.APPROVED_AGENDA));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(any(Specification.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_all_result() {
        List<Result> results = Fixture.from(Result.class).gimme(5, VALID.name());
        when(repository.findAll()).thenReturn(results);

        assertEquals(results, resultQuery.findAll());

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll();
        inOrder.verifyNoMoreInteractions();
    }

}
