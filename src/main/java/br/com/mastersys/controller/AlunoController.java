package br.com.mastersys.controller;

import br.com.mastersys.dto.AlunoFiltroRequest;
import br.com.mastersys.dto.AlunoRequest;
import br.com.mastersys.dto.AlunoResponse;
import br.com.mastersys.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> cadastrar(@RequestBody @Valid AlunoRequest request, UriComponentsBuilder uriBuilder) {
        AlunoResponse response = alunoService.cadastrar(request);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AlunoResponse>> listar(AlunoFiltroRequest filtro, @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<AlunoResponse> page = alunoService.listar(filtro, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/slice")
    public ResponseEntity<Slice<AlunoResponse>> listarSlice(@PageableDefault(page = 0, size = 10,
            sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<AlunoResponse> slice = alunoService.listarSlice(pageable);
        return ResponseEntity.ok(slice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable Long id) {
        AlunoResponse response = alunoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AlunoRequest request) {
        AlunoResponse response = alunoService.atualizarPorId(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        alunoService.excluir(id);
//        ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity.noContent();
    }
}