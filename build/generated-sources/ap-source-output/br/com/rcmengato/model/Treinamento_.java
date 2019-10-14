package br.com.rcmengato.model;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Habilidade;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-14T13:57:17")
@StaticMetamodel(Treinamento.class)
public class Treinamento_ { 

    public static volatile SingularAttribute<Treinamento, Habilidade> assunto;
    public static volatile SingularAttribute<Treinamento, Integer> codigo;
    public static volatile SingularAttribute<Treinamento, Boolean> ativo;
    public static volatile SingularAttribute<Treinamento, Date> dataTermino;
    public static volatile SingularAttribute<Treinamento, Date> dataInicio;
    public static volatile ListAttribute<Treinamento, Funcionario> funcionarios;

}