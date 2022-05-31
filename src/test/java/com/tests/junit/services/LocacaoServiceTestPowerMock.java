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
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tests.junit.builders.FilmeBuilder.filmeBuilder;
import static com.tests.junit.builders.LocacaoBuilder.umaLocacao;
import static com.tests.junit.builders.UsuarioBuilder.umUsuario;
import static com.tests.junit.matchers.MatcherProprios.*;
import static org.hamcrest.Matchers.is;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class,DataUtils.class,DiaSemanaMatcher.class})
public class LocacaoServiceTestPowerMock {

    @InjectMocks
    private LocacaoService locacaoService;

    @InjectMocks
    private LocacaoService locacaoServiceSpy;

    @Mock
    private SPCService spcService;

    @Mock
    private LocaocaoDAO locaocaoDAO;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();





    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        locacaoServiceSpy = PowerMockito.spy(locacaoServiceSpy);
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
    public void naoDeveriaDevolverFilmeNoDomingo() throws Exception {
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,28);
        calendar.set(Calendar.MONTH,Calendar.MAY);
        calendar.set(Calendar.YEAR,2022);
        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(calendar.getTime());
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().variosFilmes(1));
        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }

    @Test
    public void naoDeveriaDevolverFilmeNoDomingoComAssume() throws Exception {
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,28);
        calendar.set(Calendar.MONTH,Calendar.MAY);
        calendar.set(Calendar.YEAR,2022);
        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(calendar.getTime());
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(1));
        boolean ehSegunda = DataUtils.verificarDiaDaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }

    /**
     * Esse mtodo só funciona se o teste for rodado no nabado, o que não queremos.
     */
    @Test
    @DisplayName("Devera colocar data de retorno para segunda feira caso a data caia no domingo ")
    public void veirificarDiaSemanaComMeuMatcher() throws Exception {
        

        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,28);
        calendar.set(Calendar.MONTH,Calendar.MAY);
        calendar.set(Calendar.YEAR,2022);
        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(calendar.getTime());
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmeBuilder().variosFilmes(1));
        Assert.assertThat(locacao.getDataRetorno(),new DiaSemanaMatcher(Calendar.MONDAY));
    }

    @Test
    public void verificarDiaSemanaComMatCherProperties() throws Exception {
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,28);
        calendar.set(Calendar.MONTH,Calendar.MAY);
        calendar.set(Calendar.YEAR,2022);
        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(calendar.getTime());
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), FilmeBuilder.filmeBuilder().variosFilmes(1));
        Assert.assertThat(locacao.getDataRetorno(), caiNaSegundaFeira());
    }


}































