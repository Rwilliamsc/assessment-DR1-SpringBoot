package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
