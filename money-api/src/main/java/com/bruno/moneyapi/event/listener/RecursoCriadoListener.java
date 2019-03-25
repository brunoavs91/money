package com.bruno.moneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.moneyapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {


	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoevent) {
	HttpServletResponse response = recursoCriadoevent.getResponse();
	Long codigo = recursoCriadoevent.getCodigo();
	
	adicionaHeader(response, codigo);
		
	}

	private void adicionaHeader(HttpServletResponse response, Long codigo) {
		URI uri=ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
			
			response.setHeader("Location",uri.toASCIIString());
	}

}
