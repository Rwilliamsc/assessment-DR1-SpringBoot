package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
