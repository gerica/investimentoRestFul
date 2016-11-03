package com.invest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class DataUtil {
	public static final String PATTERN_DATA_1 = "dd MMM yyyy hh:mm:ss";
	public static final String PATTERN_DATA_2 = "dd/MM/yyyy";
	public static final String PATTERN_DATA_3 = "EEE, d MMM yyyy HH:mm:ss Z";
	public static final String PATTERN_DATA_4 = "EEE MMM D HH:mm:ss";
	public static final String PATTERN_DATA_5 = "dd/MM/yyyy hh:mm";
	public static final String PATTERN_DATA_6 = "dd/MM/yy";

	public static Date parseToDate(String stringDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
		try {
			return formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parseToDate(String stringDate) {
		String pattern = null;
		switch (stringDate.length()) {
		case 10:
			pattern = "dd/MM/yyyy";
			break;
		case 16:
			pattern = "dd/MM/yyyy hh:mm";
			break;
		case 19:
			pattern = "EEE MMM D HH:mm:ss";
			break;
		case 20:
			pattern = "dd MMM yyyy hh:mm:ss";
			break;
		case 31:
			pattern = "EEE, d MMM yyyy HH:mm:ss Z";
			break;
		default:
			pattern = "dd/MM/yyyy";
			if ((stringDate == null) || (stringDate.length() <= 0)) {
				stringDate = "10/10/2014";
			}
			break;
		}
		return parseToDate(stringDate, pattern);
	}

	public static int compararData(Date data1, Date data2) {
		LocalDate l1 = new LocalDate(data1);
		LocalDate l2 = new LocalDate(data2);
		return l1.compareTo(l2);
	}

	public static void main(String[] args) {
//		String stData1 = "Thu, 07 May 2015 19:50:00 -0300";
//		String stData2 = "07/05/2015";
//		String stData3 = "04 May 2015 05:00:00";
//		String stData4 = "Thu May 07 15:27:00";
//		String stData5 = "13/05/2015 15:20";
//		String stData6 = "28 oct 2016 15:20:00";
//
//		System.out.println(stData1.length());
//		System.out.println(stData2.length());
//		System.out.println(stData3.length());
//		System.out.println(stData4.length());
//		System.out.println(stData5.length());
//		System.out.println(stData6.length());
//		Date data1 = parseToDate(stData1);
//		Date data2 = parseToDate(stData2);
//		Date data3 = parseToDate(stData3);
//		Date data4 = parseToDate(stData4);
//		Date data5 = parseToDate(stData5);
//		Date data6 = parseToDate(stData6);
//
//		System.out.println(data3);
//		System.out.println(data4);
//		System.out.println(data5);
//
//		System.out.println(compararData(data1, data2));
//		System.out.println(data6);
//		System.out.println("----------------------------");
		String dt1 = "10 de out de 2016";
		String dt2 = "7 de out de 2016";
		String dt3 = "28 de fev";
		String dt4 = "8 de dez";
//		String dt5 = "8 de dez de";
		String dt6 = "1 de nov 18:07";		
		String dt7 = "12 de nov de 2016 18:07";
		System.out.println(dt1.length());
		System.out.println(dt2.length());
		System.out.println(dt3.length());
		System.out.println(dt4.length());
//		System.out.println(dt5.length());
		System.out.println(parseToDateTransformeString(dt1));
		System.out.println(parseToDateTransformeString(dt2));
		System.out.println(parseToDateTransformeString(dt3));
		System.out.println(parseToDateTransformeString(dt4));
//		System.out.println(parseToDateTransformeString(dt5));
		System.out.println(parseToDateTransformeString(dt6));
		System.out.println(parseToDateTransformeString(dt7));

	}

	public Date obterData(String dataString) {
		DateTime data = null;
		try {
			data = DateTime.parse(dataString, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
		} catch (IllegalArgumentException e) {
			data = DateTime.parse(dataString.substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
		}
		return data.toDate();
	}

	/**
	 * Criar data
	 * 
	 * @param dataAtransformar
	 *            data no formato dd de mmm : 28 ago
	 * @return data
	 */
	public static Date parseToDateTransformeString(String dataAtransformar) {
		if (dataAtransformar == null) {
			throw new IllegalArgumentException("A data não pode ser nulo.");
		}
		if (dataAtransformar.length() == 14){
			dataAtransformar = dataAtransformar.substring(0,8);
		}else if(dataAtransformar.length() == 23){
			dataAtransformar = dataAtransformar.substring(0,9);
		}
		
		if (!(dataAtransformar.length() == 8 //
				|| dataAtransformar.length() == 9 //
				|| dataAtransformar.length() == 16 //
				|| dataAtransformar.length() == 17)) {
			throw new IllegalArgumentException(
					"A data para esse método terá que estar no formato DD de MMM ou DD de MMM de YYYY. Exemplo: (28 de ago) ou (28 de ago de 2016)");
		}
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		String mes = null;
		String dia = null;

		if (dataAtransformar.length() == 16 || dataAtransformar.length() == 8) {
			dia = dataAtransformar.substring(0, 1);
			mes = dataAtransformar.substring(5, 8);
		} else {
			dia = dataAtransformar.substring(0, 2);
			mes = dataAtransformar.substring(6, 9);
		}

		if ("jan".equals(mes.toLowerCase())) {
			mes = "01";
		} else if ("fev".equals(mes.toLowerCase())) {
			mes = "02";
		} else if ("mar".equals(mes.toLowerCase())) {
			mes = "03";
		} else if ("abr".equals(mes.toLowerCase())) {
			mes = "04";
		} else if ("mai".equals(mes.toLowerCase())) {
			mes = "05";
		} else if ("jun".equals(mes.toLowerCase())) {
			mes = "06";
		} else if ("jul".equals(mes.toLowerCase())) {
			mes = "07";
		} else if ("ago".equals(mes.toLowerCase())) {
			mes = "08";
		} else if ("set".equals(mes.toLowerCase())) {
			mes = "09";
		} else if ("out".equals(mes.toLowerCase())) {
			mes = "10";
		} else if ("nov".equals(mes.toLowerCase())) {
			mes = "11";
		} else if ("dez".equals(mes.toLowerCase())) {
			mes = "12";
		}
		return parseToDate(dia + "/" + mes + "/" + year);
	}
}
