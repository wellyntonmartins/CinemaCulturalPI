package br.com.cinemacultural.service;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;
import br.com.cinemacultural.repository.memory.AnaliseRepositoryMemoria;
import br.com.cinemacultural.repository.memory.FilmeRepositoryMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmeServiceTest {

    private FilmeRepository filmeRepository;
    private AnaliseRepository analiseRepository;
    private FilmeService filmeService;

    @BeforeEach
    void setUp() {
        filmeRepository = new FilmeRepositoryMemoria();
        analiseRepository = new AnaliseRepositoryMemoria();
        filmeService = new FilmeService(filmeRepository, analiseRepository);
    }

    @Test
    void cadastraFilmeValidoERemoveEspacosExternos() {
        Filme filme = filmeService.cadastrar(
                "  Interestelar  ",
                "  Uma missão espacial.  ",
                "  Ficção científica  ",
                2014
        );

        assertNotNull(filme.getId());
        assertEquals("Interestelar", filme.getTitulo());
        assertEquals("Uma missão espacial.", filme.getSinopse());
        assertEquals("Ficção científica", filme.getGenero());
    }

    @Test
    void listaFilmesCadastrados() {
        filmeService.cadastrar("Interestelar", "Uma missão espacial.", "Ficção científica", 2014);
        filmeService.cadastrar("O Poderoso Chefão", "Uma família mafiosa.", "Drama", 1972);

        assertEquals(2, filmeService.listar().size());
    }

    @Test
    void rejeitaTituloVazio() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.cadastrar("  ", "Sinopse válida", "Drama", 2020)
        );

        assertEquals("Informe o título do filme.", erro.getMessage());
    }

    @Test
    void rejeitaSinopseVazia() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.cadastrar("Filme", " ", "Drama", 2020)
        );

        assertEquals("Informe a sinopse do filme.", erro.getMessage());
    }

    @Test
    void rejeitaGeneroVazio() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.cadastrar("Filme", "Sinopse válida", " ", 2020)
        );

        assertEquals("Informe o gênero do filme.", erro.getMessage());
    }

    @Test
    void rejeitaAnoAnteriorAoInicioDoCinema() {
        assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.cadastrar("Filme", "Sinopse válida", "Drama", 1887)
        );
    }

    @Test
    void rejeitaAnoMuitoNoFuturo() {
        int anoInvalido = Year.now().getValue() + 11;

        assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.cadastrar("Filme", "Sinopse válida", "Drama", anoInvalido)
        );
    }

    @Test
    void atualizaFilmeExistente() {
        Filme filme = filmeService.cadastrar("Interestelar", "Sinopse antiga", "Ficção científica", 2014);

        Filme atualizado = filmeService.atualizar(
                filme.getId(),
                "Interestelar",
                "Sinopse atualizada",
                "Ficção científica",
                2014
        );

        assertEquals(filme.getId(), atualizado.getId());
        assertEquals("Sinopse atualizada", filmeService.buscar(filme.getId()).getSinopse());
    }

    @Test
    void rejeitaBuscaDeFilmeInexistente() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> filmeService.buscar(999L)
        );

        assertEquals("Filme não encontrado.", erro.getMessage());
    }

    @Test
    void excluirFilmeTambemRemoveAnalisesRelacionadas() {
        Filme filme = filmeService.cadastrar("Interestelar", "Uma missão espacial.", "Ficção científica", 2014);
        analiseRepository.salvar(new Analise(null, filme.getId(), "Boa história.", 9.0));

        filmeService.excluir(filme.getId());

        assertTrue(filmeRepository.buscarPorId(filme.getId()).isEmpty());
        assertTrue(analiseRepository.listarPorFilme(filme.getId()).isEmpty());
        assertFalse(filmeService.listar().stream().anyMatch(item -> item.getId().equals(filme.getId())));
    }

    @Test
    void rejeitaExclusaoDeFilmeInexistente() {
        assertThrows(IllegalArgumentException.class, () -> filmeService.excluir(999L));
    }
}
