package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static br.com.gumillanf.cooperativa.agenda.AgendaStatus.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgendaStatus status;

    private LocalDateTime startDateAgenda;

    private LocalDateTime endDateAgenda;

    public Agenda start(Integer minutesToEnd) {
        if (this.status.equals(WAITING)) {
            this.startDateAgenda = LocalDateTime.now();
            this.endDateAgenda = LocalDateTime.now().plusMinutes(minutesToEnd);
            this.status = AgendaStatus.VOTING;
        }
        return this;
    }

    public Agenda finish() {
        if (this.status.equals(VOTING)) {
            this.status = FINISHED;
        }
        return this;
    }

    public boolean isOpen() {
        return (this.status.equals(VOTING) && LocalDateTime.now().isBefore(this.endDateAgenda));
    }

    public static Agenda of(String description) {
        return Agenda.builder().description(description).status(AgendaStatus.WAITING).build();
    }

}
