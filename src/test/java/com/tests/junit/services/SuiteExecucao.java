package com.tests.junit.services;

import com.tests.junit.model.CalculadoraTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Adriano Rabello 29/05/2022 13:56:41
 **/


/**
 * Com essa anotação eu consito colocar um nível a cima de todos os testes
 * e executar comandos. É como se fosse um service que chama os demais testes
 * */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LocacaoServiceTest.class,
        CalculoValorLocacaoTest.class
})
public class SuiteExecucao {

    @Mock
    public SPCService spcService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void before(){
        System.out.println("before");
    }

    @AfterClass
    public static void after(){
        System.out.println("after");
    }
}
