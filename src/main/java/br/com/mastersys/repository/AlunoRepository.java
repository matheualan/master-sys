package br.com.mastersys.repository;

import br.com.mastersys.domain.Aluno;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Slice<Aluno> findAllBy(Pageable pageable);
}