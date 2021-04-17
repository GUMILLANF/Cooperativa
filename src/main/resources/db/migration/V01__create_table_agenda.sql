CREATE TABLE Agenda (
    id serial not null constraint pk_agenda primary key,
    description varchar(255) not null,
    status varchar(255) not null,
    startDateAgenda timestamp,
    endDateAgenda timestamp
);
