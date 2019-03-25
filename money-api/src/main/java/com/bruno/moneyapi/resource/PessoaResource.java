package com.bruno.moneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.moneyapi.event.RecursoCriadoEvent;
import com.bruno.moneyapi.model.Pessoa;
import com.bruno.moneyapi.repository.PessoaRepository;
import com.bruno.moneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List>  listar(){
		List<Pessoa>listaPessoa = pessoaRepository.findAll();
		return !listaPessoa.isEmpty() ? 
				ResponseEntity.ok(listaPessoa) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED) anotaçao de retorno 201 create
	public ResponseEntity<?> criar(@RequestBody Pessoa pessoa, HttpServletResponse response) {
	Pessoa pessoaSalva=pessoaRepository.save(pessoa);

	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		//retornando resposta 
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscaPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		return pessoa != null? ResponseEntity.ok(pessoa):ResponseEntity.notFound().build();
	}
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
		
	}
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	@PutMapping("/{codigo}/ativo")
	public void atualizarPropiedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo) {
		pessoaService.atualizarPropiedadeAtivo(codigo,ativo);
	}
}
