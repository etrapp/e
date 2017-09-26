package br.com.trapp;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "Ponto", targetNamespace = "http://trapp.com.br/")
public interface Ponto {

	@WebMethod(operationName = "registra", action = "urn:Registra")
	@RequestWrapper(className = "br.com.trapp.jaxws.Registra", localName = "registra", targetNamespace = "http://trapp.com.br/")
	@ResponseWrapper(className = "br.com.trapp.jaxws.RegistraResponse", localName = "registraResponse", targetNamespace = "http://trapp.com.br/")
	@WebResult(name = "return")
	int registra();

}