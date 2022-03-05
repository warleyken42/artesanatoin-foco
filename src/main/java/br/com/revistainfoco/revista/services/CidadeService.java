package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.CidadeUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradaException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Cidade create(Cidade cidade) {
        Estado estadoCadastrado = estadoService.findByNomeAndUf(cidade.getEstado().getNome(), cidade.getEstado().getUf());
        cidade.setEstado(estadoCadastrado);

        Cidade cidadeCadastrada = repository.findByNome(cidade.getNome());

        if (cidadeCadastrada != null) {
            return cidadeCadastrada;
        }

        return repository.save(cidade);
    }

    public List<Cidade> findAll() {
        return repository.findAll();
    }

    public Cidade findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrada"));
    }

    public Cidade findByNome(String nome) {
        return repository.findByNome(nome);
    }

    public Cidade update(Long id, Cidade cidade) {
        Cidade cidadeCadastrada = repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrado"));
        Estado estadoCadastrado = estadoService.findByNomeAndUf(cidade.getEstado().getNome(), cidade.getEstado().getUf());

        cidadeCadastrada.setNome(cidade.getNome());
        cidadeCadastrada.setEstado(estadoCadastrado);

        return repository.save(cidadeCadastrada);
    }

    public void delete(Long id) {
        Cidade cidadeCadastrada = repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrado"));
        repository.delete(cidadeCadastrada);
    }

    public Cidade toEntity(CidadeRequestDTO cidadeRequestDTO) {
        return modelMapper.map(cidadeRequestDTO, Cidade.class);
    }

    public Cidade toEntity(CidadeUpdateRequestDTO cidadeUpdateRequestDTO) {
        return modelMapper.map(cidadeUpdateRequestDTO, Cidade.class);
    }

    public CidadeResponseDTO toResponse(Cidade cidade) {
        return modelMapper.map(cidade, CidadeResponseDTO.class);
    }

}
