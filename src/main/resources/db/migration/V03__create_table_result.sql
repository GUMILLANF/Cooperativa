CREATE TABLE Result (
    id serial not null constraint pk_result primary key,
    agendaId int not null,
    finalResult varchar(20) not null,
    amountYes int not null,
    amountNo int not null,
    UNIQUE(agendaId)
);

ALTER TABLE Result ADD CONSTRAINT fk_result_agenda FOREIGN KEY (agendaId) REFERENCES Agenda(id);
