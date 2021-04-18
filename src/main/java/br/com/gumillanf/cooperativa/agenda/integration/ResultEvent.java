package br.com.gumillanf.cooperativa.agenda.integration;

import br.com.gumillanf.cooperativa.agenda.Agenda;
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

    public static ResultEvent of(Agenda agenda, Integer amountYes, Integer amountNo, String finalResult) {
        return ResultEvent.builder().agendaId(agenda.getId()).amountYes(amountYes)
                .amountNo(amountNo).finalResult(finalResult).build();
    }

}
