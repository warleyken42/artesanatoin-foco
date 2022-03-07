package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EnderecoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.errors.exceptions.EnderecoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;
    private final CidadeService cidadeService;
    private final ModelMapper modelMapper;


    @Autowired
    public EnderecoService(EnderecoRepository repository, CidadeService cidadeService, ModelMapper modelMapper) {
        this.repository = repository;
        this.cidadeService = cidadeService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Endereco create(Endereco endereco) {
        Cidade cidadeCadastrada;
        if (endereco.getCidade() != null && endereco.getCidade().getId() != null) {
            cidadeCadastrada = cidadeService.findById(endereco.getCidade().getId());
        } else {
            cidadeCadastrada = cidadeService.create(new Cidade(null, endereco.getCidade().getNome(), endereco.getCidade().getEstado()));
        }
        endereco.setCidade(cidadeCadastrada);
        return repository.save(endereco);
    }

    public List<Endereco> findAll() {
        return repository.findAll();
    }

    public Endereco findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado"));
    }


    public Endereco findByCep(String cep) {
        return repository.findByCep(cep).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado"));
    }

    public Endereco update(Long id, Endereco endereco) {
        Endereco enderecoCadastrado = this.findById(id);

        Cidade cidadeCadastrada;

        enderecoCadastrado.setLogradouro(enderecoCadastrado.getLogradouro());
        enderecoCadastrado.setCep(endereco.getCep());
        enderecoCadastrado.setNumero(endereco.getNumero());
        enderecoCadastrado.setComplemento(enderecoCadastrado.getComplemento());
        enderecoCadastrado.setBairro(enderecoCadastrado.getBairro());

        if (endereco.getCidade() != null && endereco.getCidade().getId() != null) {
            cidadeCadastrada = cidadeService.findById(enderecoCadastrado.getCidade().getId());
            if (cidadeCadastrada != null) {
                cidadeCadastrada.setNome(endereco.getCidade().getNome());
                cidadeCadastrada.setEstado(endereco.getCidade().getEstado());
                cidadeService.update(cidadeCadastrada.getId(), cidadeCadastrada);
            }
        } else {
            cidadeCadastrada = cidadeService.create(new Cidade(null, endereco.getCidade().getNome(), endereco.getCidade().getEstado()));
        }

        endereco.setCidade(cidadeCadastrada);

        return repository.save(endereco);
    }

    public void delete(Long id) {
        Endereco enderecoCadastrado = this.findById(id);
        repository.delete(enderecoCadastrado);
    }

    public Endereco toEntity(EnderecoRequestDTO enderecoRequestDTO) {
        return modelMapper.map(enderecoRequestDTO, Endereco.class);
    }

    public EnderecoResponseDTO toResponse(Endereco endereco) {
        return modelMapper.map(endereco, EnderecoResponseDTO.class);
    }
}
