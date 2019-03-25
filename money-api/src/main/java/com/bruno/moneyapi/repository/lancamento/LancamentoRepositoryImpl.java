package com.bruno.moneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.bruno.moneyapi.model.Lancamento;
import com.bruno.moneyapi.repository.filter.LancamentoFilter;
import com.bruno.moneyapi.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter,Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery= builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] listaQuery = criarRestricoes(lancamentoFilter,builder,root);
		
		TypedQuery<Lancamento> query= manager.createQuery(criteriaQuery);
		
		adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<Lancamento>(query.getResultList(), pageable, total(lancamentoFilter)) ;
	}

	private long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder=manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual* totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
		
		
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), "%"+ lancamentoFilter.getDescricao().toLowerCase()+"%"));
		}
		
		if(lancamentoFilter.getDataVencimento() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(lancamentoFilter.getDataVencimento().toString()),lancamentoFilter.getDataVencimentoAte()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThan(root.get(lancamentoFilter.getDataVencimentoAte().toString()),lancamentoFilter.getDataVencimento()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento>root= criteria.from(Lancamento.class);


		
		
		criteria.select(builder.construct(ResumoLancamento.class,
				root.get("codigo"),root.get("descricao")
				,root.get("dataVencimento"),root.get("dataPagamento")
				,root.get("valor"),root.get("tipo")
				,root.get("categoria").get("nome")
				,root.get("pessoa").get("nome")));
		
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query= manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		
		return new PageImpl<ResumoLancamento>(query.getResultList(),pageable,total(lancamentoFilter));
	}

}
