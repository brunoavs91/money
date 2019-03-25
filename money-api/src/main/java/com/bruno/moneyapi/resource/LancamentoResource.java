package com.bruno.moneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.moneyapi.model.Lancamento;
import com.bruno.moneyapi.repository.LancamentoRepository;
import com.bruno.moneyapi.repository.filter.LancamentoFilter;
import com.bruno.moneyapi.repository.projection.ResumoLancamento;
import com.bruno.moneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	
	@GetMapping(params ="resumo")
	public Page<ResumoLancamento>resumir(LancamentoFilter lancamentoFilter,Pageable pageable){
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter,Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter,pageable);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Lancamento> buscaPeloCodigo(@PathVariable Long codigo) {
		return lancamentoRepository.findById(codigo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento) {
		
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
}
