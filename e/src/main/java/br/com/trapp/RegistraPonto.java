package br.com.trapp;

import java.sql.Connection;
import java.sql.Statement;

import javax.jws.WebService;

@WebService(targetNamespace = "http://trapp.com.br/", endpointInterface = "br.com.trapp.Ponto", portName = "RegistraPontoPort", serviceName = "RegistraPontoService")
public class RegistraPonto implements Ponto {

	public int registra() {
		int resultado=0;

		//Connection con = ConnectMySql.createConnection("jdbc:mysql://127.0.0.1:3306/e", "root", "root");
		//Connection con  =ConnectMySql.createConnection("jdbc:mysql://127.10.59.130:3306/e","admindKmxv7z", "Fa3QPUaN895w");
		Connection con =ConnectMySql.createConnection();

		if (con != null) {

			try {
				Statement stmt = con.createStatement();
				resultado = stmt.executeUpdate("INSERT INTO ponto (BATIDA) VALUES((SELECT NOW()));");
			} catch (Exception e) {
				System.out.println("Problemas ao registro. " + e.getMessage());
			}
			
			close();

		} else {
			System.out.println("Failed to make connection!");
		}
		return resultado;
	}
	
	public void close() {
		ConnectMySql.close();
	}
}