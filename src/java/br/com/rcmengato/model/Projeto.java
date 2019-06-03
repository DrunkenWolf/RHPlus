/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ronaldo
 */
@Entity
@Table(name = "projeto")
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p WHERE p.status <> 'Finalizado' ORDER BY p.codigo DESC"),
    @NamedQuery(name = "Projeto.findAllFinalizados", query = "SELECT p FROM Projeto p WHERE p.status = 'Finalizado' ORDER BY p.codigo DESC"),
    @NamedQuery(name = "Projeto.count", query = "SELECT COUNT(p) FROM Projeto p"),
    @NamedQuery(name = "Projeto.terminando", query = "SELECT p FROM Projeto p WHERE (p.dataTermino BETWEEN :dataAtual AND :dataDps) AND (p.status = 'Em execução')")
})
public class Projeto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    private String nome;
    private String descricao;
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Temporal(TemporalType.DATE)
    private Date dataEncerrado;
    
    @OneToMany(mappedBy = "projeto",cascade = CascadeType.ALL)
    private List<ProjetoFuncionario> funcionarios;
    private String status;

    public Projeto(){
        this.funcionarios = new ArrayList<>();
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public List<ProjetoFuncionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<ProjetoFuncionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataEncerrado() {
        return dataEncerrado;
    }

    public void setDataEncerrado(Date dataEncerrado) {
        this.dataEncerrado = dataEncerrado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codigo);
        hash = 23 * hash + Objects.hashCode(this.nome);
        hash = 23 * hash + Objects.hashCode(this.descricao);
        hash = 23 * hash + Objects.hashCode(this.dataInicio);
        hash = 23 * hash + Objects.hashCode(this.dataTermino);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Projeto other = (Projeto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataTermino, other.dataTermino)) {
            return false;
        }
        return true;
    }
    
    
}
