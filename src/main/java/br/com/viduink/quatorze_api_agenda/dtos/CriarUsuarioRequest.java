package br.com.viduink.quatorze_api_agenda.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(

        @Size(min = 1, max = 100, message  = "O nome do usuário deve ter pelo menos 8 caracteres.")
        @NotEmpty(message = "O nome do usuário é obrigatório.")
        String nome,

        @Email(message = "Informe um endereço de e-mail válido")
        @NotEmpty(message = "O e-mail do usuário é obrigatório.")
        String email,

        @Pattern(
                regexp = "",
                message = "A senha deve ter pelo menos uma letra minúscula, maiúscula, número e caractere especial, " +
                "assim como no mínimo 8 caractereres."
        )

        @NotEmpty(message = "A senha do usuário é obrigatória.")
        String senha
) {
}
