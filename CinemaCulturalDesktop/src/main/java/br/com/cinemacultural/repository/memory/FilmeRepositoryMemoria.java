package br.com.cinemacultural.repository.memory;

import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.repository.FilmeRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FilmeRepositoryMemoria implements FilmeRepository {

    private final Map<Long, Filme> filmes = new LinkedHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    @Override
    public List<Filme> listar() {
        return new ArrayList<>(filmes.values());
    }

    @Override
    public Optional<Filme> buscarPorId(long id) {
        return Optional.ofNullable(filmes.get(id));
    }

    @Override
    public Filme salvar(Filme filme) {
        if (filme.getId() == null || filme.getId() <= 0) {
            filme.setId(proximoId.getAndIncrement());
        }
        filmes.put(filme.getId(), filme);
        return filme;
    }

    @Override
    public void excluir(long id) {
        filmes.remove(id);
    }
}
