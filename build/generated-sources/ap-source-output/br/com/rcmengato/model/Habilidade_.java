package br.com.rcmengato.model;

import br.com.rcmengato.model.FuncionarioHabilidade;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-29T14:47:53")
@StaticMetamodel(Habilidade.class)
public class Habilidade_ { 

    public static volatile SingularAttribute<Habilidade, Integer> codigo;
    public static volatile SingularAttribute<Habilidade, String> tipo;
    public static volatile SingularAttribute<Habilidade, String> nome;
    public static volatile ListAttribute<Habilidade, FuncionarioHabilidade> funcionarios;

}