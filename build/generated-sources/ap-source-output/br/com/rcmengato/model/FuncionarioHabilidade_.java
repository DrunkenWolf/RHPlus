package br.com.rcmengato.model;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Habilidade;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-14T13:57:17")
@StaticMetamodel(FuncionarioHabilidade.class)
public class FuncionarioHabilidade_ { 

    public static volatile SingularAttribute<FuncionarioHabilidade, Integer> codigo;
    public static volatile SingularAttribute<FuncionarioHabilidade, Funcionario> funcionario;
    public static volatile SingularAttribute<FuncionarioHabilidade, Habilidade> habilidade;
    public static volatile SingularAttribute<FuncionarioHabilidade, String> nivel;

}