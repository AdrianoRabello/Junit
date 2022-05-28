package com.tests.junit.model;

import com.tests.junit.exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adriano Rabello 28/05/2022 16:16:23
 **/
public class CalculadoraTest {
    private Calculadora calculadora;

    @Before
    public void setUp() {
        this.calculadora = new Calculadora();
    }

    @Test
    public void deveraSomarDoisNumeros() {
        int resultado = this.calculadora.somar(5, 3);
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveraSubtrairDoisNumeros() {
        int a = 8;
        int b = 3;
        int resultado = calculadora.subtrair(a, b);
        Assert.assertEquals(5, resultado);
    }


    @Test(expected = DivisaoPorZeroException.class)
    public void deveraLancarExcecaoComDivisaoPorZero() throws DivisaoPorZeroException {
        int a = 6;
        int b = 0;
        calculadora.dividir(a, b);
    }

    @Test
    public void deveraLancarExcecaoPorZeraII(){
        int a = 6;
        int b = 0;
        try{
            calculadora.dividir(a,b);
            Assert.fail();
        }catch (DivisaoPorZeroException e){
            Assert.assertEquals("A divisão não pode ser deita po 0.",e.getMessage());
        }
    }
}
