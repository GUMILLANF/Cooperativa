package br.com.gumillanf.cooperativa.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface VoteRepository extends JpaRepository<Vote, VoteId>, JpaSpecificationExecutor<Vote> {
}
