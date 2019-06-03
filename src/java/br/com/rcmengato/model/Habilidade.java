/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author ronaldo
 */
@Entity
@Table(name = "habilidade")
@NamedQueries({
    @NamedQuery(name = "habilidades", query = "SELECT h FROM Habilidade h ORDER BY h.tipo DESC"),
    @NamedQuery(name = "habilidades_idioma", query = "SELECT h FROM Habilidade h WHERE h.tipo = 'IDIOMA'"),
    @NamedQuery(name = "habilidades_tecnicas", query = "SELECT h FROM Habilidade h WHERE h.tipo = 'TÉCNICA'"),
    @NamedQuery(name = "habilidades_dominio", query = "SELECT h FROM Habilidade h WHERE h.tipo = 'DOMÍNIO'"),
    @NamedQuery(name = "Habilidade.count", query = "SELECT COUNT(h) FROM Habilidade h")
})
public class Habilidade implements Serializable, Comparable<Habilidade>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    private String nome;
    private String tipo;
    
    @OneToMany(mappedBy = "habilidade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FuncionarioHabilidade> funcionarios;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<FuncionarioHabilidade> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioHabilidade> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        final Habilidade other = (Habilidade) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

   
    @Override
    public String toString() {
        return "Habilidade{" + "codigo=" + codigo + ", nome=" + nome  + ", tipo=" + tipo + '}';
    }

    @Override
    public int compareTo(Habilidade o) {
        if(Objects.equals(o.codigo, this.codigo)){
            return 1;
        }
        return 0;
    }
    
}
