package com.bruno.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.moneyapi.model.Lancamento;
import com.bruno.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>,LancamentoRepositoryQuery {

}
