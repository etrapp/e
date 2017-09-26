package br.com.trapp.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PontoVO implements Serializable {

	private static final long serialVersionUID = 7653849280827398142L;
	private Date data;
	private List<String> hora;
	private String dataf;

	public String getMes() {
		SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMM");
		String resultado = formatoData.format(data);
		return resultado;
	}
	
	
	public String getResultado() {

		if(hora != null) {
			switch (hora.size()) {
			case 2:
				return diff2(hora.get(0), hora.get(1));
			case 3:
				return diff3(hora.get(0), hora.get(1), hora.get(2));
			case 4:
				return diff4(hora.get(0), hora.get(1), hora.get(2), hora.get(3));
			case 6:
				return diff6(hora.get(0), hora.get(1), hora.get(2), hora.get(3), hora.get(4), hora.get(5));
			default:
				break;
			}
		}
		
		return null;

	}
	private String diff2(String horaInicial,String horaFinal)  {
		String total="";
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calInicial = Calendar.getInstance();
        Calendar calFinal = Calendar.getInstance();

		calInicial.setTime(sdf.parse(horaInicial));
        calFinal.setTime(sdf.parse(horaFinal));
        
        long minutos = ((calFinal.getTimeInMillis() - calInicial.getTimeInMillis())) / 60000;
        long resto = minutos % 60;
        long horas = minutos / 60;
        
        total = horas + ":" + resto;

        } catch (ParseException e) {
			e.printStackTrace();
		}
        return total;
	}	
	
	private String diff2Faltante(String horaInicial,String horaFinal)  {
		String diferenca="";
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calInicial = Calendar.getInstance();
        Calendar calFinal = Calendar.getInstance();

		calInicial.setTime(sdf.parse(horaInicial));
        calFinal.setTime(sdf.parse(horaFinal));
        
        long minutos = ((calFinal.getTimeInMillis() - calInicial.getTimeInMillis())) / 60000;
        long minutos_faltantes = 480l - minutos;        
        long resto = minutos_faltantes % 60;
        long horas = minutos_faltantes / 60;
        
        diferenca = horas + ":" + resto;

        } catch (ParseException e) {
			e.printStackTrace();
		}
        return diferenca;
	}		
	
	private String diff3(String horaInicial,String horaFinal,String horaInicial2)  {
		String diferenca="";
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calInicial = Calendar.getInstance();
        Calendar calFinal = Calendar.getInstance();

        Calendar calInicial2 = Calendar.getInstance();
        Calendar calFinal2 = Calendar.getInstance();

		calInicial.setTime(sdf.parse(horaInicial));
        calFinal.setTime(sdf.parse(horaFinal));
        
        calInicial2.setTime(sdf.parse(horaInicial2));
        calFinal2.setTime(sdf.parse(horaInicial2));

        long minutos = ((calFinal.getTimeInMillis() - calInicial.getTimeInMillis())) / 60000;
        long minutos_faltantes = 480l - minutos;        
        long resto = minutos_faltantes % 60;
        long horas = minutos_faltantes / 60;
        
        diferenca = horas + ":" + resto;

        calFinal2.add(Calendar.MINUTE, (int)minutos_faltantes);
        
        diferenca = sdf.format(calFinal2.getTime());
        
        } catch (ParseException e) {
			e.printStackTrace();
		}
        return diferenca;
	}	

	
	private String diff4(String horaInicial,String horaFinal,String horaInicial2,String horaFinal2)  {
		String diferenca="";
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calInicial = Calendar.getInstance();
        Calendar calFinal = Calendar.getInstance();

        Calendar calInicial2 = Calendar.getInstance();
        Calendar calFinal2 = Calendar.getInstance();

		calInicial.setTime(sdf.parse(horaInicial));
        calFinal.setTime(sdf.parse(horaFinal));
        
        calInicial2.setTime(sdf.parse(horaInicial2));
        calFinal2.setTime(sdf.parse(horaFinal2));

        long minutos = ((calFinal.getTimeInMillis() - calInicial.getTimeInMillis()) + (calFinal2.getTimeInMillis() - calInicial2.getTimeInMillis())) / 60000;
        long resto = minutos % 60;
        long horas = minutos / 60;
        diferenca = horas + ":" + resto;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return diferenca;
	}		

	private String diff6(String horaInicial,String horaFinal,String horaInicial2,String horaFinal2,String extraInicial,String extraFinal)  {
		String diferenca="";
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calInicial = Calendar.getInstance();
        Calendar calFinal = Calendar.getInstance();

        Calendar calInicial2 = Calendar.getInstance();
        Calendar calFinal2 = Calendar.getInstance();

        Calendar calExtraInicial = Calendar.getInstance();
        Calendar calExtraFinal = Calendar.getInstance();

		calInicial.setTime(sdf.parse(horaInicial));
        calFinal.setTime(sdf.parse(horaFinal));
        
        calInicial2.setTime(sdf.parse(horaInicial2));
        calFinal2.setTime(sdf.parse(horaFinal2));

        calExtraInicial.setTime(sdf.parse(extraInicial));
        calExtraFinal.setTime(sdf.parse(extraFinal));

        long minutos = ((calFinal.getTimeInMillis() - calInicial.getTimeInMillis()) + (calFinal2.getTimeInMillis() - calInicial2.getTimeInMillis()) + (calExtraFinal.getTimeInMillis() - calExtraInicial.getTimeInMillis())) / 60000;
        long resto = minutos % 60;
        long horas = minutos / 60;
        diferenca = horas + ":" + resto;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return diferenca;
	}		

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDataf() {
		return dataf;
	}

	public void setDataf(String dataf) {
		this.dataf = dataf;
	}

	public List<String> getHora() {
		return hora;
	}

	public void setHora(List<String> hora) {
		this.hora = hora;
	}
	

}
