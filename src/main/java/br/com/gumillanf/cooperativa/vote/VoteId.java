package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Builder
@ToString
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteId implements Serializable {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "agendaId")
    private Agenda agenda;

    private String associetedCpf;

    public Long getAgendaId() {
        return this.getAgenda().getId();
    }

}
