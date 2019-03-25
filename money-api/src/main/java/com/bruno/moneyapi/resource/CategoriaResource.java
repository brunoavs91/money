package com.bruno.moneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.moneyapi.event.RecursoCriadoEvent;
import com.bruno.moneyapi.model.Categoria;
import com.bruno.moneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@GetMapping
	public List<Categoria>listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED) anotaçao de retorno 201 create
	public ResponseEntity<?> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
	Categoria categoriaSalva=categoriaRepository.save(categoria);
	
	publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
	
	
		//retornando resposta 
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	@GetMapping("/{codigo}")
	public Optional<Categoria> buscaPeloCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findById(codigo);
	}
}
