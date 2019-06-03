/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author ronaldo
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByEmailAndSenha", query = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")
})
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Column(name = "email")
    @Size(max = 250)
    private String email;
    @Column(name = "nome")
    @Size(max = 250)
    private String nome;
    @Column(name = "senha")
    @Size(max = 250)
    private String senha;
    @Column(name = "mudar_senha")
    private boolean mudarSenha;
    @ManyToOne
    private TipoUsuario tipoUsuario;
    private boolean ehVisitante;

    public Usuario() {
    }

    public Usuario(Integer codigo,String email, String nome, String senha, boolean ehVisitante) {
        this.codigo = codigo;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.ehVisitante = ehVisitante;
    }
    
    public Usuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isMudarSenha() {
        return mudarSenha;
    }

    public void setMudarSenha(boolean mudarSenha) {
        this.mudarSenha = mudarSenha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isEhVisitante() {
        return ehVisitante;
    }

    public void setEhVisitante(boolean ehVisitante) {
        this.ehVisitante = ehVisitante;
    }
    @Override
    public String toString() {
        return "Usuario{" + "codigo=" + codigo + ", email=" + email + ", nome=" + nome + ", senha=" + senha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

   
}
