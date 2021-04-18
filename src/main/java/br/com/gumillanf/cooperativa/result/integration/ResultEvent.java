package br.com.gumillanf.cooperativa.result.integration;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.Result;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultEvent {

    private Long agendaId;

    private Integer amountYes;

    private Integer amountNo;

    private String finalResult;

    Result to(Agenda agenda) {
        return Result.builder().agenda(agenda).amountYes(this.amountYes).amountNo(this.amountNo)
                .finalResult(FinalResult.valueOf(finalResult)).build();
    }

}
