package com.tests.junit.services;

import com.tests.junit.builders.FilmeBuilder;
import com.tests.junit.daos.LocaocaoDAO;
import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.matchers.DiaSemanaMatcher;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.tests.junit.builders.FilmeBuilder.filmeBuilder;
import static com.tests.junit.builders.LocacaoBuilder.umaLocacao;
import static com.tests.junit.builders.UsuarioBuilder.umUsuario;
import static com.tests.junit.matchers.MatcherProprios.*;

public class LocacaoServiceTest {

    @InjectMocks
    private LocacaoService locacaoService;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Mock
    private SPCService spcService;
    @Mock
    private LocaocaoDAO locaocaoDAO;
    @Mock
    private NotificacaoService notificacaoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        Usuario usuario = umUsuario().agora();
        Filme filme = filmeBuilder().setPreco(5.0).umFilme();
        // ação
        Locacao locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
        //verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveraLancarExcecaoComEstoqueFilmeComZero() throws Exception {
        locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder()
                .semEstoque()
                .setPreco(5.0)
                .variosFilmes(1));
    }

    @Test
    public void deveraLancarExcecaoValidandoAMensagem() {
        try {
            locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder()
                    .semEstoque()
                    .setPreco(5.0)
                    .variosFilmes(1));
            Assert.fail("Devera lançar exceção para validar a mensagem ");
        } catch (Exception e) {
            Assert.assertEquals("O estoque do filme deve ser maior que 0", e.getMessage());
        }
    }

    @Test
    public void novaFormaVerificarExcecao() throws Exception {
        exception.expect(FilmeSemEstoqueException.class);
        locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().semEstoque().variosFilmes(1));
    }

    @Test
    public void deveraLancarExcecaoQuandoUsuarioEhNulo() throws Exception {
        try {
            locacaoService.alugarFilme(null, filmeBuilder().variosFilmes(1));
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertEquals("O usuário deve ser informado", e.getMessage());
        }
    }

    @Test
    public void deveraLancarExcecaoQuandoFilmeEhNulo() throws Exception {
        try {
            locacaoService.alugarFilme(umUsuario().agora(), null);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertEquals(e.getClass().getName(), LocadoraException.class.getName());
        }
    }

    @Test
    public void deveraLancarExcecaoQuandoFilmesEhVazio() throws Exception {
        try {
            locacaoService.alugarFilme(umUsuario().agora(), Arrays.asList());
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertEquals(e.getClass().getName(), LocadoraException.class.getName());
        }
    }

    @Test
    public void deveSomarValorDosFilmes() throws Exception {
        Usuario usuario = umUsuario().agora();
        Locacao locacao = locacaoService.alugarFilme(usuario, filmeBuilder().variosFilmes(2));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(8.0));
    }

    @Test
    public void deveriaAplicarDescronte75pctNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(3));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(11.0));
    }

    @Test
    public void deverapagar50PercNoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(4));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(13.0));
    }

    @Test
    public void deverapagar25PercNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(5));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(14.0));
    }

    @Test
    public void deveraLevarFimeSeisGratuitamente() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(6));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(14.0));
    }

    @Test
    public void naoDeveriaDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().variosFilmes(1));
        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }

//    @Test
//    @Disabled
//    public void naoDeveriaDevolverFilmeNoDomingoComAssume() throws FilmeSemEstoqueException, LocadoraException {
//        // Esse teste só será executado caso o metodo Datautils.verificarDiaDaSemana possui os parametros informados
//        // caso contrario o teste será ignorado
//        Assume.assumeTrue(DataUtils.verificarDiaDaSemana(new Date(), Calendar.SATURDAY));
//        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(1));
//        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
//        Assert.assertTrue(ehSegunda);
//    }

    /**
     * Esse mtodo só funciona se o teste for rodado no nabado, o que não queremos.
     */
    @Test
    @DisplayName("Devera colocar data de retorno para segunda feira caso a data caia no domingo ")
    public void veirificarDiaSemanaComMeuMatcher() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().variosFilmes(1));
        Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
    }

    @Test
    public void verificarDiaSemanaComMatCherProperties() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(1));
        Assert.assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
        Assert.assertThat(locacao.getDataRetorno(), caiNaSegundaFeira());
    }

    @Test
    public void verificarDataDeRetornoComMeuMathcerComDiferencaDeDias() throws FilmeSemEstoqueException, LocadoraException {
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(1));
        Assert.assertThat(locacao.getDataRetorno(), ehNoDia(1));
        Assert.assertThat(locacao.getDataLocacao(), ehHoje());
    }

    @Test
    public void deveraLancarExcecaoComUsuarioNegativado() {

        Mockito.when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
        try {
            locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().variosFilmes(1));
            Assert.fail();
        } catch (LocadoraException | FilmeSemEstoqueException exception) {
            Assert.assertEquals("O usuário está negativado", exception.getMessage());
        }
    }

    @Test
    public void deveraNotificarUsuariariosComEntregaAtrasada() {
        Usuario usuario1 = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
        Usuario usuario3 = umUsuario().comNome("Usuario 3").agora();
        List<Locacao> locacoes = Arrays.asList(
                umaLocacao().comUsuario(usuario2).agora(),
                umaLocacao().comAtraso().comUsuario(usuario1).agora(),
                umaLocacao().comAtraso().comUsuario(usuario3).agora()
        );
        Mockito.when(locaocaoDAO.buscarLocacoesAtrasadas()).thenReturn(locacoes);
        locacaoService.notificarLocacoesAtrasadas();
        Mockito.verify(notificacaoService).notificarAtraso(usuario1);
        Mockito.verify(notificacaoService, Mockito.never()).notificarAtraso(usuario2);
        Mockito.verify(notificacaoService).notificarAtraso(usuario3);
        Mockito.verifyNoMoreInteractions(notificacaoService);
    }


    @Test
    public void deveProrrogarUmaLocacao(){
        Locacao locacao = umaLocacao().agora();
        locacaoService.prorrogarLocacao(locacao,3);

        ArgumentCaptor<Locacao> argumentCaptor = ArgumentCaptor.forClass(Locacao.class);

        /** para capturar o calor que o locacaoDAO.salvar() está executando */
        Mockito.verify(locaocaoDAO).salvar(argumentCaptor.capture());
        /** é nesse objeto que temos o valor do objeto persistido*/
        Locacao locacaoRetornada = argumentCaptor.getValue();
        Assert.assertThat(locacaoRetornada.getValor(),CoreMatchers.is(12.0));
        Assert.assertThat(locacaoRetornada.getDataLocacao(),ehHoje());
        Assert.assertThat(locacaoRetornada.getDataRetorno(),ehNoDia(3));

    }
}































