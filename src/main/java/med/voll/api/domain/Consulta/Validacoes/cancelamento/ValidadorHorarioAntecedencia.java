package med.voll.api.domain.Consulta.Validacoes.cancelamento;

import med.voll.api.domain.Consulta.ConsultaRepository;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.dto.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

        @Autowired
        private ConsultaRepository repository;

        @Override
        public void validar(DadosCancelamentoConsulta dados) {
            var consulta = repository.getReferenceById(dados.idConsulta());
            var agora = LocalDateTime.now();
            var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

            if (diferencaEmHoras < 24) {
                throw new ValidacaoException("Consulta pode ser cancelada somente com antecedência de 24 horas!");
            }
        }
    }

