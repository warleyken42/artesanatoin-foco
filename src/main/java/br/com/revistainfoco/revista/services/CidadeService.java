package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.CidadeRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradoException;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository repository;
    private final ModelMapper modelMapper;


    @Autowired
    public CidadeService(CidadeRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public CidadeResponseDTO create(CidadeRequestDTO cidadeRequestDTO) {
        Cidade cidade = modelMapper.map(cidadeRequestDTO, Cidade.class);
        Cidade cidadeSalvo = repository.save(cidade);
        return modelMapper.map(cidadeSalvo, CidadeResponseDTO.class);
    }

    public List<CidadeResponseDTO> readAll() {
        List<CidadeResponseDTO> cidadesCadastrados = new ArrayList<>();
        repository.findAll().forEach(cidade -> {
            CidadeResponseDTO cidadeResponseDTO = modelMapper.map(cidade, CidadeResponseDTO.class);
            cidadesCadastrados.add(cidadeResponseDTO);
        });
        return cidadesCadastrados;
    }

    public CidadeResponseDTO readById(Long id) {
        Cidade cidade = getCidade(id);
        return modelMapper.map(cidade, CidadeResponseDTO.class);
    }

    public CidadeResponseDTO update(Long id, CidadeRequestDTO cidadeRequestDTO) {
        Cidade cidadeParaAtualizar = getCidade(id);
        cidadeParaAtualizar.setNome(cidadeRequestDTO.getNome());
        Cidade cidadeAtualizado = repository.save(cidadeParaAtualizar);
        return modelMapper.map(cidadeAtualizado, CidadeResponseDTO.class);
    }

    public void delete(Long id) {
        getCidade(id);
        repository.deleteById(id);
    }

    private Cidade getCidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradoException("Cidade não encontrada"));
    }

}
