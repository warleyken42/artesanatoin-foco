package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstadoService {

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
        Estado estado = getEstado(id);
        return modelMapper.map(estado, EstadoResponseDTO.class);
    }

    public EstadoResponseDTO update(Long id, EstadoRequestDTO estadoRequestDTO) {
        Estado estadoParaAtualizar = getEstado(id);
        estadoParaAtualizar.setNome(estadoRequestDTO.getNome());
        estadoParaAtualizar.setUf(estadoRequestDTO.getUf());
        Estado estadoAtualizado = repository.save(estadoParaAtualizar);
        return modelMapper.map(estadoAtualizado, EstadoResponseDTO.class);
    }

    public void delete(Long id) {
        getEstado(id);
        repository.deleteById(id);
    }

    private Estado getEstado(Long id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException("Estado não encontrado"));
    }

}
