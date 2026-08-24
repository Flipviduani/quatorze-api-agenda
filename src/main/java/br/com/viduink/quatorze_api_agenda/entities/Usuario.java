package br.com.viduink.quatorze_api_agenda.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Usuario {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataHoraCriacao;
    private List<Tarefa> tarefas;
}
