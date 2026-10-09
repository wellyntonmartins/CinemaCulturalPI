package br.com.cinemacultural.service;

import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;

import java.time.Year;
import java.util.List;

public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final AnaliseRepository analiseRepository;

    public FilmeService(FilmeRepository filmeRepository, AnaliseRepository analiseRepository) {
        this.filmeRepository = filmeRepository;
        this.analiseRepository = analiseRepository;
    }

    public List<Filme> listar() {
        return filmeRepository.listar();
    }

    public Filme buscar(long id) {
        return filmeRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado."));
    }

    public Filme cadastrar(String titulo, String sinopse, String genero, int anoLancamento) {
        validarDados(titulo, sinopse, genero, anoLancamento);
        return filmeRepository.salvar(new Filme(null, titulo.trim(), sinopse.trim(), genero.trim(), anoLancamento));
    }

    public Filme atualizar(long id, String titulo, String sinopse, String genero, int anoLancamento) {
        buscar(id);
        validarDados(titulo, sinopse, genero, anoLancamento);
        return filmeRepository.salvar(new Filme(id, titulo.trim(), sinopse.trim(), genero.trim(), anoLancamento));
    }

    public void excluir(long id) {
        buscar(id);
        analiseRepository.excluirPorFilme(id);
        filmeRepository.excluir(id);
    }

    private void validarDados(String titulo, String sinopse, String genero, int anoLancamento) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Informe o título do filme.");
        }
        if (sinopse == null || sinopse.isBlank()) {
            throw new IllegalArgumentException("Informe a sinopse do filme.");
        }
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("Informe o gênero do filme.");
        }
        if (anoLancamento < 1888 || anoLancamento > Year.now().getValue() + 10) {
            throw new IllegalArgumentException("Informe um ano de lançamento válido.");
        }
    }
}
