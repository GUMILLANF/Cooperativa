package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static br.com.gumillanf.cooperativa.result.FinalResult.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "agendaId")
    private Agenda agenda;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FinalResult finalResult;

    @NotNull
    private Integer amountYes;

    @NotNull
    private Integer amountNo;

    public Long getAgendaId() {
        return this.getAgenda().getId();
    }

    public static Result of(Agenda agenda, Integer amountYes, Integer amountNo) {
        return Result.builder().agenda(agenda).amountYes(amountYes).amountNo(amountNo)
                .finalResult(amountYes > amountNo ? APPROVED_AGENDA : (amountYes < amountNo ? UNAPPROVED_AGENDA : UNDEFINED))
                .build();
    }

}
