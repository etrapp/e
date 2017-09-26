package br.com.trapp.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.trapp.bean.ListagemBean;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.vo.FormaPgtoVO;

@FacesConverter(value="formaPgtoAutoCompleteConverter")
public class FormaPgtoAutoCompleteConverter implements Converter{
	private ListagemBean listagemBean = new ListagemBean();
    
    public Object getAsObject(FacesContext contet, UIComponent component, String value) {
        if(value==null || value.equals(""))
            return null;
        try{
            Long id = Long.parseLong(value);
            return listagemBean.findFormaPgtoById(id);
        }catch (NumberFormatException e) {
//        	System.out.println("erro ao buscar forma de pgto por id:" + e.getCause());
            return listagemBean.findFormaPgtoByValue(value);            
        }catch (Exception e) {
            e.printStackTrace();
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MessagesUtil.retornaMsg("msg.formaPgto.invalido"), ""));
        }
    }

    public String getAsString(FacesContext contet, UIComponent component, Object value) {
        if(value==null || value.equals(""))
            return null;
//        return String.valueOf(((FormaPgtoVO)value).getIdFormaPgto());
        return String.valueOf(((FormaPgtoVO)value).getFormaPgto());
    }
}