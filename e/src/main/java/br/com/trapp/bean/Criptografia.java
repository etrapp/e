package br.com.trapp.bean;

public class Criptografia {

	private final static int conversor = 3; 
	
	protected static String criptografa(String senha) {
		String conversao = "";
		char[] x = senha.toCharArray();
		for (int i = 0; i < x.length; i++) {
			char c = x[i];
			conversao +=  (char) (c+conversor+i);			
		}
		return conversao;
	}
	
	protected static String descriptografa(String senha) {
		String conversao = "";
		char[] x = senha.toCharArray();
		for (int i = 0; i < x.length; i++) {
			char c = x[i];
			conversao +=  (char) (c-conversor-i);			
		}
		return conversao;
	}
	
//	public static void main(String args[]) {
//		System.out.println(criptografa("senha123"));
//	}
}
