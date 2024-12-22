package com.eduardo.analiserede.repository;

import com.eduardo.analiserede.entity.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
  List<Medicao> findAllByLocal_Usuario_UsuarioId(Long localUsuarioUsuarioId);
}
