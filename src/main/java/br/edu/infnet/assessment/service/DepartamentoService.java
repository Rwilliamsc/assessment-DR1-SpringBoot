package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.Departamento;
import br.edu.infnet.assessment.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento saveDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public void deleteDepartamento(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento não encontrado com id: " + id);
        }
        departamentoRepository.deleteById(id);
    }

    public Departamento updateDepartamento(Long id, Departamento departamentoDetails) {
        Optional<Departamento> departamentoOpt = departamentoRepository.findById(id);
        if (departamentoOpt.isPresent()) {
            Departamento departamento = departamentoOpt.get();
            departamento.setNome(departamentoDetails.getNome());
            departamento.setLocal(departamentoDetails.getLocal());
            departamento.setFuncionarios(departamentoDetails.getFuncionarios());
            return departamentoRepository.save(departamento);
        } else {
            throw new RuntimeException("Departamento não encontrado");
        }
    }
}