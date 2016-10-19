package com.invest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class DataUtil
{
  public static final String PATTERN_DATA_1 = "dd MMM yyyy hh:mm:ss";
  public static final String PATTERN_DATA_2 = "dd/MM/yyyy";
  public static final String PATTERN_DATA_3 = "EEE, d MMM yyyy HH:mm:ss Z";
  public static final String PATTERN_DATA_4 = "EEE MMM D HH:mm:ss";
  public static final String PATTERN_DATA_5 = "dd/MM/yyyy hh:mm";
  public static final String PATTERN_DATA_6 = "dd/MM/yy";
  
  public static Date parseToDate(String stringDate, String pattern)
  {
    SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
    try
    {
      return formatter.parse(stringDate);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Date parseToDate(String stringDate)
  {
    String pattern = null;
    switch (stringDate.length())
    {
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
  
  public static int compararData(Date data1, Date data2)
  {
    LocalDate l1 = new LocalDate(data1);
    LocalDate l2 = new LocalDate(data2);
    return l1.compareTo(l2);
  }
  
  public static void main(String[] args)
  {
    String stData1 = "Thu, 07 May 2015 19:50:00 -0300";
    String stData2 = "07/05/2015";
    String stData3 = "04 May 2015 05:00:00";
    String stData4 = "Thu May 07 15:27:00";
    String stData5 = "13/05/2015 15:20";
    
    System.out.println(stData1.length());
    System.out.println(stData2.length());
    System.out.println(stData3.length());
    System.out.println(stData4.length());
    System.out.println(stData5.length());
    Date data1 = parseToDate(stData1);
    Date data2 = parseToDate(stData2);
    Date data3 = parseToDate(stData3);
    Date data4 = parseToDate(stData4);
    Date data5 = parseToDate(stData5);
    
    System.out.println(data3);
    System.out.println(data4);
    System.out.println(data5);
    System.out.println(compararData(data1, data2));
  }
  
  public Date obterData(String dataString)
  {
    DateTime data = null;
    try
    {
      data = DateTime.parse(dataString, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
    }
    catch (IllegalArgumentException e)
    {
      data = DateTime.parse(dataString.substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
    }
    return data.toDate();
  }
}
