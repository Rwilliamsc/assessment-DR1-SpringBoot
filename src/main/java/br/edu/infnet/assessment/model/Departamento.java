package br.edu.infnet.assessment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String local;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Departamento(String nome, String local) {
        this.nome = nome;
        this.local = local;
    }

    public Departamento() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios.clear();
        if (funcionarios != null) {
            this.funcionarios.addAll(funcionarios);
            for (Funcionario funcionario : funcionarios) {
                funcionario.setDepartamento(this);
            }
        }
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        funcionario.setDepartamento(this);
    }

    public void removeFuncionario(Funcionario funcionario) {
        funcionarios.remove(funcionario);
        funcionario.setDepartamento(null);
    }
}