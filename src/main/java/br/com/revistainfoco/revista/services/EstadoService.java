package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class EstadoService {

    //TODO: Trocar as entidades por DTO´s
    //TODO: Aplicar validacoes para os dados de entrada

    private final EstadoRepository repository;

    @Autowired
    public EstadoService(EstadoRepository repository) {
        this.repository = repository;
    }

    public Estado create(Estado estado) {
        try {
            return repository.save(estado);
        } catch (RuntimeException exception) {
            throw new EstadoJaCadastradoException("Este estado já está cadastrado");
        }
    }

    public List<Estado> readAll() {
        return repository.findAll();
    }

    public Estado readById(BigInteger id) {
        return getEstado(id);
    }

    public Estado update(BigInteger id, Estado estado) {
        Estado estadoParaAtualizar = getEstado(id);
        estadoParaAtualizar.setNome(estado.getNome());
        estadoParaAtualizar.setUf(estado.getUf());
        return repository.save(estadoParaAtualizar);
    }

    public void delete(BigInteger id) {
        getEstado(id);
        repository.deleteById(id);
    }

    private Estado getEstado(BigInteger id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
    }

}
