package com.eduardo.analiserede.repository;

import com.eduardo.analiserede.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
  List<Local> findAllByUsuario_Id(Long usuarioId);
}
