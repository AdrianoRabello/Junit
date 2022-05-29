package com.tests.junit.matchers;

import java.util.Calendar;

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

}
