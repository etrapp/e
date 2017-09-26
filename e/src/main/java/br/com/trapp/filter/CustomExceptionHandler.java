package br.com.trapp.filter;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpSession;

import br.com.trapp.util.MessagesUtil;
import br.com.trapp.util.SessionUtils;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;
    
//	final Logger logger = LogManager.getLogger(this.getClass());

    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            // get the exception from context
            Throwable t = context.getException();
            
//            logger.debug("error---->" + t.getMessage());

            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            //here you do what ever you want with exception
            try {

        		HttpSession sess = SessionUtils.getSession();
        		if(sess!=null) {
        			sess.invalidate();
        		}
        		
                if (t instanceof ViewExpiredException) {
                	
            		System.out.println("----ViewExpiredException----" + ((ViewExpiredException) t).getViewId());

                	String viewId = ((ViewExpiredException) t).getViewId();
                	if(viewId.equals("/login.xhtml")) {
                    	//Ignorar ExpiredSession para login
                		iterator.remove();
                	} else if(viewId.equals("/esqueceu_senha.xhtml")) {
                    	//Ignorar ExpiredSession para esqueceu a senha
                		iterator.remove();
                	} else if(viewId.equals("/esqueceu_senha_bradesco.xhtml")) {
                    	//Ignorar ExpiredSession para esqueceu a senha
                		iterator.remove();
                	} else if(viewId.equals("/primeiro_acesso.xhtml")) {
                		//Ignorar ExpiredSession para primeiro acesso
        				HttpSession session = SessionUtils.getSession(true);
        				session.setAttribute("usuario", "primeiro_acesso");    	
	                    String errorPageLocation = "/primeiro_acesso.xhtml";
	                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
	                    fc.getPartialViewContext().setRenderAll(true);
	                    fc.renderResponse();
	                    iterator.remove();
                		
                	} else if(viewId.equals("/erro.xhtml")) {
                		//Ignorar ExpiredSession para primeiro erro e direcionar para pagina de login
                		System.out.println("----redirect login.xhtml----" + ((ViewExpiredException) t).getViewId());
	                    String errorPageLocation = "/login.xhtml";
	                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
	                    fc.getPartialViewContext().setRenderAll(true);
	                    fc.renderResponse();
	                    iterator.remove();
                		
                	} else {
                		
                		System.out.println("----redirect login.xhtml----" + ((ViewExpiredException) t).getViewId());
                		
	    			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MessagesUtil.retornaMsg("msg.usuario.sessao_expirada"), null));
	    			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MessagesUtil.retornaMsg("msg.usuario.sessao_expirada_info"), null));
	                    requestMap.put("javax.servlet.error.message", MessagesUtil.retornaMsg("msg.usuario.sessao_expirada"));
	                    String errorPageLocation = "/login.xhtml";
	                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
	                    fc.getPartialViewContext().setRenderAll(true);
	                    fc.renderResponse();
	                    iterator.remove();
                	}
                	
                } else if (t instanceof FacesException) {
            		System.out.println("----FacesException direciona erro 500---- " + t.getCause());
//                    String errorPageLocation = "/500.xhtml";
//                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
//                    fc.getPartialViewContext().setRenderAll(true);
//                    fc.renderResponse();
//                    iterator.remove();       		
//                	//throw new FacesException("problemas com aplicaçao:" + t.getMessage());
                	
                    requestMap.put("javax.servlet.error.message", t.getMessage());
    			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, t.getMessage(), null));
                    nav.handleNavigation(fc, null, "/erro.xhtml");
                    fc.renderResponse();
            		iterator.remove();                	
                } else {
            		System.out.println("----other Exception - redirect erro.xhtml---- " + t.getCause());
                	
                    //redirect error page
                    requestMap.put("javax.servlet.error.message", t.getMessage());
    			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, t.getMessage(), null));
                    nav.handleNavigation(fc, null, "/erro.xhtml");
                    fc.renderResponse();
            		iterator.remove();
                    
                }

//                fc.renderResponse();
                // remove the comment below if you want to report the error in a jsf error message
                //JsfUtil.addErrorMessage(t.getMessage());
            } finally {
                //remove it from queue
//                iterator.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
    }
}