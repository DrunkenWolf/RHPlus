package br.com.rcmengato.model;

import br.com.rcmengato.model.Cargo;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Projeto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-14T13:57:17")
@StaticMetamodel(ProjetoFuncionario.class)
public class ProjetoFuncionario_ { 

    public static volatile SingularAttribute<ProjetoFuncionario, Integer> codigo;
    public static volatile SingularAttribute<ProjetoFuncionario, Projeto> projeto;
    public static volatile SingularAttribute<ProjetoFuncionario, Funcionario> funcionario;
    public static volatile SingularAttribute<ProjetoFuncionario, Cargo> cargo;

}