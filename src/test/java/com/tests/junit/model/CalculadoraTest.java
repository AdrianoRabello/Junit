package com.tests.junit.model;

import com.tests.junit.exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Adriano Rabello 28/05/2022 16:16:23
 **/
public class CalculadoraTest {


    @Mock
    private Calculadora calculadoraMock;

    @Spy
    private Calculadora calculadoraSpy;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveraSomarDoisNumeros() {

        Mockito.when(calculadoraMock.somar(5, 3)).thenReturn(8);
        int resultado = this.calculadoraMock.somar(5, 3);
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveraSubtrairDoisNumeros() {

        Mockito.when(calculadoraMock.subtrair(8, 3)).thenReturn(5);
        int a = 8;
        int b = 3;
        int resultado = calculadoraMock.subtrair(a, b);
        Assert.assertEquals(5, resultado);
    }


    @Test(expected = DivisaoPorZeroException.class)
    public void deveraLancarExcecaoComDivisaoPorZero() throws DivisaoPorZeroException {

        Mockito.when(calculadoraMock.dividir(6, 0)).thenThrow(DivisaoPorZeroException.class);
        int a = 6;
        int b = 0;
        calculadoraMock.dividir(a, b);
        Assert.fail();
    }

    @Test
    public void deveraLancarExcecaoPorZeroII() {
        int a = 6;
        int b = 0;
        try {
            calculadoraSpy.dividir(a, b);
            Assert.fail();
        } catch (DivisaoPorZeroException e) {
            Assert.assertEquals("A divisão não pode ser deita po 0.", e.getMessage());
        }
    }

    @Test
    public void diferencaEntreMockSpy() {

        Mockito.when(calculadoraMock.somar(1, 3)).thenReturn(5);
        Mockito.when(calculadoraSpy.somar(1, 6)).thenReturn(4);

        /**
         * O mock apenas faz a definição do cenário que criamos antes. Ou seja, o que definimos no Mock
         *
         * O Spy executa o método proriamente dido ao ser chamado.
         * O Spy também pode ser mockado para retorna valores desejados
         * O spy não funciona com interface.
         * */
        System.out.println(String.format("Mockito: %s", calculadoraMock.somar(1, 3)));
        System.out.println(String.format("Spy: %s", calculadoraSpy.somar(1, 6)));
    }


    @Test
    public void invocacaoMetodosVoid() {

        /**
         * O comportamento padrão do Mock é não executar
         * O comportamento padrão do Spy é invocar o metodo
         * */

        System.out.println("Mock");
        calculadoraMock.imprime();
        System.out.println("Spy");
        calculadoraSpy.imprime();


        /**
         * Spy, caso eu queira que o spy não execute o método
         *
         * */
        Mockito.doNothing().when(calculadoraSpy).imprime();
        calculadoraSpy.imprime();
    }

    @Test
    public void usandoDoReturn() {

        /**
         * Com spy when sendo declaro desta forma, juntamente com spy, o java
         * vai executar em ordem as seguencias de comandos dentro do metodo,
         * por isso o metodo imprime no console que esta somand dois números
         * */
        Mockito.when(calculadoraSpy.somar(1, 2)).thenReturn(3);
        calculadoraSpy.somar(1, 2);


        /**
         * Dessa forma o mockito não ira chamar o método.
         * Ele ira retorna o valor quando o metodo for chamado mas sem executa-lo.
         * Devido a esse motivo que não imprime no console o que esta dentro do metodo somar.
         * */
        Mockito.doReturn(5).when(calculadoraSpy).somar(1, 2);

        System.out.println(String.format("Spy somando : %s", calculadoraSpy.somar(1, 2)));
    }
}
