package br.com.revistainfoco.revista.repository;

import br.com.revistainfoco.revista.domain.entity.Anunciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnuncianteRepository extends JpaRepository<Anunciante, Long> {
    Optional<Anunciante> findByCnpj(String cnpj);
}
