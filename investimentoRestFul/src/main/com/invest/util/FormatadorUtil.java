package com.invest.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.Months;

public class FormatadorUtil
{
  public static String formatarData(Date data)
  {
    return formatarData(data, "dd/MM/yyyy");
  }
  
  public static String formatarData(Date data, String pattern)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    if (data != null) {
      return sdf.format(data);
    }
    return "";
  }
  
  public static String formatarMoeda(Double valor)
  {
    Locale in_ID = new Locale("pt", "BR");
    DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(in_ID);
    if (valor != null) {
      return nf.format(valor);
    }
    return "";
  }
  
  public static String formatarMoeda(BigDecimal valor)
  {
    Locale in_ID = new Locale("pt", "BR");
    DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(in_ID);
    if (valor != null) {
      return nf.format(valor);
    }
    return "";
  }
  
  public static String formatarMoedaEN(Double valor)
  {
    Locale in_ID = new Locale("en", "US");
    DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(in_ID);
    if (valor != null) {
      return nf.format(valor);
    }
    return "";
  }
  
  public static String formatarPorcentagem(Double valor)
  {
    NumberFormat defaultFormat = NumberFormat.getPercentInstance();
    defaultFormat.setMinimumFractionDigits(2);
    if (valor != null) {
      return defaultFormat.format(valor);
    }
    return "";
  }
  
  public static boolean isInteger(String s)
  {
    return isInteger(s, 10);
  }
  
  public static boolean isInteger(String s, int radix)
  {
    if (s.isEmpty()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      if ((i == 0) && (s.charAt(i) == '-'))
      {
        if (s.length() == 1) {
          return false;
        }
      }
      else if (Character.digit(s.charAt(i), radix) < 0) {
        return false;
      }
    }
    return true;
  }
  
  public static int differencaEmMes(LocalDate dateMaior, LocalDate dateMenor)
  {
    return Months.monthsBetween(dateMaior, dateMenor).getMonths();
  }
  
  public static int differencaEmMes(Date dateMaior, Date dateMenor)
  {
    return differencaEmMes(new LocalDate(dateMaior), new LocalDate(dateMaior));
  }
  
  public static Date[] getDataMesAnterior(Date data)
  {
    LocalDate hoje = null;
    if (data == null) {
      hoje = new LocalDate();
    } else {
      hoje = new LocalDate(data);
    }
    int mesHoje = hoje.getMonthOfYear();
    int anoHoje = hoje.getYear();
    if (mesHoje == 1)
    {
      mesHoje = 12;
      anoHoje--;
    }
    else
    {
      mesHoje--;
    }
    Date[] datas = new Date[2];
    datas[0] = hoje.withMonthOfYear(mesHoje).withYear(anoHoje).dayOfMonth().withMaximumValue().toDate();
    
    datas[1] = hoje.withMonthOfYear(mesHoje).withYear(anoHoje).dayOfMonth().withMinimumValue().toDate();
    
    return datas;
  }
  
  public static String retirarCaracteresEspaco(String valor)
  {
    String text = Normalizer.normalize(valor, Normalizer.Form.NFD);
    text = text.replaceAll("[^\\p{ASCII}]", "");
    text = text.replaceAll("[\\s\\-()\\$\\.\\/]", "");
    
    return text;
  }
  
  public static String formatarParaNumero(String valor)
  {
    valor = valor.replaceAll("[\\.\\%]", "");
    valor = valor.replace(",", ".");
    return valor;
  }
  
  public static boolean verificarStringVazia(String valor)
  {
    if ((valor == null) || ("".equals(valor.trim()))) {
      return true;
    }
    return false;
  }
  
  public static void main(String[] args)
    throws ParseException
  {
    System.out.println(formatarMoeda(new BigDecimal(-33220000)));
  }
}
