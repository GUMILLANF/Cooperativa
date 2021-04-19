package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.Result;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.FINISHED;
import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;

public class ResultTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Result.class).addTemplate(VALID.name(), new Rule() {
            {
                add("id", random(Long.class, range(1, 20)));
                add("agenda", one(Agenda.class, FINISHED.name()));
                add("finalResult", random(FinalResult.APPROVED_AGENDA, FinalResult.UNAPPROVED_AGENDA, FinalResult.UNDEFINED));
                add("amountYes", random(Integer.class, range(1, 20)));
                add("amountNo", random(Integer.class, range(1, 20)));
            }
        });
    }

}
