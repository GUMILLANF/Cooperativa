package br.com.gumillanf.cooperativa.result;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ResultCommand {

    private final ResultRepository resultRepository;

    public void create(Result result) {
        resultRepository.save(result);
    }

}
