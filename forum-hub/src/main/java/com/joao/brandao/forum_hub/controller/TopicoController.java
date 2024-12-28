package com.joao.brandao.forum_hub.controller;

import com.joao.brandao.forum_hub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

        @Autowired
        private TopicoRepository topicoRepository;

        @PostMapping
        @Transactional
        public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {

                var titulo = dados.tituloDoComentario();
                var mensagem = dados.mensagem();

                Optional<Topico> verificaTitulo =  topicoRepository.findByTituloAndMensagem(titulo,mensagem);

                if (verificaTitulo.isPresent()) {
                        return ResponseEntity
                                .badRequest()
                                .body("Já existe uma publicação igual cadastrada!");
                }else{
                        var topico = new Topico(titulo, mensagem, dados.autor(), dados.curso());
                        topicoRepository.save(topico);
                        System.out.printf("Publicação cadastrada! ");
                        return ResponseEntity.ok().body("Cadastrado com sucesso!");
                }
        }


        @GetMapping
        public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {


                var dadosTopicos = topicoRepository.findByEstadoTopicoTrue(paginacao).map(DadosListagemTopico::new);;


                return ResponseEntity.ok(dadosTopicos);
        }



        @PutMapping("/{id}")
        public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
                var topico = topicoRepository.getReferenceById(dados.id());
                topico.atualizarInformacao(dados);

                topicoRepository.save(topico);
                return ResponseEntity.ok(new DadosAtualizacaoTopico(topico));
        }


        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity excluir(@PathVariable Long id) {
                var topico = topicoRepository.getReferenceById(id);
                topico.excluir();
                return ResponseEntity.noContent().build();
        }

}