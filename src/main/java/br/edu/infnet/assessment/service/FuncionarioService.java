package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.Departamento;
import br.edu.infnet.assessment.model.Funcionario;
import br.edu.infnet.assessment.repository.DepartamentoRepository;
import br.edu.infnet.assessment.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario saveFuncionario(Funcionario funcionario) {
        long departamentoId = funcionario.getDepartamento().getId();
        funcionario.setDepartamento(null);

        Optional<Departamento> departamentoOpt = departamentoRepository.findById(departamentoId);
        if (departamentoOpt.isPresent()) {
            funcionario.setDepartamento(departamentoOpt.get());
        } else {
            throw new RuntimeException("Departamento não encontrado");
        }
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario updateFuncionario(Long id, Funcionario funcionarioUpdate) {
        long departamentoId = funcionarioUpdate.getDepartamento().getId();

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            funcionario.setNome(funcionarioUpdate.getNome());
            funcionario.setEndereco(funcionarioUpdate.getEndereco());
            funcionario.setTelefone(funcionarioUpdate.getTelefone());
            funcionario.setEmail(funcionarioUpdate.getEmail());
            funcionario.setDataNascimento(funcionarioUpdate.getDataNascimento());

            Optional<Departamento> departamentoOpt = departamentoRepository.findById(departamentoId);
            if (departamentoOpt.isPresent()) {
                funcionario.setDepartamento(departamentoOpt.get());
            } else {
                throw new RuntimeException("Departamento não encontrado");
            }

            return funcionarioRepository.save(funcionario);
        } else {
            throw new RuntimeException("Funcionário não encontrado");
        }
    }

    public void deleteFuncionario(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com id: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
