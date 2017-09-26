package br.com.trapp.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.trapp.vo.FormaPgtoVO;

@FacesConverter(value="formaPgtoConverter")
public class FormaPgtoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		for (UIComponent child : component.getChildren()) {  
            if (child instanceof UISelectItems) {  
                @SuppressWarnings("unchecked")
				List<FormaPgtoVO> items = (List<FormaPgtoVO>) ((UISelectItems) child).getValue();  
                if(items!=null){
	                return obterFormaPgto(items, value);
                }
            }  
        } 
		return null;
	}

	private Object obterFormaPgto(List<FormaPgtoVO> items, String value) {
		for(FormaPgtoVO vo : items){
        	if(vo.getIdFormaPgto().toString().equals(value)){
        		return vo; 
        	}
        }
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null && ! "".equals(value)) {
			FormaPgtoVO vo = (FormaPgtoVO) value;
            return vo.getIdFormaPgto().toString();
		}
		return null;
	}
}
