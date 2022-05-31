package com.tests.junit.services;

import com.tests.junit.builders.UsuarioBuilder;
import com.tests.junit.daos.LocaocaoDAO;
import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Adriano Rabello 29/05/2022 08:51:53
 **/

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @InjectMocks
    public LocacaoService locacaoService;

    @Mock
    public SPCService spcService;

    @Mock
    public LocaocaoDAO locaocaoDAO;

    @Parameterized.Parameter
    public  List<Filme> filmes;
    @Parameterized.Parameter(value = 1)
    public double valorFilmes;
    @Parameterized.Parameter(value = 2)
    public String descricao;


    private static Filme filme1 = new Filme("Filme 1 ", 1, 4.0);
    private static Filme filme2 = new Filme("Filme 2 ", 1, 4.0);
    private static Filme filme3 = new Filme("Filme 3 ", 1, 4.0);
    private static Filme filme4 = new Filme("Filme 4 ", 1, 4.0);
    private static Filme filme5 = new Filme("Filme 5 ", 1, 4.0);
    private static Filme filme6 = new Filme("Filme 6 ", 1, 4.0);


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    /** Esse metodo deve ser sempre estatico pois o Junit vai insancia-lo antes mesmo da criação da classe */
    @Parameterized.Parameters(name = "Teste {index} = {2}")
    public static Collection<Object[]> getPametros() {

        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1), 4, "1 filme : Sem desconto "},
                {Arrays.asList(filme1, filme2), 8.0, "2 filmes : Sem desconto "},
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 filmes : 25% de descontro no terceiro filme "},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0,"4 filmes : 50% de descontro no quarto filme "},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0,"5 filmes : 75% de descontro no 5 filme"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0,"6 filmes : 100% de descontro no 6 filme "},
        });
    }


    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
        // canario
        Usuario usuario = new Usuario("Usuario 1 ");
        // acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        // verificaoção
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(valorFilmes));
        System.out.println(descricao);
    }

    @Test
    public void print(){
        System.out.println(valorFilmes);
    }
}
