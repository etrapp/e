package br.com.trapp;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "Caixa", targetNamespace = "http://trapp.com.br/")
public interface Caixa {

	@WebMethod(operationName = "consulta", action = "urn:Consulta")
	@RequestWrapper(className = "br.com.trapp.jaxws.Consulta", localName = "consulta", targetNamespace = "http://trapp.com.br/")
	@ResponseWrapper(className = "br.com.trapp.jaxws.ConsultaResponse", localName = "consultaResponse", targetNamespace = "http://trapp.com.br/")
	@WebResult(name = "return")
	String consulta(@WebParam(name = "arg0") int key );

}