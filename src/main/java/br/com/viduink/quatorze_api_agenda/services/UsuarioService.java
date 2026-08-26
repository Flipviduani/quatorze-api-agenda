package br.com.viduink.quatorze_api_agenda.services;

import br.com.viduink.quatorze_api_agenda.dtos.CriarUsuarioRequest;
import br.com.viduink.quatorze_api_agenda.dtos.CriarUsuarioResponse;
import br.com.viduink.quatorze_api_agenda.entities.Usuario;
import br.com.viduink.quatorze_api_agenda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) throws Exception {

        var usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(criptografarSenha(request.senha()));
        usuario.setDataHoraCriacao(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return new CriarUsuarioResponse(
                "Usuário cadastrado com sucesso!",
                LocalDateTime.now(),
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    private String criptografarSenha(String senha) throws Exception {

        //Instanciando o algoritmo SHA-256
        var messageDigest = MessageDigest.getInstance("SHA-256");
        //Gerando o hash da senha
        var hash = messageDigest.digest(
                senha.getBytes(StandardCharsets.UTF_8)
        );

        //Convertendo o hash para hexadecimal
        var hexadecimal = new StringBuilder();
        for (byte b : hash) {
            hexadecimal.append(String.format("%02x", b));
        }

        return hexadecimal.toString();
    }
}