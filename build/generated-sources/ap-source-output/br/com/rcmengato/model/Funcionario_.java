package br.com.rcmengato.model;

import br.com.rcmengato.model.Depoimento;
import br.com.rcmengato.model.Endereco;
import br.com.rcmengato.model.FormacaoAcademica;
import br.com.rcmengato.model.FuncionarioHabilidade;
import br.com.rcmengato.model.ProjetoFuncionario;
import br.com.rcmengato.model.Treinamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-14T13:57:17")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ { 

    public static volatile SingularAttribute<Funcionario, String> telefone;
    public static volatile SingularAttribute<Funcionario, String> estado;
    public static volatile ListAttribute<Funcionario, FormacaoAcademica> formacaoAcademica;
    public static volatile SingularAttribute<Funcionario, Boolean> ehFreelancer;
    public static volatile ListAttribute<Funcionario, ProjetoFuncionario> projetos;
    public static volatile SingularAttribute<Funcionario, Integer> semeador;
    public static volatile SingularAttribute<Funcionario, Integer> insvestigador;
    public static volatile SingularAttribute<Funcionario, String> dataAdmissao;
    public static volatile SingularAttribute<Funcionario, Integer> implementador;
    public static volatile SingularAttribute<Funcionario, Integer> pratico;
    public static volatile SingularAttribute<Funcionario, Integer> trabEquip;
    public static volatile SingularAttribute<Funcionario, Integer> complementador;
    public static volatile SingularAttribute<Funcionario, Integer> engenhoso;
    public static volatile SingularAttribute<Funcionario, Integer> energetico;
    public static volatile SingularAttribute<Funcionario, Integer> minucioso;
    public static volatile SingularAttribute<Funcionario, String> cpf;
    public static volatile SingularAttribute<Funcionario, Boolean> disponivel;
    public static volatile SingularAttribute<Funcionario, Integer> coordenador;
    public static volatile SingularAttribute<Funcionario, String> dataNascimento;
    public static volatile SingularAttribute<Funcionario, Integer> entusiastico;
    public static volatile SingularAttribute<Funcionario, String> email;
    public static volatile ListAttribute<Funcionario, FuncionarioHabilidade> habilidades;
    public static volatile SingularAttribute<Funcionario, Integer> codigo;
    public static volatile SingularAttribute<Funcionario, Endereco> endereco;
    public static volatile SingularAttribute<Funcionario, Integer> eficiente;
    public static volatile SingularAttribute<Funcionario, Integer> inteligencia;
    public static volatile SingularAttribute<Funcionario, Integer> simpatico;
    public static volatile SingularAttribute<Funcionario, Integer> altruista;
    public static volatile SingularAttribute<Funcionario, Integer> confiante;
    public static volatile SingularAttribute<Funcionario, String> nome;
    public static volatile SingularAttribute<Funcionario, Integer> monitor;
    public static volatile SingularAttribute<Funcionario, Integer> confiavel;
    public static volatile SingularAttribute<Funcionario, Integer> formatador;
    public static volatile SingularAttribute<Funcionario, Integer> cooperativo;
    public static volatile SingularAttribute<Funcionario, String> likedin;
    public static volatile SingularAttribute<Funcionario, Integer> ratingGeral;
    public static volatile SingularAttribute<Funcionario, Integer> otimista;
    public static volatile SingularAttribute<Funcionario, String> foto;
    public static volatile SingularAttribute<Funcionario, Integer> instavel;
    public static volatile SingularAttribute<Funcionario, Integer> critico;
    public static volatile SingularAttribute<Funcionario, String> rg;
    public static volatile ListAttribute<Funcionario, Treinamento> treinamentos;
    public static volatile SingularAttribute<Funcionario, Integer> organizado;
    public static volatile SingularAttribute<Funcionario, String> sexo;
    public static volatile SingularAttribute<Funcionario, Integer> sociavel;
    public static volatile ListAttribute<Funcionario, Depoimento> depoimentos;
    public static volatile SingularAttribute<Funcionario, Integer> assertividade;

}