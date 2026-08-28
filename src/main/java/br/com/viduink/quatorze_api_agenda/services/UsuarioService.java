package br.com.viduink.quatorze_api_agenda.services;

import br.com.viduink.quatorze_api_agenda.dtos.AutenticarUsuarioRequest;
import br.com.viduink.quatorze_api_agenda.dtos.AutenticarUsuarioResponse;
import br.com.viduink.quatorze_api_agenda.dtos.CriarUsuarioRequest;
import br.com.viduink.quatorze_api_agenda.dtos.CriarUsuarioResponse;
import br.com.viduink.quatorze_api_agenda.entities.Usuario;
import br.com.viduink.quatorze_api_agenda.exceptions.AcessoNegadoException;
import br.com.viduink.quatorze_api_agenda.exceptions.EmailJaCadastradoException;
import br.com.viduink.quatorze_api_agenda.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AutenticarUsuarioResponse autenticarUsuario(AutenticarUsuarioRequest request) throws Exception{
        var usuario = usuarioRepository.findByEmail(request.email());

        if (usuario == null){
            throw new AcessoNegadoException("Acesso negado. Usuário não encontrado.");
        }

        var senhaCriptografada = criptografarSenha(request.senha());

        if( ! usuario.getSenha().equals(senhaCriptografada)){
            throw new AcessoNegadoException("Acesso negado. Credenciais inválidas.");
        }

        return new AutenticarUsuarioResponse(
                "Usuário autenticado com sucesso!",
                LocalDateTime.now(),
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                gerarToken(usuario.getEmail())
        );

    }

    public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) throws Exception {

        if(usuarioRepository.findByEmail(request.email()) != null){
            throw new EmailJaCadastradoException("E-mail informado já cadastrado.");
        }

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

    private String gerarToken(String email) throws Exception{
        var chaveAssinatura = "4e3321d3-bad5-4d0a-a1f0-9e2286d9094f";

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, chaveAssinatura)
                .compact();
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