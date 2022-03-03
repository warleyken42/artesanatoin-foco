package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.AnuncianteRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.AnuncianteUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncianteResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anunciante;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.AnuncianteNaoEncontradoException;
import br.com.revistainfoco.revista.errors.exceptions.EnderecoNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncianteRepository;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncianteService {

    private final AnuncianteRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final ModelMapper modelMapper;
    private final EnderecoService enderecoService;

    @Autowired
    public AnuncianteService(AnuncianteRepository repository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository, ModelMapper modelMapper, EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.modelMapper = modelMapper;
        this.enderecoService = enderecoService;
    }

    @Transactional
    public Anunciante create(Anunciante anunciante) {
        Endereco enderecoCadastrado;
        if (anunciante.getEndereco() != null  && anunciante.getEndereco().getId() != null){
            enderecoCadastrado = enderecoService.findById(anunciante.getEndereco().getId());
        } else {
            enderecoCadastrado = enderecoService.create(new Endereco(null, "Rua Cidade Lion", new Cidade(1L, "Guarulhos", new Estado(1L, "São Paulo", "SP")), "74185296", "184", "Apto: 32", "Jardim Anny"));
        }
        anunciante.setEndereco(enderecoCadastrado);
        return repository.save(anunciante);
    }

    public List<Anunciante> findAll() {
        return repository.findAll();
    }

    public Anunciante findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));
    }

    public Anunciante update(Long id, Anunciante anunciante) {
        Anunciante anuncianteCadastrado = repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));

        Endereco enderecoCadastrado;

        anuncianteCadastrado.setCnpj(anuncianteCadastrado.getCnpj());
        anuncianteCadastrado.setRazaoSocial(anuncianteCadastrado.getRazaoSocial());
        anuncianteCadastrado.setNomeFantasia(anuncianteCadastrado.getNomeFantasia());
        anuncianteCadastrado.setEmail(anuncianteCadastrado.getEmail());
        anuncianteCadastrado.setSite(anunciante.getSite());

        if (anunciante.getEndereco() != null && anunciante.getEndereco().getId() != null){
            enderecoCadastrado = enderecoService.findById(anuncianteCadastrado.getEndereco().getId());
            if (enderecoCadastrado != null) {
                enderecoCadastrado.setLogradouro(anunciante.getEndereco().getLogradouro());
                enderecoCadastrado.setCep(anunciante.getEndereco().getCep());
                enderecoCadastrado.setNumero(anunciante.getEndereco().getNumero());
                enderecoCadastrado.setComplemento(anunciante.getEndereco().getComplemento());
                enderecoCadastrado.setBairro(anunciante.getEndereco().getBairro());
            }
        } else {
            enderecoCadastrado = enderecoService.create(new Endereco(null, anunciante.getEndereco().getLogradouro(), anunciante.getEndereco().getCidade(), anunciante.getEndereco().getCep(), anunciante.getEndereco().getNumero(), anunciante.getEndereco().getComplemento(), anunciante.getEndereco().getBairro()));
        }

        anunciante.setEndereco(enderecoCadastrado);
        return repository.save(anunciante);
    }

    public void delete(Long id) {
        Anunciante anuncianteCadastrado = repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));
        repository.delete(anuncianteCadastrado);
    }

    public Anunciante toEntity(AnuncianteRequestDTO anuncianteRequestDTO) {
        return modelMapper.map(anuncianteRequestDTO, Anunciante.class);
    }

    public Anunciante toEntity(AnuncianteUpdateRequestDTO anuncianteUpdateRequestDTO) {
        return modelMapper.map(anuncianteUpdateRequestDTO, Anunciante.class);
    }

    public AnuncianteResponseDTO toResponse(Anunciante anunciante) {
        return modelMapper.map(anunciante, AnuncianteResponseDTO.class);
    }


}
