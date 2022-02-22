package br.com.revistainfoco.revista.repository;

import br.com.revistainfoco.revista.domain.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
