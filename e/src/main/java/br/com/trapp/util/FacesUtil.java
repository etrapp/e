package br.com.trapp.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * classe utilitaria para acoes com o Faces Context
 * 
 * 
 * @author carlos.bispo
 *
 */
public class FacesUtil {

	/*
	 * retorna parametro da requisicao
	 */
	public static String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	/*
	 * redireciona pagina
	 */
	public static void redirecionaPagina(String pagina) throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	
	/*
     * atribui valor para mensagem na tela 
     */
    public static void exibirMensagem(FacesMessage fm) {
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }
    
    public static void exibirMensagemInfo(String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, facesMessage);
    }
    
    public static void exibirMensagemInfo(String mensagem, String detalhes) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, detalhes);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, facesMessage);
    }
    
    public static void exibeMensagemErro(String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, facesMessage);
    }
    
    
    public static void exibeMensagemErro(String mensagem, String detalhes) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, detalhes);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, facesMessage);
    }
    
    
    public static HttpServletResponse getHttpResponse() {
    	FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        return response;
    }
    
    public static void responseComplete() {
    	 FacesContext.getCurrentInstance().responseComplete();
    }
    
    
    
    


}
