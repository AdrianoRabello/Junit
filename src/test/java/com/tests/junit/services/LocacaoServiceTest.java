package com.tests.junit.services;

import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;
import org.junit.Assert;
import org.junit.Test;

public class LocacaoServiceTest {


    @Test
    public void teste() {

        // cenario
        LocacaoService locacaoService = new LocacaoService();

        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(2)
                .build();

        // ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        //verificação
        Assert.assertTrue(locacao.getValor() == 5.0);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

    }

}
