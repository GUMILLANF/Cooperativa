package br.com.gumillanf.cooperativa.result.web;

import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.Result;
import br.com.gumillanf.cooperativa.result.ResultQuery;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

public class ResultRestServiceTest extends TestSupport {

    private static final Long AGENDA_ID = 10L;

    @Mock
    private ResultQuery resultQuery;

    @Mock
    private ResultAssemblerSupport resultAssemblerSupport;

    private ResultRestService resultRestService;

    @Override
    public void init() {
        resultRestService = new ResultRestService(resultQuery, resultAssemblerSupport);
    }

    @Test
    public void should_return_list_result_by_final_result() {
        List<Result> results = Fixture.from(Result.class).gimme(5, VALID.name());
        when(resultQuery.findByFinalResult(FinalResult.APPROVED_AGENDA)).thenReturn(results);

        assertEquals(ResponseEntity.ok(results), resultRestService.findByFinalResult(FinalResult.APPROVED_AGENDA));

        InOrder inOrder = inOrder(resultQuery);
        inOrder.verify(resultQuery, times(1)).findByFinalResult(FinalResult.APPROVED_AGENDA);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_list_all_result() {
        List<Result> results = Fixture.from(Result.class).gimme(5, VALID.name());
        when(resultQuery.findAll()).thenReturn(results);

        assertEquals(ResponseEntity.ok(results), resultRestService.findAll());

        InOrder inOrder = inOrder(resultQuery);
        inOrder.verify(resultQuery, times(1)).findAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_result_by_agenda_id() throws Throwable {
        Result result = Fixture.from(Result.class).gimme(VALID.name());
        when(resultQuery.findByAgenda(AGENDA_ID)).thenReturn(result);

        assertEquals(ResponseEntity.ok(resultAssemblerSupport.toModel(result)), resultRestService.findByAgendaId(AGENDA_ID));

        InOrder inOrder = inOrder(resultQuery);
        inOrder.verify(resultQuery, times(1)).findByAgenda(AGENDA_ID);
        inOrder.verifyNoMoreInteractions();
    }

}
