package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.vote.web.VoteResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote implements Serializable {

    @EmbeddedId
    private VoteId id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VoteResponse response;

    public static Vote of(VoteResource voteResource, Agenda agenda) {
        return Vote.builder().id(VoteId.builder().agenda(agenda).associetedCpf(voteResource.getAssocietedCpf()).build())
                .date(LocalDateTime.now()).response(voteResource.getResponse()).build();
    }

}
