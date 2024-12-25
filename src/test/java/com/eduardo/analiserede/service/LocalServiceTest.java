package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Local;
import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.LocalMapper;
import com.eduardo.analiserede.mapper.MedicaoMapper;
import com.eduardo.analiserede.model.dto.LocalDTO;
import com.eduardo.analiserede.repository.LocalRepository;
import com.eduardo.analiserede.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocalServiceTest {
  @Mock
  private LocalRepository localRepository;

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private LocalMapper localMapper;

  @Mock
  private MedicaoMapper medicaoMapper;

  @Mock
  private Local local;

  @Mock
  private LocalDTO localDTO;

  @Mock
  private Usuario usuario;

  @InjectMocks
  private MedicaoService medicaoService;

  @InjectMocks
  private LocalService localService;

  @Test
  @DisplayName("Deveria salvar um local")
  void deveriaSalvarUmLocal() {
    // Arrange
    String emailUsuario = "teste@teste.com.br";

    // Configurar o mock do usuário retornado
    given(usuarioRepository.findByEmail(emailUsuario)).willReturn(usuario);

    // Configurar o mock do mapeamento de LocalDTO para Local
    given(localMapper.localDTOtoLocal(localDTO)).willReturn(local);

    // Configurar o mock do repositório para salvar o Local
    given(localRepository.save(local)).willReturn(local);

    // Configurar o mock do mapeamento de Local salvo para LocalDTO
    given(localMapper.localToLocalDTO(local)).willReturn(localDTO);

    // Act
    LocalDTO resultado = localService.salvar(localDTO, emailUsuario);

    // Assert
    assertNotNull(resultado); // Verifica que o resultado não é nulo
    assertEquals(localDTO, resultado); // Confirma que o objeto retornado é o esperado

    // Validar se os métodos dos mocks foram chamados corretamente
    verify(usuarioRepository).findByEmail(emailUsuario);
    verify(localMapper).localDTOtoLocal(localDTO);
    verify(localRepository).save(local);
    verify(localMapper).localToLocalDTO(local);
  }

  @Test
  @DisplayName("Deve deletar um usuário")
  void deletar() {
    Long localId = 1L;

    given(localRepository.findById(localId)).willReturn(Optional.of(local));

    localService.deletar(localId);

    verify(localRepository).findById(1L);
    verify(localRepository).delete(local);
    Assertions.assertDoesNotThrow(() -> localService.deletar(1L));
  }
}