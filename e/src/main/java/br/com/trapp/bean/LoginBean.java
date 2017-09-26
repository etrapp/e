package br.com.trapp.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import br.com.trapp.ConnectMySql;
import br.com.trapp.util.FacesUtil;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.util.SessionUtils;
import br.com.trapp.vo.UsuarioVO;

@ViewScoped
@ManagedBean
//@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 4815435519259217158L;

	private AuditoriaBean auditoria = new AuditoriaBean();
	
	private String userName="";

	private String senha;

	private String localeTxt;
	
	private Locale localeObj;
	
   private static Map<String,Object> countries;
   
	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("Portugues", new Locale("pt", "BR"));
		countries.put("Español", new Locale("es"));
		countries.put("English", Locale.ENGLISH);
		countries.put("Français", Locale.FRENCH);
		countries.put("Deutsche", Locale.GERMAN);
	}

	public String loginInternalTeste() throws Exception {
		FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.credencial.invalida", localeObj));
		return "mlogin";
	}

	public String loginInternal() throws Exception {
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
	    changeLocale();
		try {
			if (userName != null) {
				UsuarioVO usuario = getUserDb(userName);
				if(usuario!=null && Criptografia.descriptografa(usuario.getSenha()).equals(senha)) {
					auditoria.registrar(userName, "Login", userAgent);
					SessionUtils.setSessionMapValue("usuario", userName);
					SessionUtils.setSessionMapValue("idOrganizacao", usuario.getIdOrganizacao());
					changeLocale(localeTxt);
					SessionUtils.setSessionMapValue("locale", localeObj);

					if(userAgent.indexOf("Mobile") != -1 || userAgent.indexOf("Android") != -1 ){
						//versao mobile
//						FacesUtil.redirecionaPagina("m/caixa.xhtml");
//						return "mcaixa";
						return "mcx";
					} else {
						//versao web
//						FacesUtil.redirecionaPagina("caixa.xhtml");
						return "caixa";
					}
				} else {
					auditoria.registrar(userName, "TryLogin", userAgent);
					FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.credencial.invalida",localeObj));
					return "";
				}

			} else {
				FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.credencial.invalida",localeObj));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.redirecionaPagina("erro.xhtml");
			return "erro";
		}
		return "login";
	}
	
	public void checkBrowser() {
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
		if(userAgent.indexOf("Mobile") != -1 || userAgent.indexOf("Android") != -1 ){
			try {
				FacesUtil.redirecionaPagina("mlogin.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private UsuarioVO getUserDb(String login) throws SQLException {
		UsuarioVO usuario = null;
		Connection con =ConnectMySql.createConnection();

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select usuario, senha, email, nome, id_organizacao from usuario where ativo = 1 and usuario ='"+login+"'");
		if (rs.next()) {
			usuario = new UsuarioVO();
			usuario.setLogin(login);
			usuario.setSenha(rs.getString("senha"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setIdOrganizacao(rs.getLong("id_organizacao"));
		}

		return usuario;
	}

	public void localeChanged(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();
		changeLocale(newLocaleValue);
	}
				   
	public void changeLocale(String newLocale) {

		for (Map.Entry<String, Object> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(newLocale)) {
				localeObj = (Locale) entry.getValue();
				FacesContext.getCurrentInstance().getViewRoot().setLocale(localeObj);
			}
		}

	}
	
	public void changeLocale() {
		System.out.println("Login:: user:" + userName +  " - Locale:" + localeTxt);
		if(localeTxt != null) {
			localeObj = (Locale)SessionUtils.getSessionMapValue("locale");
			FacesContext.getCurrentInstance().getViewRoot().setLocale(localeObj);			
		}
	}
	
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		if(session != null) {
			session.invalidate();
		}
		return "login";
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Map<String, Object> getCountries() {
		return countries;
	}

	public Locale getLocaleObj() {
		return localeObj;
	}

	public void setLocaleObj(Locale localeObj) {
		this.localeObj = localeObj;
	}

	public String getLocaleTxt() {
		return localeTxt;
	}

	public void setLocaleTxt(String localeTxt) {
		this.localeTxt = localeTxt;
	}	
}
