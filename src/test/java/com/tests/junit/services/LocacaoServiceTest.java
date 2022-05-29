package com.tests.junit.services;

import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.matchers.DiaSemanaMatcher;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.jupiter.api.Disabled;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tests.junit.matchers.MatcherProprios.*;

public class LocacaoServiceTest {


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private LocacaoService locacaoService;

    @Before
    public void setUp() {
        this.locacaoService = new LocacaoService();
    }

    @After
    public void tearDown() {
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
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
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
        locacaoService.alugarFilme(usuario, Arrays.asList(filme));
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
            locacaoService.alugarFilme(usuario, Arrays.asList(filme));
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
        locacaoService.alugarFilme(usuario, Arrays.asList(filme));
    }

    @Test
    public void deveraLancarExcecaoQuandoUsuarioRhNulo() throws Exception {
        Filme filme = new Filme().builder()
                .precoLocacao(5.0)
                .nome("Filme 01")
                .estoque(1)
                .build();
        try {
            locacaoService.alugarFilme(null, Arrays.asList(filme));
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

        locacaoService.alugarFilme(usuario, Arrays.asList(filme1, filme2));

    }


    @Test
    public void deveriaAplicarDescronte75pctNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Adriano");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 01", 1, 4.0),
                new Filme("Filme 02", 1, 4.0),
                new Filme("Filme 03", 1, 4.0)
        );
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(11.0));
    }

    @Test
    public void deverapagar50PercNoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Adriano");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 01", 1, 4.0),
                new Filme("Filme 02", 1, 4.0),
                new Filme("Filme 03", 1, 4.0),
                new Filme("Filme 03", 1, 4.0)
        );
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(13.0));
    }


    @Test
    public void deverapagar25PercNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Adriano");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 01", 1, 4.0),
                new Filme("Filme 02", 1, 4.0),
                new Filme("Filme 03", 1, 4.0),
                new Filme("Filme 04", 1, 4.0),
                new Filme("Filme 05", 1, 4.0)
        );
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(14.0));
    }


    @Test
    public void deveraLevarFimeSeisGratuitamente() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Adriano");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 01", 1, 4.0),
                new Filme("Filme 02", 1, 4.0),
                new Filme("Filme 03", 1, 4.0),
                new Filme("Filme 04", 1, 4.0),
                new Filme("Filme 05", 1, 4.0),
                new Filme("Filme 06", 1, 4.0)
        );
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(14.0));
    }

    @Test
    public void naoDeveriaDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Filme filme = new Filme("Filme 01 ", 1, 4.0);
        Usuario usuario = new Usuario("Adriano Rabello");
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }

    @Test
    @Disabled
    public void naoDeveriaDevolverFilmeNoDomingoComAssume() throws FilmeSemEstoqueException, LocadoraException {
        // Esse teste só será executado caso o metodo Datautils.verificarDiaDaSemana possui os parametros informados
        // caso contrario o teste será ignorado
        Assume.assumeTrue(DataUtils.verificarDiaDaSemana(new Date(), Calendar.SATURDAY));
        Filme filme = new Filme("Filme 01 ", 1, 4.0);
        Usuario usuario = new Usuario("Adriano Rabello");
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }



    /**
     * Esse mtodo só funciona se o teste for rodado no nabado, o que não queremos. */
    @Test
    @Disabled
    public void veirificarDiaSemanaComMeuMatcher() throws FilmeSemEstoqueException, LocadoraException {
        Filme filme = new Filme("Filme 01 ", 1, 4.0);
        Usuario usuario = new Usuario("Adriano Rabello");
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
    }

    @Test
    public void verificarDiaSemanaComMatCherProperties() throws FilmeSemEstoqueException, LocadoraException {
        Filme filme = new Filme("Filme 01 ", 1, 4.0);
        Usuario usuario = new Usuario("Adriano Rabello");
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        Assert.assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
        Assert.assertThat(locacao.getDataRetorno(), caiNaSegundaFeira());
    }

    @Test
    public void verificarDataDeRetornoComMeuMathcerComDiferencaDeDias() throws FilmeSemEstoqueException, LocadoraException{
        Filme filme = new Filme("Filme 01 ", 1, 4.0);
        Usuario usuario = new Usuario("Adriano Rabello");
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        Assert.assertThat(locacao.getDataRetorno(),ehNoDia(1));
        Assert.assertThat(locacao.getDataLocacao(),ehHoje());
    }
}































