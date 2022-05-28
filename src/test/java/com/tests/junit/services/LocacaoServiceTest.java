package com.tests.junit.services;

import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

public class LocacaoServiceTest {


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deverarAlugarFilme() throws Exception {
        // cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(1)
                .build();
        // ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);
        //verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }

    @Test(expected = Exception.class)
    public void deveraLancarExcecaoComEstoqueFilmeComZero() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void deveraLancarExcecaoValidandoAMensagem() {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();

        try {
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Devera lançar exceção para validar a mensagem ");
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("Estoque é 0"));
            Assert.assertEquals("Estoque é 0", e.getMessage());
        }
    }


    @Test
    public void novaFormaVerificarExcecao() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();

        exception.expect(Exception.class);
        locacaoService.alugarFilme(usuario, filme);

    }


}
