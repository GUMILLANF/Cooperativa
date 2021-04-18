package br.com.gumillanf.cooperativa.vote;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteQuery {

    private final VoteRepository voteRepository;

    public Optional<Vote> findOne(Specification<Vote> specification) {
        return voteRepository.findOne(specification);
    }

    public List<Vote> findAll(Specification<Vote> specification) {
        return voteRepository.findAll(specification);
    }

    public Integer count(Specification<Vote> specification) {
        return (int)voteRepository.count(specification);
    }

}
