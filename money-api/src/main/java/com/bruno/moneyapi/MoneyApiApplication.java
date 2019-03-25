package com.bruno.moneyapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bruno.moneyapi.model.Categoria;
import com.bruno.moneyapi.repository.CategoriaRepository;
import com.bruno.moneyapi.repository.PessoaRepository;

@SpringBootApplication
public class MoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyApiApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner job(CategoriaRepository cr) {
//		return(args) ->{
//			Categoria c = new Categoria();
//			c.setNome("Categoria 1");
//			cr.save(c);
//			
//		};
//		}
	
}

