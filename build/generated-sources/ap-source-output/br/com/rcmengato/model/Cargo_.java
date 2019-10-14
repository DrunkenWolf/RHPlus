package br.com.rcmengato.model;

import br.com.rcmengato.model.Habilidade;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-14T13:57:17")
@StaticMetamodel(Cargo.class)
public class Cargo_ { 

    public static volatile SingularAttribute<Cargo, Integer> codigo;
    public static volatile SingularAttribute<Cargo, String> nome;
    public static volatile ListAttribute<Cargo, Habilidade> habilidades;

}