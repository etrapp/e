package br.com.trapp.bean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.trapp.ConnectMySql;
import br.com.trapp.util.SessionUtils;

@ApplicationScoped
@ManagedBean
public class AuditoriaBean {

	
	public void registrar(String acao, String descricao) throws SQLException {
		String usuario = ""; 
		Object user = SessionUtils.getSessionMapValue("usuario");
		if(user!=null){
			usuario=user.toString();
		}
		
		registrar(usuario, acao, descricao);
	}
	
	public void registrar(String usuario, String acao, String descricao) throws SQLException {
		
		if(descricao==null){
			descricao="";
		}
		
		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "insert into auditoria (data, usuario, acao, descricao) values(now()"+ ",'" +usuario+"', '"+acao+"','"+descricao+ "')";
		stmt.executeUpdate(sql);

	}

}
