package br.com.trapp.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesUtil {
	
	public static String retornaConfig(String chave) {
    	return ResourceBundle.getBundle("config").getString(chave); 
    }

    public static String retornaMsg(String chave) {
    	return ResourceBundle.getBundle("Messages").getString(chave); 
    }
	
	public static String retornaMsg(String chave, Locale locale) {
		if (locale != null) {
			return ResourceBundle.getBundle("Messages", locale).getString(chave);
		} else {
			return retornaMsg(chave);
		}
	}
	
}