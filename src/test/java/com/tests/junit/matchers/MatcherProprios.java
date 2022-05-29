package com.tests.junit.matchers;

import com.tests.junit.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Adriano Rabello 29/05/2022 10:24:37
 **/
public class MatcherProprios {


    public static DiaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiNaSegundaFeira(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMatcher ehNoDia(Integer dias){
        return new DataDiferencaDiasMatcher(dias);
    }

    public static DataDiferencaDiasMatcher ehHoje(){
        return ehNoDia(0);
    }


}
