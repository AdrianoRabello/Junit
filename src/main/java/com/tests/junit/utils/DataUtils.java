package com.tests.junit.utils;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static java.util.Calendar.YEAR;

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

    public static boolean verificarDiaDaSemana(Date data, int diaSemana){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        boolean b = calendar.get(DAY_OF_WEEK) == diaSemana;
        return b;
    }


    public static boolean isMesmaData(Date data1, Date data2){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(data1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(data2);
        return (calendar1.get(DAY_OF_MONTH) == calendar2.get(DAY_OF_MONTH))
                && (calendar1.get(MONTH) == calendar2.get(MONTH))
                && (calendar1.get(YEAR) == calendar2.get(YEAR));
    }
}
