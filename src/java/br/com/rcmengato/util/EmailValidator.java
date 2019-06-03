/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.util;

import br.com.rcmengato.controller.FuncionarioController;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Usuario;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ronaldo
 */
@Named
@RequestScoped
public class EmailValidator implements Validator {

    private Pattern pattern;
    private Matcher matcher;
    private final Dao usuarioDao = new Dao(Usuario.class);
    private final Dao funcDao = new Dao(Funcionario.class);
    @Inject
    private FuncionarioController controller;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        pattern = Pattern.compile(EMAIL_PATTERN);
        String email = (String) o;
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            //email invalido
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este email não é inválido!", null);
            throw new ValidatorException(message);
        } else {
            Usuario u = (Usuario) usuarioDao.executeSingleResult("Usuario.findByEmail", new QueryParameter("email", email));
            if (u != null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este email já existe no banco de dados!", null);
                throw new ValidatorException(message);
            } else {
                Funcionario f = (Funcionario) funcDao.executeSingleResult("Funcionario.finByEmail", new QueryParameter("email", email));
                if (f != null) {
                    if (controller.getFuncEditar()!= null) {
                        if (!f.getEmail().equals(controller.getFuncEditar().getEmail())) {
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este email já existe no banco de dados!", null);
                            throw new ValidatorException(message);
                        }
                    }
                }
            }
        }
    }

}
