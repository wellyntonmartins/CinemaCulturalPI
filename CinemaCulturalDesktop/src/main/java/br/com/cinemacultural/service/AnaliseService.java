package br.com.cinemacultural.service;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;

import java.util.List;

public class AnaliseService {

    private final AnaliseRepository analiseRepository;
    private final FilmeRepository filmeRepository;

    public AnaliseService(AnaliseRepository analiseRepository, FilmeRepository filmeRepository) {
        this.analiseRepository = analiseRepository;
        this.filmeRepository = filmeRepository;
    }

    public List<Analise> listarPorFilme(long filmeId) {
        verificarFilme(filmeId);
        return analiseRepository.listarPorFilme(filmeId);
    }

    public Analise buscar(long id) {
        return analiseRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Análise não encontrada."));
    }

    public Analise cadastrar(long filmeId, String texto, double nota) {
        verificarFilme(filmeId);
        validarDados(texto, nota);
        return analiseRepository.salvar(new Analise(null, filmeId, texto.trim(), nota));
    }

    public Analise atualizar(long id, long filmeId, String texto, double nota) {
        Analise atual = buscar(id);
        verificarFilme(filmeId);
        validarDados(texto, nota);
        return analiseRepository.salvar(new Analise(atual.getId(), filmeId, texto.trim(), nota));
    }

    public void excluir(long id) {
        buscar(id);
        analiseRepository.excluir(id);
    }

    private void verificarFilme(long filmeId) {
        if (!filmeRepository.buscarPorId(filmeId).isPresent()) {
            throw new IllegalArgumentException("O filme selecionado não existe.");
        }
    }

    private void validarDados(String texto, double nota) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe o texto da análise.");
        }
        if (!Double.isFinite(nota) || nota < 0 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 10.");
        }
    }
}
