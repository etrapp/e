package br.com.trapp.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.trapp.vo.ContaVO;

@FacesConverter(value="contaConverter")
public class ContaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		for (UIComponent child : component.getChildren()) {  
            if (child instanceof UISelectItems) {  
                @SuppressWarnings("unchecked")
				List<ContaVO> items = (List<ContaVO>) ((UISelectItems) child).getValue();  
                if(items!=null){
	                return obterConta(items, value);
                }
            }  
        } 
		return null;
	}

	private Object obterConta(List<ContaVO> items, String value) {
		for(ContaVO vo : items){
        	if(vo.getIdConta().toString().equals(value)){
        		return vo; 
        	}
        }
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null && ! "".equals(value)) {
			ContaVO vo = (ContaVO) value;
            return vo.getIdConta().toString();
		}
		return null;
	}
}
