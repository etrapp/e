package br.com.trapp.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpSession getSession(boolean force) {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(force);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}

	public static String getUsuario() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("usuario");
		else
			return null;
	}

	/*
	 * atribui um valor para sessao
	 */
	public static void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(key, value);
	}

	/*
	 * retorna valor da sessao
	 */
	public static Object getSessionMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}

	public static void removeSession(String key) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
	}

	public static void invalidaSession() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
}
