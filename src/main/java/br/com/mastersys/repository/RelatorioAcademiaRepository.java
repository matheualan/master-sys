package br.com.mastersys.repository;

import br.com.mastersys.domain.FaturaMatricula;
import br.com.mastersys.projection.AlunosPorCidadeProjection;
import br.com.mastersys.projection.FaturamentoMensalProjection;
import br.com.mastersys.projection.FaturasEmAbertoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatorioAcademiaRepository extends Repository<FaturaMatricula, Long> {

    @Query(
            value = """
            SELECT TO_CHAR(data_vencimento, 'yyyy-MM') AS mes,
                   SUM(valor) AS total
            FROM faturas_matriculas
            WHERE status = 'PAGA'
            GROUP BY TO_CHAR(data_vencimento, 'yyyy-MM')
            ORDER BY mes
            """,
            nativeQuery = true
    )
    List<FaturamentoMensalProjection> faturamentoMensal();

    @Query(
            value = """
            SELECT cidade,
                   COUNT(*) AS quantidade
            FROM alunos
            GROUP BY cidade
            ORDER BY quantidade desc
            """,
            nativeQuery = true
    )
    List<AlunosPorCidadeProjection> alunosPorCidade();

    @Query(
            value = """
            SELECT
                     m.id AS matriculaId,
                     a.nome AS alunoNome,
                     fm.data_vencimento AS dataVencimento,
                     fm.valor AS valor
            FROM faturas_matriculas fm
            JOIN matriculas m ON m.id = fm.matricula_id
            JOIN alunos a on a.id = m.aluno_id
            WHERE fm.status = 'ABERTA'
            ORDER BY fm.data_vencimento desc
            """,
            nativeQuery = true
    )
    List<FaturasEmAbertoProjection> faturasEmAberto();

}