package com.tests.junit.matchers;

import com.tests.junit.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Adriano Rabello 29/05/2022 10:47:54
 **/
public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

    private Integer diferencaDias;

    public DataDiferencaDiasMatcher(Integer diferencaDias) {
        this.diferencaDias = diferencaDias;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diferencaDias));
    }

    @Override
    public void describeTo(Description description) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DataUtils.obterDataComDiferencaDias(diferencaDias));
        String dataPorExtenso = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).format(calendar.getTime());
        description.appendText(dataPorExtenso);
    }
}
