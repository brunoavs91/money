package com.bruno.moneyapi.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.moneyapi.model.Lancamento;
import com.bruno.moneyapi.model.Pessoa;
import com.bruno.moneyapi.repository.LancamentoRepository;
import com.bruno.moneyapi.repository.PessoaRepository;
import com.bruno.moneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(@Valid Lancamento lancamento) {
	Pessoa pessoa =pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
	if(pessoa == null ||pessoa.isInativo() ) {
		throw new PessoaInexistenteOuInativaException();
	}
		return lancamentoRepository.save(lancamento);
	}

}
