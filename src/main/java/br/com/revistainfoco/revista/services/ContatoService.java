package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.ContatoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.ContatoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Contato;
import br.com.revistainfoco.revista.errors.exceptions.ContatoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.ContatoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    private final ContatoRepository repository;
    private final ModelMapper modelMapper;


    @Autowired
    public ContatoService(ContatoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Contato create(Contato contato) {
        return repository.save(contato);
    }

    public List<Contato> findAll() {
        return repository.findAll();
    }

    public Contato findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ContatoNaoEncontradoException("Contato não encontrado"));
    }

    public Optional<Contato> findByCelular(String celular) {
        return repository.findByCelular(celular);
    }

    public Contato update(Long id, Contato contato) {
        Contato contatoCadastrado = repository.findById(id).orElseThrow(() -> new ContatoNaoEncontradoException("Contato não encontrado"));

        contatoCadastrado.setEmail(contato.getEmail());
        contatoCadastrado.setCelular(contato.getCelular());
        contatoCadastrado.setNome(contato.getNome());
        contatoCadastrado.setSobrenome(contato.getSobrenome());

        return repository.save(contatoCadastrado);
    }

    public Contato toEntity(ContatoRequestDTO contatoRequestDTO) {
        return modelMapper.map(contatoRequestDTO, Contato.class);
    }

    public ContatoResponseDTO toResponse(Contato contato) {
        return modelMapper.map(contato, ContatoResponseDTO.class);
    }
}
