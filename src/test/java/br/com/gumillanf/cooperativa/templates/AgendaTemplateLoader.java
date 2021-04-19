package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.agenda.AgendaStatus;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;

public class AgendaTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Agenda.class).addTemplate(VALID.name(), new Rule() {
            {
                add("id", random(Long.class, range(1, 20)));
                add("description", random("Teste 1", "Teste 2", "Teste 3"));
                add("status", AgendaStatus.WAITING);
            }
        });

        Fixture.of(Agenda.class).addTemplate(STARTED.name()).inherits(VALID.name(), new Rule() {
            {
                add("status", AgendaStatus.VOTING);
                add("startDateAgenda", LocalDateTime.now());
                add("endDateAgenda", LocalDateTime.now().plusMinutes(10));
            }
        });

        Fixture.of(Agenda.class).addTemplate(FINISHED.name()).inherits(VALID.name(), new Rule() {
            {
                add("status", AgendaStatus.FINISHED);
                add("startDateAgenda", LocalDateTime.now().minusMinutes(10));
                add("endDateAgenda", LocalDateTime.now());
            }
        });
    }
}
