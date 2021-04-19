package br.com.gumillanf.cooperativa.agenda.integration;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultCreateEvent {

    private Long agendaId;

    private Integer amountYes;

    private Integer amountNo;

    private String finalResult;

    public static ResultCreateEvent of(Agenda agenda, Integer amountYes, Integer amountNo, String finalResult) {
        return ResultCreateEvent.builder().agendaId(agenda.getId()).amountYes(amountYes)
                .amountNo(amountNo).finalResult(finalResult).build();
    }

}
