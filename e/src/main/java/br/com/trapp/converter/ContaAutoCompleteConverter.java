package br.com.trapp.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.trapp.bean.ListagemBean;
import br.com.trapp.util.FacesUtil;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.vo.ContaVO;

@FacesConverter(value="contaAutoCompleteConverter")
public class ContaAutoCompleteConverter implements Converter{
	private ListagemBean listagemBean = new ListagemBean();
    
    public Object getAsObject(FacesContext contet, UIComponent component, String value) {
        if(value==null || value.equals(""))
            return null;
        try{
            Long id = Long.parseLong(value);
            return listagemBean.findContaById(id);
        }catch(NumberFormatException e) {
//        	System.out.println("erro ao buscar conta por id:" + e.getCause());
            return listagemBean.findContaByValue(value);        	
        }catch (Exception e) {
            e.printStackTrace();
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MessagesUtil.retornaMsg("msg.conta.invalido"), ""));
        }
    }

    public String getAsString(FacesContext contet, UIComponent component, Object value) {
        if(value==null || value.equals(""))
            return null;
//        return String.valueOf(((ContaVO)value).getIdConta());
        return String.valueOf(((ContaVO)value).getConta());
    }
}