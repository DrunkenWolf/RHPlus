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
    private Integer inteligente;
    private Integer pratico;
    private Integer simpatico;
    private Integer instavel;
    private Integer assertivo;
    private Integer otimista;
    private Integer critico;



    //17 CARACTERISTICAS PARA EQUIPE DE PROJETO

    
    private Integer capacidadeResolverConflito;  // Altruísta + Cooperativo / 2
    private Integer boaComunicacao;     //Sociável
    private Integer extroversao;        //Sociável + Entusiástico + Energético / 3
    private Integer confiancaMutua;     //Confiável + Confiante / 2
    private Integer criatividade;       //Engenhoso
    private Integer inteligencia;       //Inteligente
    private Integer organizacao;        //Organizado
    private Integer postCritica;        //Critico
    private Integer postOtimista;       //Otimista
    private Integer postDetalhista;     //Minucioso
    private Integer introversao;        //!Sociável + !Entusiástico + !Energético / 3 
    private Integer entusiasmo;         //Entusiástico
    private Integer praticidade;        //Pratico
    private Integer simpatia;           //Simpático
    private Integer estabilidade;       //!Instavel
    private Integer assertividade;      //Assertivo
    private Integer eficiencia;         //Eficiente
    
    
    
    
    private String primeiraCarac;
    private String segundaCarac;
    
    
    public void CalcPrincipais(Funcionario funcionario){
        Integer sortArray[];
        sortArray = new Integer[9];
        String texto1 = " ";
        String texto2 = " ";
        
        sortArray[0] = 0;  
        sortArray[1] = funcionario.semeador;
        sortArray[2] = funcionario.insvestigador;
        sortArray[3] = funcionario.monitor;
        sortArray[4] = funcionario.coordenador;
        sortArray[5] = funcionario.formatador;
        sortArray[6] = funcionario.implementador;
        sortArray[7] = funcionario.trabEquip;
        sortArray[8] = funcionario.complementador;

        int marcador1 = 0;
        int marcador2 = 0;
           
        for(int i = 0; i < 9; i++){
            if(sortArray[i] > sortArray[marcador1]){
                marcador1 = i;
            }
            else if(sortArray[i] > sortArray[marcador2]){
                marcador2 = i;
            }
              
        }
      
        if(marcador1 == 1){
            texto1 = "Semeador";
        }
        else if(marcador1 == 2){
            texto1 = "Investigador";
        }
        else if(marcador1 == 3){
            texto1 = "Monitor";
        }        
        else if(marcador1 == 4){
            texto1 = "Coordenador";
        }
        else if(marcador1 == 5){
            texto1 = "Formatador";
        }
        else if(marcador1 == 6){
            texto1 = "Implementador";
        }
        else if(marcador1 == 7){
            texto1 = "Trabalho em Equipe";
        }
        else if(marcador1 == 8){
            texto1 = "Complementador";
        }
        
        
        if(marcador2 == 1){
            texto2 = "Semeador";
        }
        else if(marcador2 == 2){
            texto2 = "Investigador";
        }
        else if(marcador2 == 3){
            texto2 = "Monitor";
        }        
        else if(marcador2 == 4){
            texto2 = "Coordenador";
        }
        else if(marcador2 == 5){
            texto2 = "Formatador";
        }
        else if(marcador2 == 6){
            texto2 = "Implementador";
        }
        else if(marcador2 == 7){
            texto2 = "Trabalho em Equipe";
        }
        else if(marcador2 == 8){
            texto2 = "Complementador";
        }
                
                
                
        primeiraCarac = texto1 + " = " + sortArray[marcador1] + "%";
        segundaCarac = texto2 + " = " + sortArray[marcador2] + "%";
    }
    
    
    //8 PERFIS DO MODELO BigFive
    private Integer semeador;       //Criativo + Inteligênte + Introversão + Confiança Mutua + Inteligencia / 5
    private Integer insvestigador;  //Extroversão + Simpátia + Confiança Mutua + Inteligência / 4
    private Integer monitor;        //Inteligencia + Introversão + Postura Critica + Estabilidade + Confiança Mutua + Inteligencia / 6
    private Integer coordenador;    // Assertividade + Estabilidade + Extroversão + Confiança Mutua + Confiança Mutua + Inteligencia / 6
    private Integer formatador;     // Assertividade + Extroversão + Práticidade + !Capacidade de resolver conflitos + Confiança Mutua + Inteligencia / 6
    private Integer implementador;  // Práticidade + Organização + Eficiencia + Estábilidade + Confiança Mutua + Inteligencia / 6
    private Integer trabEquip;      // Capacidade de resolver Conflitos + Boa Comunicação + Simpatia + Confiança mutua + Inteligencia / 5
    private Integer complementador; // Postura Detalhista + Eficiencia + Introversão + Postura Otimista
    
    
    
    
    
    public Integer getInteligente() {
        return inteligente;
    }

    public void setInteligente(Integer inteligente) {
        this.inteligente = inteligente;
    }

    public Integer getAssertivo() {
        return assertivo;
    }

    public void setAssertivo(Integer assertivo) {
        this.assertivo = assertivo;
    }

    public Integer getCapacidadeResolverConflito() {
        return capacidadeResolverConflito;
    }

    public void setCapacidadeResolverConflito(Integer capacidadeResolverConflito) {
        this.capacidadeResolverConflito = capacidadeResolverConflito;
    }

    public Integer getBoaComunicacao() {
        return boaComunicacao;
    }

    public void setBoaComunicacao(Integer boaComunicacao) {
        this.boaComunicacao = boaComunicacao;
    }

    public Integer getExtroversao() {
        return extroversao;
    }

    public void setExtroversao(Integer extroversao) {
        this.extroversao = extroversao;
    }

    public Integer getConfiancaMutua() {
        return confiancaMutua;
    }

    public void setConfiancaMutua(Integer confiancaMutua) {
        this.confiancaMutua = confiancaMutua;
    }

    public Integer getCriatividade() {
        return criatividade;
    }

    public void setCriatividade(Integer criatividade) {
        this.criatividade = criatividade;
    }

    public Integer getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(Integer organizacao) {
        this.organizacao = organizacao;
    }

    public Integer getPostCritica() {
        return postCritica;
    }

    public void setPostCritica(Integer postCritica) {
        this.postCritica = postCritica;
    }

    public Integer getPostOtimista() {
        return postOtimista;
    }

    public void setPostOtimista(Integer postOtimista) {
        this.postOtimista = postOtimista;
    }

    public Integer getPostDetalhista() {
        return postDetalhista;
    }

    public void setPostDetalhista(Integer postDetalhista) {
        this.postDetalhista = postDetalhista;
    }

    public Integer getIntroversao() {
        return introversao;
    }

    public void setIntroversao(Integer introversao) {
        this.introversao = introversao;
    }

    public Integer getEntusiasmo() {
        return entusiasmo;
    }

    public void setEntusiasmo(Integer entusiasmo) {
        this.entusiasmo = entusiasmo;
    }

    public Integer getPraticidade() {
        return praticidade;
    }

    public void setPraticidade(Integer praticidade) {
        this.praticidade = praticidade;
    }

    public Integer getSimpatia() {
        return simpatia;
    }

    public void setSimpatia(Integer simpatia) {
        this.simpatia = simpatia;
    }

    public Integer getEstabilidade() {
        return estabilidade;
    }

    public void setEstabilidade(Integer estabilidade) {
        this.estabilidade = estabilidade;
    }

    public Integer getEficiencia() {
        return eficiencia;
    }    
    
    public void setEficiencia(Integer eficiencia) {
        this.eficiencia = eficiencia;
    }
    
    
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
    
    
    //AS FUNÇÕES ABAIXO CALCULAM AS CARACTERISTICAS DE ACORDO COM AS FACETAS OBTIDAS NO QUESTIONARIO
    
    public void CalcCaracteristicas(Funcionario funcionario){
        CalcCapacidadeResolverConflito(funcionario);
        CalcBoaComunicacao(funcionario);
        CalcExtroversao(funcionario);
        CalcConfiancaMutua(funcionario);
        CalcCriatividade(funcionario);
        CalcInteligencia(funcionario);
        CalcOrganizacao(funcionario);
        CalcPostCritica(funcionario);
        CalcPostOtimista(funcionario);
        CalcPostDetalhista(funcionario);
        CalcIntroversao(funcionario);
        CalcEntusiasmo(funcionario);
        CalcPraticidade(funcionario);
        CalcSimpatia(funcionario);
        CalcEstabilidade(funcionario);
        CalcAssertividade(funcionario);
        CalcEficiencia(funcionario);
                
    }
    
    public void CalcCapacidadeResolverConflito(Funcionario funcionario){
        Integer x;
        
        x = (getAltruista() + getCooperativo()) / 2;
        
        setCapacidadeResolverConflito(x);
    }
    
    public void CalcBoaComunicacao(Funcionario funcionario){
        setBoaComunicacao(getSociavel());
    }
    
    public void CalcExtroversao(Funcionario funcionario){
        Integer x;
        
        x = (getSociavel() + getEntusiastico() + getEnergetico()) / 3;
   
        setExtroversao(x);
    }
    
    public void CalcConfiancaMutua(Funcionario funcionario){
        Integer x;
        
        x = (getConfiavel() + getConfiante()) / 2;
        
        setConfiancaMutua(x);
    }
    
    public void CalcCriatividade(Funcionario funcionario){
        setCriatividade(getEngenhoso());
    }
    
    public void CalcInteligencia(Funcionario funcionario){
        setInteligencia(getInteligente());
    }
    
    public void CalcOrganizacao(Funcionario funcionario){
        setOrganizacao(getOrganizado());
    }
    
    public void CalcPostCritica(Funcionario funcionario){
        setPostCritica(getCritico());
    }
    
    public void CalcPostOtimista(Funcionario funcionario){
        setPostOtimista(getOtimista());
    }
    
    public void CalcPostDetalhista(Funcionario funcionario){
        setPostDetalhista(getMinucioso());
    }
    
    public void CalcIntroversao(Funcionario funcionario){
        Integer x, a, b, c;
        
        a = 100 - getSociavel();
        b = 100 - getEntusiastico();
        c = 100 - getEnergetico();
        
        x = (a + b + c) / 3;
        
        setIntroversao(x);
    }
    
    public void CalcEntusiasmo(Funcionario funcionario){
        setEntusiasmo(getEntusiastico());
    }
    
    public void CalcPraticidade(Funcionario funcionario){
        setPraticidade(getPratico());
    }
    
    public void CalcSimpatia(Funcionario funcionario){
        setSimpatia(getSimpatico());
    }
    
    public void CalcEstabilidade(Funcionario funcionario){
        setEstabilidade(100 - getInstavel());
    }
    
    public void CalcAssertividade(Funcionario funcionario){
        setAssertividade(getAssertivo());
    }
    
    public void CalcEficiencia(Funcionario funcionario){
        setEficiencia(getEficiente());
    }
    
    //AS FUNÇÕES ABAIXO CALCULAM O PERFIL PSICOLOGICO DE ACORDO COM AS CARACTERISTICAS
    
    
    public void CalcPerfil(Funcionario funcionario){
        CalcSemeador(funcionario);
        CalcInsvestigador(funcionario);
        CalcMonitor(funcionario);
        CalcCoordenador(funcionario);
        CalcFormatador(funcionario);
        CalcImplementador(funcionario);
        CalcTrabEquip(funcionario);
        CalcComplementador(funcionario);

    }
    
    public void CalcSemeador(Funcionario funcionario){
        Integer x;
        
        x = (getCriatividade() + getInteligencia() + getIntroversao() + getConfiancaMutua() + getInteligencia()) / 5;
        
        setSemeador(x);
    }
            
    public void CalcInsvestigador(Funcionario funcionario){
        Integer x;
        
        x = (getExtroversao() + getSimpatia() + getConfiancaMutua() + getInteligencia()) / 4;
        
        setInsvestigador(x);
    }        
    
    public void CalcMonitor(Funcionario funcionario){
        Integer x;
        
        x = (getInteligencia() + getIntroversao() + getPostCritica() + getEstabilidade() + getConfiancaMutua() + getInteligencia()) / 6;
        
        setMonitor(x);
    }
    
    public void CalcCoordenador(Funcionario funcionario){
        Integer x;
        
        x = (getAssertividade() + getEstabilidade() + getExtroversao() + getConfiancaMutua() + getConfiancaMutua() + getInteligencia()) / 6;
        
        setCoordenador(x);
    }
    
      public void CalcFormatador(Funcionario funcionario){
        Integer x, a;
        
        a = (100 - getCapacidadeResolverConflito());
        
        x = (getAssertividade() + getExtroversao() + getPraticidade() + a + getConfiancaMutua() + getInteligencia()) / 6;
        
        setFormatador(x);
    }  
    
    public void CalcImplementador(Funcionario funcionario){
        Integer x;
    
        x = (getPraticidade() + getOrganizacao() + getEficiencia() + getEstabilidade() + getConfiancaMutua() + getInteligencia()) / 6;
    
        setImplementador(x);
    
    }  
    
    public void CalcTrabEquip(Funcionario funcionario){
        Integer x;
        
        x = (getCapacidadeResolverConflito() + getBoaComunicacao() + getSimpatia() + getConfiancaMutua() + getInteligencia()) / 5;
        
        setTrabEquip(x);
    }
    
    public void CalcComplementador(Funcionario funcionario){
        Integer x;
        
        x = (getPostDetalhista() + getEficiencia() + getIntroversao() + getPostOtimista() + getConfiancaMutua() + getInteligencia()) / 6;
        
        setComplementador(x);
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
