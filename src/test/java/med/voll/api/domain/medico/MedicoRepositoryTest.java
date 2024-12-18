package med.voll.api.domain.medico;

import med.voll.api.domain.Consulta.Consulta;
import med.voll.api.domain.dto.DadosCadastroMedico;
import med.voll.api.domain.dto.DadosCadastroPaciente;
import med.voll.api.domain.dto.DadosEndereco;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void escolherMedicoAleatorioNaDataCenario1() {

        // given ou arrange
        var proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour( 10)
                .minusMinutes(0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou assert
        var medicoLivre = medicoRepository.escolherMedicoAleatorioNaData(Especialidade.PSIQUIATRIA, proximaSegundaAs10);
        //then ou assert
        assertThat(medicoLivre).isNull();

    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioNaDataCenario2() {

        //given ou arrange
        var proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour( 10)
                .minusMinutes(0);

        //when ou act
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);

    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "619999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                " ",
                null
        );
    }


}
