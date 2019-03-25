package com.bruno.moneyapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.moneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	
}
