package br.com.mastersys.repository;

import br.com.mastersys.domain.Assiduidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssiduidadeRepository extends JpaRepository<Assiduidade, Long> {
}
