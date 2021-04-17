package br.com.gumillanf.cooperativa.agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface AgendaRepository extends JpaRepository<Agenda, Long>, JpaSpecificationExecutor<Agenda> {
}
