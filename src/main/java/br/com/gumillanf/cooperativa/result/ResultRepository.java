package br.com.gumillanf.cooperativa.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface ResultRepository extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result> {
}
