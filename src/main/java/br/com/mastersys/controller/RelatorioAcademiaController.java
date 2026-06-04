package br.com.mastersys.controller;

import br.com.mastersys.projection.AlunosPorCidadeProjection;
import br.com.mastersys.projection.FaturamentoMensalProjection;
import br.com.mastersys.projection.FaturasEmAbertoProjection;
import br.com.mastersys.repository.RelatorioAcademiaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/relatorios")
public class RelatorioAcademiaController {

    private final RelatorioAcademiaRepository relatorioAcademiaService;

    public RelatorioAcademiaController(RelatorioAcademiaRepository relatorioAcademiaService) {
        this.relatorioAcademiaService = relatorioAcademiaService;
    }

    @GetMapping(value = "/faturamento-mensal")
    public ResponseEntity<List<FaturamentoMensalProjection>> faturamentoMensal() {
        return ResponseEntity.ok(relatorioAcademiaService.faturamentoMensal());
    }

    @GetMapping(value = "/aluno-por-cidade")
    public ResponseEntity<List<AlunosPorCidadeProjection>> alunosPorCidade() {
        return ResponseEntity.ok(relatorioAcademiaService.alunosPorCidade());
    }

    @GetMapping(value = "/faturas-em-aberto")
    public ResponseEntity<List<FaturasEmAbertoProjection>> faturasEmAberto() {
        return ResponseEntity.ok(relatorioAcademiaService.faturasEmAberto());
    }

}