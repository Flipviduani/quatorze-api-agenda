package br.com.viduink.quatorze_api_agenda.dtos;

import java.time.LocalDateTime;

public record CriarTarefaResponse(
        String mensagem,
        LocalDateTime dataHoraCadastro,
        Integer tarefaId
) {
}