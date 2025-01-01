package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Usuario;
import com.eduardo.analiserede.mapper.UsuarioMapper;
import com.eduardo.analiserede.model.dto.UsuarioDTO;
import com.eduardo.analiserede.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
  @Captor
  private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

  @Captor
  private ArgumentCaptor<Long> longArgumentCaptor;

  @InjectMocks
  private UsuarioService usuarioService;

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private UsuarioMapper usuarioMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private Usuario usuario;

  @Mock
  private UsuarioDTO usuarioDTO;

  @Mock
  private List<Usuario> usuarioList;

  @Mock
  private List<UsuarioDTO> usuarioDTOList;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockarUsuario();
    mockarUsuarioDTO();
    mockarListaUsuario();
    mockarListaUsuarioDTO();
  }

  private void mockarListaUsuarioDTO() {
    this.usuarioDTOList = List.of(
        UsuarioDTO.builder()
            .usuarioId(1L)
            .email("teste1@email.com")
            .senha("123456")
            .nomeCompleto("Teste1")
            .login("teste1")
            .build(),
        UsuarioDTO.builder()
            .usuarioId(2L)
            .email("teste2@email.com")
            .senha("123456")
            .nomeCompleto("Teste2")
            .login("teste2")
            .build()
    );
  }

  private void mockarListaUsuario() {
    this.usuarioList = List.of(
        Usuario.builder()
            .usuarioId(1L)
            .email("teste1@email.com")
            .senha("123456")
            .nomeCompleto("Teste1")
            .login("teste1")
            .locais(new ArrayList<>())
            .build(),
        Usuario.builder()
            .usuarioId(2L)
            .email("teste2@email.com")
            .senha("123456")
            .nomeCompleto("Teste2")
            .login("teste2")
            .locais(new ArrayList<>())
            .build()
    );
  }

  private void mockarUsuarioDTO() {
    this.usuarioDTO = UsuarioDTO.builder()
        .usuarioId(1L)
        .email("teste@email.com")
        .senha("123456")
        .nomeCompleto("Teste")
        .login("teste")
        .build();
  }

  private void mockarUsuario() {
    this.usuario = Usuario.builder()
        .usuarioId(1L)
        .email("teste@email.com")
        .senha("123456")
        .nomeCompleto("Teste")
        .login("teste")
        .locais(new ArrayList<>())
        .build();
  }

  @Nested
  class CriarUmUsuario {
    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    public void deveCriarUmUsuarioComSucesso() {
      // Arange
      when(usuarioMapper.usuarioDTOtoUsuario(usuarioDTO)).thenReturn(usuario);
      when(usuarioMapper.usuarioToUsuarioDTO(usuario)).thenReturn(usuarioDTO);
      when(passwordEncoder.encode(usuario.getSenha())).thenReturn("senhaCriptografada");
      when(usuarioRepository.save(usuarioArgumentCaptor.capture())).thenReturn(usuario);

      // Act
      UsuarioDTO usuarioSalvo = usuarioService.salvar(usuarioDTO);

      // Assert
      Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

      Assertions.assertNotNull(usuarioSalvo);
      Assertions.assertEquals(usuario.getLogin(), usuarioSalvo.getLogin());
      Assertions.assertEquals(usuarioCapturado.getLogin(), usuario.getLogin());
    }

    @Test
    @DisplayName("Deve disparar uma exception caso ocorra um erro")
    public void deveDispararUmaExceptionCasoOcorraUmErro() {
      // Arange
      doThrow(new RuntimeException()).when(usuarioRepository).save(usuario);

      // Assert + Act
      assertThrows(RuntimeException.class, () -> usuarioService.salvar(usuarioDTO));
    }
  }

  @Nested
  class BuscarPorId {
    @Test
    @DisplayName("Deve buscar um usuário por id")
    void buscarUsuarioPorId() {
      // Arrange
      when(usuarioRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
      when(usuarioMapper.usuarioToUsuarioDTO(usuario)).thenReturn(usuarioDTO);

      // Act
      UsuarioDTO usuarioDTOSalvo = usuarioService.buscarUsuarioPorId(1L);

      // Assert
      verify(usuarioRepository).findById(1L);
      verify(usuarioMapper).usuarioToUsuarioDTO(usuario);

      Assertions.assertEquals(usuarioDTOSalvo.getUsuarioId(), longArgumentCaptor.getValue());
      Assertions.assertDoesNotThrow(() -> usuarioService.buscarUsuarioPorId(1L));
      Assertions.assertNotNull(usuarioDTOSalvo);
    }
  }

  @Nested
  class BuscarTodosUsuarios {
    @Test
    @DisplayName("Deve buscar todos os usuários")
    public void deveBuscarTodosOsUsuarios() {
      // Arrange
      when(usuarioRepository.findAll()).thenReturn(usuarioList);
      when(usuarioMapper.usuarioListToUsuarioDTOList(usuarioList)).thenReturn(usuarioDTOList);

      int size = usuarioList.size();

      // Act
      List<UsuarioDTO> usuarioListFindAll = usuarioService.buscarTodos();

      // Assert
      Assertions.assertNotNull(usuarioListFindAll);
      Assertions.assertEquals(size, usuarioListFindAll.size());
    }
  }

  @Nested
  class AtualizarUsuario {
    @Test
    @DisplayName("Deve atualizar um usuário")
    void deveAtualizarUmUsuarioComSucesso() {
      when(usuarioRepository.findById(usuarioDTO.getUsuarioId())).thenReturn(Optional.of(usuario));
      when(usuarioMapper.usuarioDTOtoUsuario(usuarioDTO)).thenReturn(usuario);
      when(usuarioMapper.usuarioToUsuarioDTO(usuarioArgumentCaptor.capture())).thenReturn(usuarioDTO);
      when(passwordEncoder.encode(usuarioDTO.getSenha())).thenReturn(any());
      when(usuarioRepository.save(usuario)).thenReturn(usuario);

      UsuarioDTO usuarioDTOAtualizado = usuarioService.atualizar(usuarioDTO);

      verify(usuarioRepository).findById(usuarioDTO.getUsuarioId());
      verify(usuarioMapper).usuarioDTOtoUsuario(usuarioDTO);
      verify(passwordEncoder).encode(usuarioDTO.getSenha());
      verify(usuarioRepository).save(usuario);

      Assertions.assertDoesNotThrow(() -> usuarioService.atualizar(usuarioDTO));
      Assertions.assertNotNull(usuario);
      Assertions.assertNotNull(usuarioDTOAtualizado);
    }

    @Test
    @DisplayName("Deve disparar uma exception caso ocorra um erro")
    public void deveDispararUmaExceptionCasoOcorraUmErro() {
      // Arange
      doThrow(new RuntimeException()).when(usuarioRepository).findById(usuario.getUsuarioId());

      // Assert + Act
      verify(usuarioRepository, times(0)).findById(usuario.getUsuarioId());
      verify(usuarioRepository, times(0)).save(any());

      assertThrows(RuntimeException.class, () -> usuarioService.atualizar(usuarioDTO));
    }
  }

  @Nested
  class DeletarPorId {
    @Test
    @DisplayName("Deve deletar com sucesso se o usuário existir")
    void deveDeletarComSucessoSeOUsuarioExistir() {
      // Arrange
      doReturn(Optional.of(usuario)).when(usuarioRepository).findById(longArgumentCaptor.capture());

      doNothing().when(usuarioRepository).delete(usuarioArgumentCaptor.capture());

      // Act
      usuarioService.deletar(usuario.getUsuarioId());

      // Assert
      verify(usuarioRepository, times(1)).findById(longArgumentCaptor.getValue());
      verify(usuarioRepository, times(1)).delete(usuarioArgumentCaptor.getValue());

      Assertions.assertEquals(usuario.getUsuarioId(), longArgumentCaptor.getValue());
      Assertions.assertDoesNotThrow(() -> usuarioService.buscarUsuarioPorId(longArgumentCaptor.getValue()));
      Assertions.assertNotNull(longArgumentCaptor.getValue());
    }


    @Test
    @DisplayName("Não deve deletar se o não usuário existir")
    void naoDeveDeletarSeOUsuarioExistir() {
      // Arrange
      when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

      // Act & Assert
      assertThrows(RuntimeException.class, () -> usuarioService.deletar(1L));

      verify(usuarioRepository, times(1)).findById(anyLong());
      verify(usuarioRepository, times(0)).delete(any());
    }
  }
}