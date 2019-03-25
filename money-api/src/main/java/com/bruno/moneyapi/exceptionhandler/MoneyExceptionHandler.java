package com.bruno.moneyapi.exceptionhandler;



import  java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.aspectj.weaver.bcel.ExceptionRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bruno.moneyapi.model.Pessoa;
import com.bruno.moneyapi.service.exception.PessoaInexistenteOuInativaException;



@ControllerAdvice
//Observa toda aplicaçao
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private static MessageSource messageSouce;
	
  @Override
protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
	  
	  String mensagemUsuario= messageSouce.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
	  String mensagemDesenvolvedor=ex.getCause().toString();
	  List<Erro>erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
	return super.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
}
  
	  @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		  List<Erro>erros =carregaListaErro(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	  @ExceptionHandler({EmptyResultDataAccessException.class})
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public void handleEmptyResulDataAcessException() {
		  
	  }
 //pega a lista de erro no parametro bindingResult
	  public List<Erro> carregaListaErro(BindingResult bindingResult){
		 List<Erro> erros = new ArrayList<Erro>();
		 for(FieldError fielError: bindingResult.getFieldErrors()) {
			 String mensagemUsuario = messageSouce.getMessage(fielError, LocaleContextHolder.getLocale());
			 String mensagemDesenvolvedor = fielError.toString();
		 }
		 
		  return erros;
	  }
	  
	  
	  
	  
  public static class Erro{
	  String mensagemUsuario;
	  String mensagemDesenvolvedor;
	
	  
	public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
		super();
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}
	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}
	  
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object>handlePessoaInexisteOuInativaException(PessoaInexistenteOuInativaException ex){
		
		  String mensagemUsuario =messageSouce.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		  String mensagemDesenvolvedor = ex.toString();
		  List<Erro>erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		  return ResponseEntity.badRequest().body(erros);
	}
	  
	  
  }
}
