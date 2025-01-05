package com.joao.brandao.forum_hub.controller;

import com.joao.brandao.forum_hub.domain.topico.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
        @Transactional
        public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados, @PathVariable Long id) {
                try {
                                // Obtém a referência do tópico pelo ID
                                var topico = topicoRepository.getReferenceById(id);
                                // Atualiza as informações do tópico
                                topico.atualizarInformacao(dados);
                                // Retorna o tópico atualizado
                                return ResponseEntity.ok(new DadosAtualizacaoTopico(topico));

                } catch (EntityNotFoundException e) {
                        // Retorna erro caso o ID não seja encontrado
                        return ResponseEntity.badRequest().body("Id informado não existe.");
                }
        }




        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<String> excluir(@PathVariable Long id) {
                var topico = topicoRepository.findById(id).orElse(null);  // Usando findById para evitar o comportamento do getReferenceById
                if(topico==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O ID informado não existe.");  // Retorna 404 com a mensagem
                }
                else{
                        topicoRepository.deleteById(id);
                        return ResponseEntity.ok("Registro excluído com sucesso.");
                }

        }


}