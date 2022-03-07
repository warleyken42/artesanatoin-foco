package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradaException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    private final CidadeRepository repository;
    private final EstadoService estadoService;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeService(CidadeRepository repository, EstadoService estadoService, ModelMapper modelMapper) {
        this.repository = repository;
        this.estadoService = estadoService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Cidade create(Cidade cidade) {
        Optional<Cidade> cidadeCadastrada = repository.findByNome(cidade.getNome());
        Estado estadoCadastrado = estadoService.create(cidade.getEstado());

        if (cidadeCadastrada.isPresent()) {
            cidadeCadastrada.get().setEstado(estadoCadastrado);
            return cidadeCadastrada.get();
        }

        cidade.setEstado(estadoCadastrado);

        return repository.save(cidade);
    }

    public List<Cidade> findAll() {
        return repository.findAll();
    }

    public Cidade findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrada"));
    }

    public Optional<Cidade> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    public Cidade update(Long id, Cidade cidade) {
        Cidade cidadeCadastrada = this.findById(id);

        Optional<Estado> estadoCadastrado = estadoService.findByNomeAndUf(cidade.getEstado().getNome(), cidade.getEstado().getUf());

        if (estadoCadastrado.isPresent()) {
            Estado estado = estadoCadastrado.get();
            estado.setUf(cidade.getEstado().getUf());
            estado.setNome(cidade.getEstado().getNome());
            Estado estadoCriado = estadoService.create(estado);
            cidadeCadastrada.setEstado(estadoCriado);
        } else {
            Estado estado = estadoService.create(cidade.getEstado());
            cidadeCadastrada.setEstado(estado);
        }

        cidadeCadastrada.setNome(cidade.getNome());

        return repository.save(cidadeCadastrada);
    }

    public void delete(Long id) {
        Cidade cidadeCadastrada = this.findById(id);
        repository.delete(cidadeCadastrada);
    }

    public Cidade toEntity(CidadeRequestDTO cidadeRequestDTO) {
        return modelMapper.map(cidadeRequestDTO, Cidade.class);
    }

    public CidadeResponseDTO toResponse(Cidade cidade) {
        return modelMapper.map(cidade, CidadeResponseDTO.class);
    }
}
