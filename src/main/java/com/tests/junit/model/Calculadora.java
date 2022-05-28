package com.tests.junit.model;

import com.tests.junit.exceptions.DivisaoPorZeroException;

import java.util.Objects;

/**
 * @author Adriano Rabello 28/05/2022 16:18:49
 **/
public class Calculadora {
    public int somar(int a, int b) {

        return a + b;
    }

    public int subtrair(int a, int b) {

        return a - b ;
    }

    public int dividir(int a, int b) throws DivisaoPorZeroException {

        if(Objects.isNull(b) || b < 1){
            throw new DivisaoPorZeroException("A divisão não pode ser deita po 0.");
        }

        return a/b;
    }
}
