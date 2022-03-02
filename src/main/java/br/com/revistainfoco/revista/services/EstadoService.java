package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class EstadoService {

    private static final List<Estado> estados = asList(
            new Estado(null, "Acre", "AC"),
            new Estado(null, "Alagoas", "AL"),
            new Estado(null, "Amapá", "AP"),
            new Estado(null, "Amazonas", "AM"),
            new Estado(null, "Bahia", "BA"),
            new Estado(null, "Ceará", "CE"),
            new Estado(null, "Espírito Santo", "ES"),
            new Estado(null, "Goiás", "GO"),
            new Estado(null, "Maranhão", "MA"),
            new Estado(null, "Mato Grosso", "MT"),
            new Estado(null, "Mato Grosso do Sul", "MS"),
            new Estado(null, "Minas Gerais", "MG"),
            new Estado(null, "Pará", "PA"),
            new Estado(null, "Paraíba", "PB"),
            new Estado(null, "Paraná", "PR"),
            new Estado(null, "Pernambuco", "PE"),
            new Estado(null, "Piauí", "PI"),
            new Estado(null, "Rio de Janeiro", "RJ"),
            new Estado(null, "Rio Grande do Norte", "RN"),
            new Estado(null, "Rio Grande do Sul", "RS"),
            new Estado(null, "Rondônia", "RO"),
            new Estado(null, "Roraima", "RR"),
            new Estado(null, "Santa Catarina", "SC"),
            new Estado(null, "São Paulo", "SP"),
            new Estado(null, "Sergipe", "SE"),
            new Estado(null, "Tocantins", "TO"),
            new Estado(null, "Distrito Federal", "DF")
    );
    private final EstadoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public EstadoService(EstadoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Estado create(Estado estado) {
        try {
            return repository.save(estado);
        } catch (DataIntegrityViolationException exception) {
            throw new EstadoJaCadastradoException("Estado já cadastrado");
        }
    }

    public List<Estado> findAll() {
        return repository.findAll();
    }

    public Estado findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
    }

    public Estado findByNomeAndUf(String nome, String uf) {
        return repository.findByNomeAndUf(nome, uf).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
    }

    public Estado update(Long id, Estado estado) {
        try {
            Estado estadoCadastrado = repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
            estadoCadastrado.setNome(estado.getNome());
            estadoCadastrado.setUf(estado.getUf());
            return repository.save(estadoCadastrado);
        } catch (DataIntegrityViolationException exception) {
            throw new EstadoJaCadastradoException("Estado já cadastrado");
        }
    }

    public void delete(Long id) {
        Estado estadoCadastrado = repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
        repository.delete(estadoCadastrado);
    }

    public void populateAddressTable() {
        repository.saveAll(estados);
    }

    public Estado toEntity(EstadoRequestDTO estadoRequestDTO) {
        return modelMapper.map(estadoRequestDTO, Estado.class);
    }

    public Estado toEntity(EstadoUpdateRequestDTO estadoUpdateRequestDTO) {
        return modelMapper.map(estadoUpdateRequestDTO, Estado.class);
    }


    public EstadoResponseDTO toResponse(Estado estado) {
        return modelMapper.map(estado, EstadoResponseDTO.class);
    }
}
