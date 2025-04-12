package com.eduardo.analiserede.service;

import com.eduardo.analiserede.entity.Endereco;
import com.eduardo.analiserede.exception.customizadas.EnderecoException;
import com.eduardo.analiserede.mapper.EnderecoMapper;
import com.eduardo.analiserede.model.dto.EnderecoDTO;
import com.eduardo.analiserede.model.enums.StatusEndereco;
import com.eduardo.analiserede.repository.EnderecoRepository;
import com.eduardo.analiserede.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class CepService {
    private static final String URL_CONSULTA_CEP = "https://viacep.com.br/ws/";
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoDTO consultar(String cep) {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setStatusConsulta(StatusEndereco.EM_CONSULTA);
        endereco = enderecoRepository.save(endereco);

        realizarRequisicao(cep, endereco);

        return enderecoMapper.enderecoToEnderecoDTO(endereco);
    }

    public EnderecoDTO consultarCep(Long enderecoId) {
        return enderecoMapper.enderecoToEnderecoDTO(
                enderecoRepository.findById(enderecoId).orElseThrow(
                        () -> new EnderecoException("Endereco Id não encontrado: #" + enderecoId, HttpStatus.BAD_REQUEST)
                )
        );
    }

    @Async
    protected void realizarRequisicao(String cep, Endereco endereco) {

        log.info("Consultando CEP: {}", cep);
        URI uri = URI.create(URL_CONSULTA_CEP + cep + "/json/");

        WebClientUtil.enviarRequisicaoAsync(
                new HttpHeaders(),
                HttpMethod.GET,
                uri.toString(),
                "",
                EnderecoDTO.class,
                Object.class
        ).subscribe(
                enderecoDTO -> {
                    log.info("Resposta recebida: {}", enderecoDTO);

                    endereco.setStatusConsulta(StatusEndereco.CONSULTADO);
                    endereco.setLogradouro(enderecoDTO.getLogradouro());
                    endereco.setBairro(enderecoDTO.getBairro());
                    endereco.setLocalidade(enderecoDTO.getLocalidade());
                    endereco.setUf(enderecoDTO.getUf());
                    endereco.setCep(enderecoDTO.getCep());
                    endereco.setComplemento(enderecoDTO.getComplemento());
                    endereco.setIbge(enderecoDTO.getIbge());
                    endereco.setGia(enderecoDTO.getGia());
                    endereco.setDdd(enderecoDTO.getDdd());
                    endereco.setSiafi(enderecoDTO.getSiafi());
                    endereco.setEstado(enderecoDTO.getEstado());
                    endereco.setUnidade(enderecoDTO.getUnidade());
                    endereco.setRegiao(enderecoDTO.getRegiao());

                    enderecoRepository.save(endereco);
                },
                throwable -> {
                    log.error("Erro na requisição: {}", throwable.getMessage());
                    endereco.setStatusConsulta(StatusEndereco.ERRO);
                    enderecoRepository.save(endereco);
                }
        );

    }
}
