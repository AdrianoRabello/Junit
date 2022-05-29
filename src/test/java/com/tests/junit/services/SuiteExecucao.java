package com.tests.junit.services;

import com.tests.junit.model.CalculadoraTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Adriano Rabello 29/05/2022 13:56:41
 **/


/**
 * Com essa anotação eu consito colocar um nível a cima de todos os testes
 * e executar comandos. É como se fosse um service que chama os demais testes
 * */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {

    @BeforeClass
    public static void before(){
        System.out.println("before");
    }

    @AfterClass
    public static void after(){
        System.out.println("after");
    }
}
