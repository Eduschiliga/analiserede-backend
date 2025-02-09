package com.eduardo.analiserede.repository;

import com.eduardo.analiserede.entity.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
  @Query("SELECT l.medicoes FROM Local l WHERE l.usuario.usuarioId = :localUsuarioUsuarioId")
  List<Medicao> findAllByLocal_Usuario_UsuarioId(Long localUsuarioUsuarioId);
}
