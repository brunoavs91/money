package com.bruno.moneyapi.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	private LocalDate dataVencimento;
	
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}
	
	
	
}
