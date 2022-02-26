package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.request.EnderecoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EnderecoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Cidade;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.domain.entity.Estado;
import br.com.revistainfoco.revista.errors.exceptions.EnderecoNaoEncontradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
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
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EnderecoService(EnderecoRepository repository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository, ModelMapper modelMapper) {
        this.enderecoRepository = repository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public EnderecoResponseDTO create(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco enderecoParaSalvar = modelMapper.map(enderecoRequestDTO, Endereco.class);

        Estado estado = estadoRepository.findByUf(enderecoRequestDTO.getUf());

        if (enderecoParaSalvar.getCidade() != null) {
            Cidade cidade = modelMapper.map(enderecoRequestDTO.getCidade(), Cidade.class);
            if (estado != null) {
                cidade.setEstado(estado);
            }
            enderecoParaSalvar.setCidade(cidade);
            cidadeRepository.save(enderecoParaSalvar.getCidade());
        }

        Endereco enderecoSalvo = enderecoRepository.save(enderecoParaSalvar);
        return modelMapper.map(enderecoSalvo, EnderecoResponseDTO.class);
    }


    public List<EnderecoResponseDTO> readAll() {
        List<EnderecoResponseDTO> enderecosCadastrados = new ArrayList<>();
        enderecoRepository.findAll().forEach(endereco -> {
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
        return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado"));
    }

    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoUpdateRequestDTO enderecoUpdateRequestDTO) {
        Endereco enderecoSalvo = getEndereco(id);
        Estado estado = estadoRepository.findByUf(enderecoUpdateRequestDTO.getUf());

        enderecoSalvo.setLogradouro(enderecoUpdateRequestDTO.getLogradouro());

        if (enderecoUpdateRequestDTO.getCidade() != null) {
            Cidade cidade = modelMapper.map(enderecoUpdateRequestDTO.getCidade(), Cidade.class);
            if (estado != null) {
                cidade.setEstado(estado);
            }
            if (enderecoUpdateRequestDTO.getCidade().getId() != null) {
                cidade.setNome(enderecoUpdateRequestDTO.getCidade().getNome());
                cidadeRepository.save(cidade);
            } else {
                Cidade novaCidadeCadastrada = cidadeRepository.save(new Cidade(null, cidade.getNome(), estado));
                enderecoSalvo.setCidade(novaCidadeCadastrada);
            }
        }

        enderecoSalvo.setCep(enderecoUpdateRequestDTO.getCep());
        enderecoSalvo.setNumero(enderecoUpdateRequestDTO.getNumero());
        enderecoSalvo.setComplemento(enderecoUpdateRequestDTO.getComplemento());
        enderecoSalvo.setBairro(enderecoUpdateRequestDTO.getBairro());

        Endereco enderecoAtualizado = enderecoRepository.save(enderecoSalvo);
        return modelMapper.map(enderecoAtualizado, EnderecoResponseDTO.class);
    }

    public void delete(Long id) {
        getEndereco(id);
        enderecoRepository.deleteById(id);
    }

    public EnderecoResponseDTO findByCep(String cep) {
        Endereco endereco = enderecoRepository.findByCep(cep);
        if (endereco != null) {
            return modelMapper.map(endereco, EnderecoResponseDTO.class);
        }
        throw new EstadoNaoEncontradoException("Endereço não encontrado para o cep " + cep);
    }
}
