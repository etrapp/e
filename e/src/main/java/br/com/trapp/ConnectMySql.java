package br.com.trapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.trapp.util.MessagesUtil;

public class ConnectMySql {

	private static Connection con = null;

	public static Connection createConnection() {
		
		String url = MessagesUtil.retornaConfig("conn.db.url");
		String user = MessagesUtil.retornaConfig("conn.db.user");
		String passwd = MessagesUtil.retornaConfig("conn.db.pwd");		
		
		return createConnection(url,user,passwd);
	}
	
	public static Connection createConnection(String url, String user, String passwd) {
//		System.out.println("-------- MySQL JDBC Connection ------------");
		//Connection con = ConnectMySql.createConnection("jdbc:mysql://127.0.0.1:3306/e", "root", "root");
		//Connection con =ConnectMySql.createConnection("jdbc:mysql://127.10.59.130:3306/e","admindKmxv7z", "Fa3QPUaN895w");
		
		if(con != null) {
			try {
				if (con.isValid(0)) {
//				if (!con.isClosed()) {
//					System.out.println("conexão existente e valida....");
					return con;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(url, user, passwd);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (con != null) {
//			System.out.println("You made it, take control your database now!");

		} else {
//			System.out.println("Failed to make connection!");
		}
		return con;
	}

	public static Connection getConnection() {
		return con;
	}

	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}