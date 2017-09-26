package br.com.trapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.trapp.vo.ContaVO;
import br.com.trapp.vo.FormaPgtoVO;

@ManagedBean
//@SessionScoped
@ViewScoped
public class AutoComplete implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private ContaVO conta;
	
	private ListagemBean listagemBean = new ListagemBean();

	private List<ContaVO> categoriaConta;

	private List<FormaPgtoVO> formaPgto;

	private List<String> listaResponsavel;
     
    @PostConstruct
    public void init() {
    	categoriaConta = listagemBean.listarContas();
    	formaPgto = listagemBean.listarFormasPgto();
    	listaResponsavel = listagemBean.listarResponsavel();
    	
//    	listaResponsavel = new ArrayList<String>();
//    	listaResponsavel.add("Emi");
//    	listaResponsavel.add("Lu");
    }
	    
	
	public AutoComplete (){

	}

	public List<String> complete(String query){
		List<String> queries = listagemBean.buscarConta(query);
		return queries;
	}

	public List<ContaVO> completeOld(String query){
		List<ContaVO> queries = listagemBean.buscarContaVO(query);
		return queries;
	}

    public List<ContaVO> completeConta(String query) {
        List<ContaVO> filteredContas = new ArrayList<ContaVO>();
         
        for (int i = 0; i < categoriaConta.size(); i++) {
            ContaVO contaNome = categoriaConta.get(i);
            if(contaNome.getConta().toLowerCase().contains(query.toLowerCase())) {
                filteredContas.add(contaNome);
            }
        }
         
        return filteredContas;
    }	
	
    public List<FormaPgtoVO> completeFormaPgto(String query) {
        List<FormaPgtoVO> filteredContas = new ArrayList<FormaPgtoVO>();
         
        for (int i = 0; i < formaPgto.size(); i++) {
            FormaPgtoVO vo = formaPgto.get(i);
            if(vo.getFormaPgto().toLowerCase().contains(query.toLowerCase())) {
                filteredContas.add(vo);
            }
        }
         
        return filteredContas;
    }	

	public List<String> completeResponsavel(String query){
//		List<String> queries = listagemBean.buscarConta(query);
//		return queries;
		
        List<String> filteredResponsavel = new ArrayList<String>();
        
        for (int i = 0; i < listaResponsavel.size(); i++) {
            String vo = listaResponsavel.get(i);
            if(vo.toLowerCase().contains(query.toLowerCase())) {
                filteredResponsavel.add(vo);
            }
        }
         
        return filteredResponsavel;		
	}

    
    
	public void salvar() {
		System.out.println("message:" + message);
		System.out.println("contaVo:" + conta.getIdConta() + "-"+ conta.getConta());
	}
	
	public List<String> completeBkp(String query){
		List<String> queries = new ArrayList<String>();
		for(int i = 0 ; i < 15 ; i++){
			queries.add(query+i);
		}
		return queries;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContaVO getConta() {
		return conta;
	}

	public void setConta(ContaVO conta) {
		this.conta = conta;
	}

}