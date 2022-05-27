package com.tests.junit.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Adriano Rabello 27/05/2022 19:50:21
 **/
public class DataUtils {

    public static Date adicionarDias(Date data, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH,dias);
        return calendar.getTime();
    }

    public static Date obterDataComDiferencaDias(int dias){
        return adicionarDias(new Date(),dias);
    }

    public  static Date obterData(int dia, int mes,int ano){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, dia);
        calendar.add(Calendar.MONTH, mes);
        calendar.add(Calendar.YEAR, ano);
        return calendar.getTime();
    }
}
