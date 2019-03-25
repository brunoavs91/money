package com.bruno.moneyapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.moneyapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	

}
