package br.com.cinemacultural.service;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;
import br.com.cinemacultural.repository.memory.AnaliseRepositoryMemoria;
import br.com.cinemacultural.repository.memory.FilmeRepositoryMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnaliseServiceTest {

    private FilmeRepository filmeRepository;
    private AnaliseRepository analiseRepository;
    private FilmeService filmeService;
    private AnaliseService analiseService;
    private Filme filme;

    @BeforeEach
    void setUp() {
        filmeRepository = new FilmeRepositoryMemoria();
        analiseRepository = new AnaliseRepositoryMemoria();
        filmeService = new FilmeService(filmeRepository, analiseRepository);
        analiseService = new AnaliseService(analiseRepository, filmeRepository);
        filme = filmeService.cadastrar(
                "Interestelar",
                "Uma missão espacial.",
                "Ficção científica",
                2014
        );
    }

    @Test
    void cadastraAnaliseValida() {
        Analise analise = analiseService.cadastrar(filme.getId(), "História envolvente.", 9.5);

        assertNotNull(analise.getId());
        assertEquals(filme.getId(), analise.getFilmeId());
        assertEquals("História envolvente.", analise.getTexto());
        assertEquals(9.5, analise.getNota(), 0.001);
    }

    @Test
    void aceitaNotasNosLimitesZeroEDez() {
        Analise notaMinima = analiseService.cadastrar(filme.getId(), "Análise com nota mínima.", 0.0);
        Analise notaMaxima = analiseService.cadastrar(filme.getId(), "Análise com nota máxima.", 10.0);

        assertEquals(0.0, notaMinima.getNota(), 0.001);
        assertEquals(10.0, notaMaxima.getNota(), 0.001);
    }

    @Test
    void rejeitaNotaNegativa() {
        assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(filme.getId(), "Nota inválida.", -0.1)
        );
    }

    @Test
    void rejeitaNotaAcimaDeDez() {
        assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(filme.getId(), "Nota inválida.", 10.1)
        );
    }

    @Test
    void rejeitaNotaNaoFinita() {
        assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(filme.getId(), "Nota inválida.", Double.NaN)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(filme.getId(), "Nota inválida.", Double.POSITIVE_INFINITY)
        );
    }

    @Test
    void rejeitaTextoVazio() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(filme.getId(), "  ", 8.0)
        );

        assertEquals("Informe o texto da análise.", erro.getMessage());
    }

    @Test
    void rejeitaAnaliseParaFilmeInexistente() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.cadastrar(999L, "Análise válida.", 8.0)
        );

        assertEquals("O filme selecionado não existe.", erro.getMessage());
    }

    @Test
    void listaSomenteAnalisesDoFilmeSelecionado() {
        Filme outroFilme = filmeService.cadastrar("O Poderoso Chefão", "Uma família mafiosa.", "Drama", 1972);
        analiseService.cadastrar(filme.getId(), "Análise A.", 8.0);
        analiseService.cadastrar(outroFilme.getId(), "Análise B.", 7.0);

        assertEquals(1, analiseService.listarPorFilme(filme.getId()).size());
        assertEquals("Análise A.", analiseService.listarPorFilme(filme.getId()).get(0).getTexto());
    }

    @Test
    void atualizaTextoENotaDaAnalise() {
        Analise analise = analiseService.cadastrar(filme.getId(), "Texto antigo.", 7.0);

        Analise atualizada = analiseService.atualizar(
                analise.getId(),
                filme.getId(),
                "  Texto atualizado.  ",
                9.0
        );

        assertEquals(analise.getId(), atualizada.getId());
        assertEquals("Texto atualizado.", atualizada.getTexto());
        assertEquals(9.0, atualizada.getNota(), 0.001);
    }

    @Test
    void rejeitaBuscaDeAnaliseInexistente() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> analiseService.buscar(999L)
        );

        assertEquals("Análise não encontrada.", erro.getMessage());
    }

    @Test
    void excluiAnaliseExistente() {
        Analise analise = analiseService.cadastrar(filme.getId(), "Texto para excluir.", 8.0);

        analiseService.excluir(analise.getId());

        assertTrue(analiseRepository.buscarPorId(analise.getId()).isEmpty());
    }
}
