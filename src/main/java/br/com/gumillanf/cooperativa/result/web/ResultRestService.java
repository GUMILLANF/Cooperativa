package br.com.gumillanf.cooperativa.result.web;

import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.ResultQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/result")
public class ResultRestService {

    private final ResultQuery resultQuery;

    private final ResultAssemblerSupport resultAssemblerSupport;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findByFinalResult(@RequestParam(defaultValue = "APPROVED_AGENDA") FinalResult finalResult) {
        return ResponseEntity.ok(resultQuery.findByFinalResult(finalResult));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findAll() {
        return ResponseEntity.ok(resultQuery.findAll());
    }

    @GetMapping("/agenda/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findByAgendaId(@PathVariable Long id) throws Throwable {
        return ResponseEntity.ok(resultAssemblerSupport.toModel(resultQuery.findByAgenda(id)));
    }

}
