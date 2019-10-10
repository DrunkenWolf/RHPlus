package br.com.rcmengato.model;

import br.com.rcmengato.model.ProjetoFuncionario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T17:33:47")
@StaticMetamodel(Projeto.class)
public class Projeto_ { 

    public static volatile SingularAttribute<Projeto, Integer> codigo;
    public static volatile SingularAttribute<Projeto, Date> dataEncerrado;
    public static volatile SingularAttribute<Projeto, Date> dataTermino;
    public static volatile SingularAttribute<Projeto, String> nome;
    public static volatile SingularAttribute<Projeto, Date> dataInicio;
    public static volatile ListAttribute<Projeto, ProjetoFuncionario> funcionarios;
    public static volatile SingularAttribute<Projeto, String> descricao;
    public static volatile SingularAttribute<Projeto, String> status;

}