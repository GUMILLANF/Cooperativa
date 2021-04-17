package br.com.gumillanf.cooperativa.vote.web;

import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/vote")
public class VoteRestService {

    private final VoteCommand voteCommand;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vote create(@Valid @RequestBody VoteResource voteResource) throws Throwable {
        return voteCommand.create(voteResource);
    }

}
