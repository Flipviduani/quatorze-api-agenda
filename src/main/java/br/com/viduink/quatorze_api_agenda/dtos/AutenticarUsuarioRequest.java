package br.com.viduink.quatorze_api_agenda.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AutenticarUsuarioRequest(

        @Email(message = "Informe um endereço de e-mail válido.")
        @NotEmpty(message = "O e-mail de acesso é obrigatório.")
        String email,

        @Size(message = "Informe uma senha com pelo menos 8 caracteres.")
        @NotEmpty(message = "A senha de acesso é obrigatória.")
        String senha

) {
}
