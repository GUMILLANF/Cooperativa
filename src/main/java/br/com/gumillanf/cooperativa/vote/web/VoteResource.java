package br.com.gumillanf.cooperativa.vote.web;

import br.com.gumillanf.cooperativa.vote.VoteResponse;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor(access = PRIVATE)
public class VoteResource {

    @NotNull
    private Long agendaId;

    @NotEmpty
    private String associetedCpf;

    @NotNull
    private VoteResponse response;

}
