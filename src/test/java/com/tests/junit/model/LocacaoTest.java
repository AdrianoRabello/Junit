package com.tests.junit.model;

import com.tests.junit.services.LocacaoService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;


public class LocacaoTest {


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void testeDaLocacaoComAssertThat() {

        LocacaoService locacaoService = new LocacaoService();
        Usuario adriano = new Usuario().builder().nome("Adriano").build();
        Filme filme = new Filme().builder()
                .nome("FIlme 01 ")
                .estoque(2)
                .precoLocacao(5.0)
                .build();

        Locacao locacao = null;
        try {
            locacao = locacaoService.alugarFilme(adriano, Arrays.asList(filme));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        assertThat(locacao.getValor(), is(5.0));
        assertThat(locacao.getValor(), not(6.0));


        // o error collector coleta o error de todos as assertivas para poder analizar onde
        // o código está errado, mesmo que no primeiro assert o código dê errado
        error.checkThat(locacao.getValor(),is(2.0));
        error.checkThat(locacao.getValor(),not(5.0));

    }



}
