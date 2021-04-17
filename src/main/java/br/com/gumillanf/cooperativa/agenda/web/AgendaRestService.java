package br.com.gumillanf.cooperativa.agenda.web;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.agenda.AgendaCommand;
import br.com.gumillanf.cooperativa.agenda.AgendaQuery;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.gumillanf.cooperativa.agenda.Agenda.of;
import static br.com.gumillanf.cooperativa.agenda.AgendaSpecification.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/agenda")
public class AgendaRestService {

    private final AgendaCommand agendaCommand;

    private final AgendaQuery agendaQuery;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agenda create(@RequestBody @Valid AgendaResource agendaResource) {
        return agendaCommand.save(of(agendaResource.getDescription()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid AgendaResource agendaResource) throws ResourceNotFoundException {
        agendaCommand.update(id, of(agendaResource.getDescription()));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> start(@PathVariable Long id, @RequestParam(defaultValue = "1") Integer minutesToEnd)
            throws ResourceNotFoundException, ActionNotAllowedException {
        agendaCommand.start(id, minutesToEnd);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findAll(@RequestParam(defaultValue = "") String description) {
        return ResponseEntity.ok(agendaQuery.findAll(agendaDescription(description)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findOne(@PathVariable Long id) throws Throwable {
        return ResponseEntity.ok(agendaQuery.findOne(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, ActionNotAllowedException {
        agendaCommand.delete(id);
        return ResponseEntity.noContent().build();
    }

}
