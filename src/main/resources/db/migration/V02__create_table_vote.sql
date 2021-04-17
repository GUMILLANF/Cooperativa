CREATE TABLE Vote (
    agendaId int not null,
    associetedCpf varchar(11) not null,
    date timestamp not null,
    response varchar(255) not null,
    primary key(agendaId, associetedCpf)
);

ALTER TABLE Vote ADD CONSTRAINT fk_vote_agenda FOREIGN KEY (agendaId) REFERENCES Agenda(id);
