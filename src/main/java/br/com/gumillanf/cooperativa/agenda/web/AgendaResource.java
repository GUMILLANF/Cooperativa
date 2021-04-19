package br.com.gumillanf.cooperativa.agenda.web;

import lombok.*;

import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor(access = PRIVATE)
public class AgendaResource {

    @NotEmpty
    private String description;

}
