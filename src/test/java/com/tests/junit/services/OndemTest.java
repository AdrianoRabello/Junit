package com.tests.junit.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Adriano Rabello 28/05/2022 09:25:06
 **/


/** Anotação para dolocar a ordem do teste pela nome de cada um */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OndemTest {

    public static int contador = 0;


    @Test
    public void inicia(){
        contador = 1;
    }

    @Test
    public void verifica(){
        Assert.assertEquals(1,contador);
    }
}
