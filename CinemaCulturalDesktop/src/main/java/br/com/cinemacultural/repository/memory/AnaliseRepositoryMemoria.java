package br.com.cinemacultural.repository.memory;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.repository.AnaliseRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AnaliseRepositoryMemoria implements AnaliseRepository {

    private final Map<Long, Analise> analises = new LinkedHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    @Override
    public List<Analise> listarPorFilme(long filmeId) {
        return analises.values().stream()
                .filter(analise -> analise.getFilmeId() == filmeId)
                .sorted(Comparator.comparing(Analise::getId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Analise> buscarPorId(long id) {
        return Optional.ofNullable(analises.get(id));
    }

    @Override
    public Analise salvar(Analise analise) {
        if (analise.getId() == null || analise.getId() <= 0) {
            analise.setId(proximoId.getAndIncrement());
        }
        analises.put(analise.getId(), analise);
        return analise;
    }

    @Override
    public void excluir(long id) {
        analises.remove(id);
    }

    @Override
    public void excluirPorFilme(long filmeId) {
        analises.values().removeIf(analise -> analise.getFilmeId() == filmeId);
    }
}
