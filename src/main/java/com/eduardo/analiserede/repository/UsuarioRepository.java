package com.eduardo.analiserede.repository;

import com.eduardo.analiserede.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Usuario findByEmail(String email);

  Usuario findByLogin(String login);
}
