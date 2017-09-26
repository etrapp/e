package br.com.trapp;

import java.sql.*;

import javax.jws.WebService;

@WebService(targetNamespace = "http://trapp.com.br/", endpointInterface = "br.com.trapp.Caixa", portName = "ConsultaContaPort", serviceName = "ConsultaContaService")
public class ConsultaConta implements Caixa {

	public String consulta(int key) {
		String resultado = "";

		//Connection con = ConnectMySql.createConnection("jdbc:mysql://127.0.0.1:3306/e", "root", "root");
		//Connection con =ConnectMySql.createConnection("jdbc:mysql://127.10.59.130:3306/e","admindKmxv7z", "Fa3QPUaN895w");
		Connection con =ConnectMySql.createConnection();

		if (con != null) {

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from conta where id_conta = " + key);
				while (rs.next()) {
					resultado = rs.getString(2);
				}
			} catch (Exception e) {
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