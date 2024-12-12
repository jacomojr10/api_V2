package med.voll.api.domain.Consulta.Validacoes.agendamento;

import med.voll.api.domain.dto.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
