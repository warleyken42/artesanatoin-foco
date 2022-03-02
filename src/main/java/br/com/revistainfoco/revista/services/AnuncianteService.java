package br.com.revistainfoco.revista.services;

import br.com.revistainfoco.revista.domain.dto.response.AnuncianteResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anunciante;
import br.com.revistainfoco.revista.repository.AnuncianteRepository;
import br.com.revistainfoco.revista.repository.CidadeRepository;
import br.com.revistainfoco.revista.repository.EnderecoRepository;
import br.com.revistainfoco.revista.repository.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncianteService {

    private final AnuncianteRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnuncianteService(AnuncianteRepository repository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.modelMapper = modelMapper;
    }

    public List<AnuncianteResponseDTO> readAll() {
        List<AnuncianteResponseDTO> anunciantesCadastrados = new ArrayList<>();
        repository.findAll().forEach(anunciante -> {
            AnuncianteResponseDTO anuncianteResponseDTO = modelMapper.map(anunciante, AnuncianteResponseDTO.class);
            anunciantesCadastrados.add(anuncianteResponseDTO);
        });
        return anunciantesCadastrados;
    }

    public AnuncianteResponseDTO readById(Long id) {
        Anunciante anunciante = getAnunciante(id);
        return modelMapper.map(anunciante, AnuncianteResponseDTO.class);
    }

    private Anunciante getAnunciante(Long id) {
        return null;
    }


}
