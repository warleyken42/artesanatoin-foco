package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EnderecoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EnderecoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.errors.exceptions.EnderecoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public EnderecoService(EnderecoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public EnderecoResponseDTO create(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco enderecoParaSalvar = modelMapper.map(enderecoRequestDTO, Endereco.class);
        Endereco enderecoSalvo = repository.save(enderecoParaSalvar);
        return modelMapper.map(enderecoSalvo, EnderecoResponseDTO.class);
    }


    public List<EnderecoResponseDTO> readAll() {
        List<EnderecoResponseDTO> enderecosCadastrados = new ArrayList<>();
        repository.findAll().forEach(endereco -> {
            EnderecoResponseDTO enderecoResponseDTO = modelMapper.map(endereco, EnderecoResponseDTO.class);
            enderecosCadastrados.add(enderecoResponseDTO);
        });
        return enderecosCadastrados;
    }

    public EnderecoResponseDTO readById(Long id) {
        Endereco endereco = getEndereco(id);
        return modelMapper.map(endereco, EnderecoResponseDTO.class);
    }

    private Endereco getEndereco(Long id) {
        return repository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado"));
    }

    public EnderecoResponseDTO update(Long id, EnderecoUpdateRequestDTO enderecoUpdateRequestDTO) {
        Endereco enderecoSalvo  = getEndereco(id);

        enderecoSalvo.setId(id);
        enderecoSalvo.setLogradouro(enderecoUpdateRequestDTO.getLogradouro());
        enderecoSalvo.setCep(enderecoUpdateRequestDTO.getCep());
        enderecoSalvo.setNumero(enderecoUpdateRequestDTO.getNumero());
        enderecoSalvo.setComplemento(enderecoUpdateRequestDTO.getComplemento());
        enderecoSalvo.setBairro(enderecoUpdateRequestDTO.getBairro());

        if (enderecoUpdateRequestDTO.getCidade() != null){
            Cidade cidade = modelMapper.map(enderecoUpdateRequestDTO.getCidade(), Cidade.class);
            enderecoSalvo.setCidade(cidade);
        }

        Endereco enderecoAtualizado = repository.save(enderecoSalvo);
        return modelMapper.map(enderecoAtualizado, EnderecoResponseDTO.class);

    }

    public void delete(Long id){
        getEndereco(id);
        repository.deleteById(id);
    }
}
