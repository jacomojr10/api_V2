package med.voll.api.domain.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }
    }
