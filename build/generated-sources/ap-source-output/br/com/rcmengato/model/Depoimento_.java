package br.com.rcmengato.model;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Projeto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-29T14:47:53")
@StaticMetamodel(Depoimento.class)
public class Depoimento_ { 

    public static volatile SingularAttribute<Depoimento, Integer> desempenho;
    public static volatile SingularAttribute<Depoimento, Integer> codigo;
    public static volatile SingularAttribute<Depoimento, Projeto> projeto;
    public static volatile SingularAttribute<Depoimento, Funcionario> funcionario;
    public static volatile SingularAttribute<Depoimento, String> comentario;
    public static volatile SingularAttribute<Depoimento, String> cargo;

}