package com.joao.brandao.forum_hub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
                                     String titulo,
                                     String mensagem,
                                     String autor,
                                     String curso) {
    public DadosAtualizacaoTopico(Topico topico){
        this( topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso());
    }
}
