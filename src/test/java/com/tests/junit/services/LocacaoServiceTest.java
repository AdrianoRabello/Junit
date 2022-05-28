package com.tests.junit.services;

import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

public class LocacaoServiceTest {


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private LocacaoService locacaoService;

    @Before
    public void setUp() {
        System.out.println("Before");
        this.locacaoService = new LocacaoService();
    }

    @After
    public void tearDown() {
        System.out.println("After");
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("before class method");
    }
    @AfterClass
    public static void afterClass() {

        System.out.println("after class method");
    }
    @Test
    public void deverarAlugarFilme() throws Exception {
        // cenario
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(1)
                .build();
        // ação
        Locacao locacao = locacaoService.alugarFilme(usuario,  Arrays.asList(filme));
        //verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
    }
    @Test(expected = FilmeSemEstoqueException.class)
    public void deveraLancarExcecaoComEstoqueFilmeComZero() throws Exception {
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();
        locacaoService.alugarFilme(usuario,  Arrays.asList(filme));
    }
    @Test
    public void deveraLancarExcecaoValidandoAMensagem() {
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();

        try {
            locacaoService.alugarFilme(usuario,  Arrays.asList(filme));
            Assert.fail("Devera lançar exceção para validar a mensagem ");
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("O estoque do filme deve ser maior que 0"));
            Assert.assertEquals("O estoque do filme deve ser maior que 0", e.getMessage());
        }
    }
    @Test
    public void novaFormaVerificarExcecao() throws Exception {
        Usuario usuario = new Usuario().builder().nome("Adriano Rabello").build();
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(0)
                .build();
        exception.expect(FilmeSemEstoqueException.class);
        locacaoService.alugarFilme(usuario,  Arrays.asList(filme));
    }
    @Test
    public void deveraLancarExcecaoQuandoUsuarioRhNulo() throws Exception {
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(1)
                .build();
        try {
            locacaoService.alugarFilme(null,  Arrays.asList(filme));
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertEquals("O usuário deve ser informado", e.getMessage());
        }
    }

    @Test
    public void deveraLancarExcecaoQuandoEstoqueEhNulo() throws Exception {
        Usuario usuario = new Usuario("adriano");
        try {
            locacaoService.alugarFilme(usuario, null);
            Assert.fail();
        } catch (LocadoraException e) {

            Assert.assertEquals(e.getClass().getName(), LocadoraException.class.getName());
        }
    }


    @Test
    public void deveSomarValorDosFilmes() throws Exception {
        Filme filme1 = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(1)
                .build();

        Filme filme2 = new Filme().builder()
                .precoLocacao(3.0)
                .nome("Filme 01")
                .estoque(1)
                .build();

        Usuario usuario = new Usuario("Adriano Rabello");

        locacaoService.alugarFilme(usuario,Arrays.asList(filme1,filme2));

    }
}
