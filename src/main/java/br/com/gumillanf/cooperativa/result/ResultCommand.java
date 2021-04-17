package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.gumillanf.cooperativa.result.Result.of;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ResultCommand {

    private final ResultRepository resultRepository;

    public void create(Agenda agenda, List<Vote> votes) {
        resultRepository.save(of(agenda, countVote(votes, VoteResponse.YES), countVote(votes, VoteResponse.NO)));
    }

    private Integer countVote(List<Vote> votes, VoteResponse response) {
        return (int)votes.stream().filter(vote -> vote.getResponse().equals(response)).count();
    }

}
