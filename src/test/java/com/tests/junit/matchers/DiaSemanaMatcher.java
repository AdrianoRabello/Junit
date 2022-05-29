package com.tests.junit.matchers;

import com.tests.junit.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Adriano Rabello 29/05/2022 10:13:33
 **/
public class DiaSemanaMatcher  extends TypeSafeMatcher<Date> {

    private Integer diaSemama;


    public DiaSemanaMatcher(Integer diaSemama) {
        this.diaSemama = diaSemama;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.verificarDiaDaSemana(data,diaSemama);
    }

    @Override
    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_WEEK, diaSemama);
        String dataPorExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,new Locale("pt","BR"));
        description.appendText(dataPorExtenso);
    }
}