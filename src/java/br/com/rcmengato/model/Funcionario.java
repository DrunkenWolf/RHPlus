/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

import br.com.caelum.stella.bean.validation.CPF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 *
 * @author ronaldo
 */
@Entity
@Table(name = "funcionario")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f WHERE f.ehFreelancer = FALSE ORDER BY f.codigo DESC"),
    @NamedQuery(name = "Funcionario.findAllFreelancer", query="SELECT f FROM Funcionario f WHERE f.ehFreelancer = TRUE ORDER BY f.codigo DESC"),
    @NamedQuery(name = "Funcionario.count", query = "SELECT COUNT(f) FROM Funcionario f WHERE f.ehFreelancer = FALSE"),
    @NamedQuery(name = "Funcionario.finByEmail", query = "SELECT f FROM Funcionario f WHERE f.email = :email")
})
public class Funcionario implements Serializable, Comparable<Funcionario>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Size(max = 250)
    private String nome;
    @CPF(message = "O CPF informado não é válido.")
    private String cpf;
    @Size(max = 20)
    private String sexo;
    private String dataNascimento;
    private String rg;
    private String foto;
    private String email;
    private String telefone;
    private String likedin;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario", orphanRemoval = true)
    private List<FuncionarioHabilidade> habilidades;
    private boolean disponivel;
    private String dataAdmissao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario", fetch = FetchType.EAGER)
    private List<Depoimento> depoimentos;
    private Integer ratingGeral;
    @ManyToMany(mappedBy = "funcionarios", fetch = FetchType.LAZY)
    private List<Treinamento> treinamentos;
    private boolean ehFreelancer;
    private String estado;
    @OneToMany
    private List<FormacaoAcademica> formacaoAcademica;
    
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
    private List<ProjetoFuncionario> projetos;
    


    //18 FACETAS REFERENTES AO QUESTIONARIO // Integer referente à porcentagem de afinidade com a faceta (min 0, max 100)
    private Integer altruista;
    private Integer cooperativo;
    private Integer sociavel;
    private Integer entusiastico;
    private Integer energetico;
    private Integer confiante;
    private Integer confiavel;
    private Integer eficiente;
    private Integer minucioso;
    private Integer organizado;
    private Integer engenhoso;
    private Integer inteligencia;
    private Integer pratico;
    private Integer simpatico;
    private Integer instavel;
    private Integer assertividade;
    private Integer otimista;
    private Integer critico;

    
    //8 PERFIS DO MODELO BigFive
    private Integer semeador;
    private Integer insvestigador;
    private Integer monitor;
    private Integer coordenador;
    private Integer formatador;
    private Integer implementador;
    private Integer trabEquip;
    private Integer complementador;
    
    
    
    public Integer getSemeador() {
        return semeador;
    }

    public void setSemeador(Integer semeador) {
        this.semeador = semeador;
    }

    public Integer getInsvestigador() {
        return insvestigador;
    }

    public void setInsvestigador(Integer insvestigador) {
        this.insvestigador = insvestigador;
    }

    public Integer getMonitor() {
        return monitor;
    }

    public void setMonitor(Integer monitor) {
        this.monitor = monitor;
    }

    public Integer getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Integer coordenador) {
        this.coordenador = coordenador;
    }

    public Integer getFormatador() {
        return formatador;
    }

    public void setFormatador(Integer formatador) {
        this.formatador = formatador;
    }

    public Integer getImplementador() {
        return implementador;
    }

    public void setImplementador(Integer implementador) {
        this.implementador = implementador;
    }

    public Integer getTrabEquip() {
        return trabEquip;
    }

    public void setTrabEquip(Integer trabEquip) {
        this.trabEquip = trabEquip;
    }

    public Integer getComplementador() {
        return complementador;
    }
    public void setComplementador(Integer complementador) {
        this.complementador = complementador;
    }
 
    
    
    
    
    public Integer getCooperativo() {
        return cooperativo;
    }

    public void setCooperativo(Integer cooperativo) {
        this.cooperativo = cooperativo;
    }

    public Integer getSociavel() {
        return sociavel;
    }

    public void setSociavel(Integer sociavel) {
        this.sociavel = sociavel;
    }

    public Integer getEntusiastico() {
        return entusiastico;
    }

    public void setEntusiastico(Integer entusiastico) {
        this.entusiastico = entusiastico;
    }

    public Integer getEnergetico() {
        return energetico;
    }

    public void setEnergetico(Integer energetico) {
        this.energetico = energetico;
    }

    public Integer getConfiante() {
        return confiante;
    }

    public void setConfiante(Integer confiante) {
        this.confiante = confiante;
    }

    public Integer getConfiavel() {
        return confiavel;
    }

    public void setConfiavel(Integer confiavel) {
        this.confiavel = confiavel;
    }

    public Integer getEficiente() {
        return eficiente;
    }

    public void setEficiente(Integer eficiente) {
        this.eficiente = eficiente;
    }

    public Integer getMinucioso() {
        return minucioso;
    }

    public void setMinucioso(Integer minucioso) {
        this.minucioso = minucioso;
    }

    public Integer getOrganizado() {
        return organizado;
    }

    public void setOrganizado(Integer organizado) {
        this.organizado = organizado;
    }

    public Integer getEngenhoso() {
        return engenhoso;
    }

    public void setEngenhoso(Integer engenhoso) {
        this.engenhoso = engenhoso;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(Integer inteligencia) {
        this.inteligencia = inteligencia;
    }

    public Integer getPratico() {
        return pratico;
    }

    public void setPratico(Integer pratico) {
        this.pratico = pratico;
    }

    public Integer getSimpatico() {
        return simpatico;
    }

    public void setSimpatico(Integer simpatico) {
        this.simpatico = simpatico;
    }

    public Integer getInstavel() {
        return instavel;
    }

    public void setInstavel(Integer instavel) {
        this.instavel = instavel;
    }

    public Integer getAssertividade() {
        return assertividade;
    }

    public void setAssertividade(Integer assertividade) {
        this.assertividade = assertividade;
    }

    public Integer getOtimista() {
        return otimista;
    }

    public void setOtimista(Integer otimista) {
        this.otimista = otimista;
    }

    public Integer getCritico() {
        return critico;
    }

    public void setCritico(Integer critico) {
        this.critico = critico;
    }
    
    
    
    
    
    
    public Integer getAltruista() {
        return altruista;
    } 
    public void setAltruista(Integer altruista) {
        this.altruista = altruista;
    }
    
    
    
    public Funcionario() {
        this.endereco = new Endereco();
        this.habilidades = new ArrayList<>();
        this.formacaoAcademica = new ArrayList<>();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<FuncionarioHabilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<FuncionarioHabilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public String getLikedin() {
        return likedin;
    }

    public void setLikedin(String likedin) {
        this.likedin = likedin;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public List<ProjetoFuncionario> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<ProjetoFuncionario> projetos) {
        this.projetos = projetos;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public List<Depoimento> getDepoimentos() {
        return depoimentos;
    }

    public void setDepoimentos(List<Depoimento> depoimentos) {
        this.depoimentos = depoimentos;
    }

    public Integer getRatingGeral() {
        return ratingGeral;
    }

    public void setRatingGeral(Integer ratingGeral) {
        this.ratingGeral = ratingGeral;
    }

    public List<Treinamento> getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(List<Treinamento> treinamentos) {
        this.treinamentos = treinamentos;
    }

    public boolean isEhFreelancer() {
        return ehFreelancer;
    }

    public void setEhFreelancer(boolean ehFreelancer) {
        this.ehFreelancer = ehFreelancer;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<FormacaoAcademica> getFormacaoAcademica() {
        return formacaoAcademica;
    }

    public void setFormacaoAcademica(List<FormacaoAcademica> formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codigo);
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
        final Funcionario other = (Funcionario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Funcionario o) {
        if(this.ratingGeral > o.ratingGeral){
            return 1;
        }
        if(this.ratingGeral < o.ratingGeral){
            return -1;
        }
        return 0;
    }

    
    
}
