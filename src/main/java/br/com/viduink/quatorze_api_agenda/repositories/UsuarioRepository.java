package br.com.viduink.quatorze_api_agenda.repositories;

import br.com.viduink.quatorze_api_agenda.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
