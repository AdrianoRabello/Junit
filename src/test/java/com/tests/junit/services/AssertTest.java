package com.tests.junit.services;

import com.tests.junit.model.Usuario;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adriano Rabello 27/05/2022 20:46:55
 **/
public class AssertTest {



    @Test
    public void test(){

        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals(1,1);

        /** para utilizar números facionados temos que colocar o delta para as casas decimais */
        Assert.assertEquals(0.51,0.51,0.01);

        /** Strings */
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertEquals("bola","bola");

        Usuario adriano1 = new Usuario("Adrano");
        Usuario adriano2 = new Usuario("Adrano");

        /** Nesse caso a veirificação esta sendo feita pelo metodo equals da classe */
        Assert.assertEquals(adriano1,adriano2);

        /** para cerificar se os objetos são as mesmas instâncias  **/
        Assert.assertNotSame(adriano1,adriano2);

        Usuario usuario = null;
        Assert.assertNull(usuario);

    }


    @Test
    public void assertThat(){

    }
}
