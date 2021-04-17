package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.commons.AppMessageSource;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.gumillanf.cooperativa.result.ResultSpecification.*;

@Service
@AllArgsConstructor
public class ResultQuery {

    private final ResultRepository resultRepository;

    private final AppMessageSource messageSource;

    public Result findByAgenda(Long agendaId) throws Throwable {
        return (Result) resultRepository.findOne(resultAgendaId(agendaId)).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("result.not-found",
                        new Object[] { agendaId })));
    }

    public List<Result> findByFinalResult(FinalResult finalResult) {
        return resultRepository.findAll(resultFinalResult(finalResult));
    }

}
