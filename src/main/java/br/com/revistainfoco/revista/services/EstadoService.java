package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EstadoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public EstadoResponseDTO create(EstadoRequestDTO estadoRequestDTO) {
        Estado estado = modelMapper.map(estadoRequestDTO, Estado.class);
        Estado estadoSalvo = repository.save(estado);
        return modelMapper.map(estadoSalvo, EstadoResponseDTO.class);
    }

    public List<EstadoResponseDTO> readAll() {
        List<EstadoResponseDTO> estadosCadastrados = new ArrayList<>();
        repository.findAll().forEach(estado -> {
            EstadoResponseDTO estadoResponseDTO = modelMapper.map(estado, EstadoResponseDTO.class);
            estadosCadastrados.add(estadoResponseDTO);
        });
        return estadosCadastrados;
    }

    public EstadoResponseDTO readById(Long id) {
        Estado estado = findById(id);
        return modelMapper.map(estado, EstadoResponseDTO.class);
    }

    public EstadoResponseDTO update(Long id, EstadoUpdateRequestDTO estadoUpdateRequestDTO) {
        Estado estadoSalvo = findById(id);

        estadoSalvo.setId(id);
        estadoSalvo.setNome(estadoUpdateRequestDTO.getNome());
        estadoSalvo.setUf(estadoUpdateRequestDTO.getUf());

        Estado estadoAtualizado = repository.save(estadoSalvo);

        return modelMapper.map(estadoAtualizado, EstadoResponseDTO.class);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public Estado findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
    }

    public void populateAddressTable() {
        repository.saveAll(estados);
    }
}
