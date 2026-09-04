package br.com.viduink.quatorze_api_agenda.dtos;

import java.time.LocalDateTime;

public record AutenticarUsuarioResponse(
        String mensagem,
        LocalDateTime dataHora,
        Integer id,
        String nome,
        String email,
        String accessToken
) {
}