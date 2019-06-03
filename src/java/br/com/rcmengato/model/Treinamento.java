/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ronaldo
 */
@Entity
@Table(name = "treinamento")
@NamedQueries({
    @NamedQuery(name = "Treinamento.findAll", query = "SELECT t FROM Treinamento t WHERE t.ativo = TRUE"),
    @NamedQuery(name = "Treinamento.reallyFindAll", query = "SELECT t FROM Treinamento t")
})
public class Treinamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @OneToOne
    private Habilidade assunto;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "treinamento_tem_funcionario",
            joinColumns = @JoinColumn(name = "id_treinamento", referencedColumnName = "codigo"),
            inverseJoinColumns = @JoinColumn(name = "id_funcionario", referencedColumnName = "codigo")
    )
    private List<Funcionario> funcionarios;
    private boolean ativo;

    public Treinamento(boolean ativo) {
        this.ativo = ativo;
    }

    public Treinamento() {
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public Habilidade getAssunto() {
        return assunto;
    }

    public void setAssunto(Habilidade assunto) {
        this.assunto = assunto;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.codigo);
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
        final Treinamento other = (Treinamento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Treinamento{" + "codigo=" + codigo + ", dataInicio=" + dataInicio + ", dataTermino=" + dataTermino + ", assunto=" + assunto + ", funcionarios=" + funcionarios + '}';
    }

}
