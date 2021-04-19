package br.com.gumillanf.cooperativa.result.integration;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.Result;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static org.junit.Assert.assertEquals;

public class ResultEventTest extends TestSupport {

    private static ResultEvent resultEvent;

    @Override
    public void init() {
        resultEvent = Fixture.from(ResultEvent.class).gimme(VALID.name());
    }

    @Test
    public void should_return_result_class() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(FINISHED.name());
        assertEquals(expected(agenda), resultEvent.to(agenda));
    }

    private Result expected(Agenda agenda) {
        return Result.builder().agenda(agenda).amountYes(resultEvent.getAmountYes()).amountNo(resultEvent.getAmountNo())
                .finalResult(FinalResult.valueOf(resultEvent.getFinalResult())).build();
    }

}
