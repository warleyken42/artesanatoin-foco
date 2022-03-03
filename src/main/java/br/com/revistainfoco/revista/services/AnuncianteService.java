package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.AnuncianteRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.AnuncianteUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncianteResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anunciante;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.AnuncianteNaoEncontradoException;
import br.com.revistainfoco.revista.repository.AnuncianteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnuncianteService {

    private final AnuncianteRepository repository;
    private final EnderecoService enderecoService;
    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final ModelMapper modelMapper;

    @Autowired
    public AnuncianteService(AnuncianteRepository repository, EnderecoService enderecoService, CidadeService cidadeService, EstadoService estadoService, ModelMapper modelMapper) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.cidadeService = cidadeService;
        this.estadoService = estadoService;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public Anunciante create(Anunciante anunciante) {
        Endereco endereco = anunciante.getEndereco();
        Cidade cidade = anunciante.getEndereco().getCidade();
        Estado estado = anunciante.getEndereco().getCidade().getEstado();
        
        Estado estadoCadastrado = estadoService.findByNomeAndUf(estado.getNome(), estado.getUf());
        Cidade cidadeCadastrada = cidadeService.findByNome(cidade.getNome());
        boolean enderecoCadastrado = enderecoService.findAll().contains(endereco);

        if (estadoCadastrado == null) {
            estadoCadastrado = estadoService.create(estado);
            anunciante.getEndereco().getCidade().setEstado(estadoCadastrado);
        }

        if (cidadeCadastrada == null) {
            cidadeCadastrada = cidadeService.create(cidade);
            anunciante.getEndereco().setCidade(cidadeCadastrada);
        }

        if (!enderecoCadastrado) {
            Endereco novoEndereco = enderecoService.create(endereco);
            anunciante.setEndereco(novoEndereco);
        }

        return repository.save(anunciante);
    }

    public List<Anunciante> findAll() {
        return repository.findAll();
    }

    public Anunciante findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));
    }

    public Anunciante update(Long id, Anunciante anunciante) {
        Anunciante anunciantesCadastrado = repository.findById(id).orElseThrow(() -> new AnuncianteNaoEncontradoException("Anunciante não encontrado"));

        if (anunciantesCadastrado != null) {
            anunciante.setId(id);
        }

        Endereco endereco = anunciante.getEndereco();
        Cidade cidade = anunciante.getEndereco().getCidade();
        Estado estado = anunciante.getEndereco().getCidade().getEstado();


        Estado estadoCadastrado = estadoService.findByNomeAndUf(estado.getNome(), estado.getUf());
        Cidade cidadeCadastrada = cidadeService.findByNome(cidade.getNome());
        boolean enderecoCadastrado = enderecoService.findAll().contains(endereco);

        if (estadoCadastrado == null) {
            estadoCadastrado = estadoService.create(estado);
            anunciante.getEndereco().getCidade().setEstado(estadoCadastrado);
        } else {
            estadoCadastrado.setNome(estado.getNome());
            estadoCadastrado.setUf(estado.getUf());
            Estado estadoAtualizado = estadoService.update(estadoCadastrado.getId(), estadoCadastrado);
            anunciante.getEndereco().getCidade().setEstado(estadoAtualizado);
        }

        if (cidadeCadastrada == null) {
            cidadeCadastrada = cidadeService.create(cidade);
            anunciante.getEndereco().setCidade(cidadeCadastrada);
        } else {
            cidadeCadastrada.setNome(cidade.getNome());
            Cidade cidadeAtualizada = cidadeService.update(cidadeCadastrada.getId(), cidade);
            anunciantesCadastrado.getEndereco().setCidade(cidadeAtualizada);
        }

        if (!enderecoCadastrado) {
            Endereco novoEndereco = enderecoService.create(endereco);
            anunciante.setEndereco(novoEndereco);
        } else {
            Endereco enderecoAtual = anunciantesCadastrado.getEndereco();
            enderecoAtual.setLogradouro(endereco.getLogradouro());
            enderecoAtual.setCep(endereco.getCep());
            enderecoAtual.setNumero(endereco.getNumero());
            enderecoAtual.setComplemento(endereco.getComplemento());
            enderecoAtual.setBairro(endereco.getBairro());
            Endereco enderecoAtualizado = enderecoService.update(enderecoAtual.getId(), enderecoAtual);
            anunciante.setEndereco(enderecoAtualizado);
        }
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
