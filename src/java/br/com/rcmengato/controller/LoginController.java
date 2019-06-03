package br.com.rcmengato.controller;

import br.com.rcmengato.model.TipoUsuario;
import br.com.rcmengato.model.Usuario;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import br.com.rcmengato.util.CriptografiaSenha;
import br.com.rcmengato.util.EnvioDeEmail;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.util.UUID;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class LoginController implements Serializable {

    private Usuario usuario;
    private String email, senha;
    private final Dao usuarioDao;
    @Inject
    private SessionController sessionController;
    private String novaSenha, confirmaNovaSenha;
    private String emailRecuperaSenha;
    private boolean ehPrimeiroLogin;
    private final ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();

    public LoginController() {
        this.usuario = new Usuario();
        this.usuarioDao = new Dao(Usuario.class);
    }

    public void logar() {
        Usuario u = new Usuario(1, "admin@teste.com",
                "Admin", "abc123*", true);
        u.setTipoUsuario(new TipoUsuario(1, "Gerente"));
        sessionController.setUsuarioLogado(u);
        handler.performNavigation("dash?faces-redirect=true");
        /*
        if (!ehPrimeiroLogin) {
            QueryParameter[] qp = new QueryParameter[2];
            qp[0] = new QueryParameter("email", email);
//            qp[1] = new QueryParameter("senha", CriptografiaSenha.encripta(senha));
            qp[1] = new QueryParameter("senha", CriptografiaSenha.encripta(senha));
            usuario = (Usuario) usuarioDao.executeSingleResult("Usuario.findByEmailAndSenha", qp);
            if (usuario != null) {
                sessionController.setUsuarioLogado(usuario);
                if (usuario.isMudarSenha()) {
                    handler.performNavigation("novaSenha?faces-redirect=true");
                } else {
                    JsfUtil.addSuccessMessage("parabéns você logou");
                    handler.performNavigation("dash?faces-redirect=true");
                }
            } else {
                JsfUtil.addErrorMessage("Email ou senha inválidos.");
            }
        } else {

            usuario = (Usuario) usuarioDao.executeSingleResult("Usuario.findByEmail", new QueryParameter("email", email));
            if (usuario != null) {
                if (usuario.isMudarSenha()) {
                    usuario.setMudarSenha(false);
                    usuario = (Usuario) usuarioDao.edit(usuario);
                    sessionController.setUsuarioLogado(usuario);
                    handler.performNavigation("novaSenha?faces-redirect=true");
                } else {
                    JsfUtil.addErrorMessage("Não é o primeiro login deste e-mail.");
                }
            } else {
                JsfUtil.addErrorMessage("Este e-mail não está cadastrado.");
            }
        }
        usuario = new Usuario();*/
    }

    public void logarComoVisitante() {
        usuario = new Usuario(191, "visitante@teste.com", "Visitante", "1234", true);
        sessionController.setUsuarioLogado(usuario);
        handler.performNavigation("dash?faces-redirect=true");
    }

    public void deslogar() {
        sessionController.setUsuarioLogado(new Usuario());
        handler.performNavigation("/index?faces-redirect=true");
    }

    public void trocarSenha() {
        if (novaSenha.equals(confirmaNovaSenha)) {
            sessionController.getUsuarioLogado().setSenha(CriptografiaSenha.encripta(novaSenha));
            sessionController.getUsuarioLogado().setMudarSenha(false);
            usuarioDao.edit(sessionController.getUsuarioLogado());
            handler.performNavigation("dash?faces-redirect=true");
        } else {
            JsfUtil.addErrorMessage("As senhas são diferentes");
        }
        emailRecuperaSenha = null;
    }

    public void esqueceuSenha() {
        System.out.println("email: " + emailRecuperaSenha);
        //busca o usuario por email
        Usuario u = (Usuario) usuarioDao.executeSingleResult("Usuario.findByEmail", new QueryParameter("email", emailRecuperaSenha));
        if (u != null) {
            //gera uma senha randomica
            UUID uuid = UUID.randomUUID();
            String senhaNova = uuid.toString().substring(0, 10);
            System.out.println("senha: " + senhaNova);
            //edita a senha do usuário
            u.setSenha(CriptografiaSenha.encripta(senhaNova));
            u.setMudarSenha(true);
            usuarioDao.edit(u);
            //envia um email com a senha
            String corpoEmail = "Você solicitou uma nova senha no sistema RH Plus, caso você não tenha solicitado"
                    + " desconsidere este email.\nUtilize a senha a seguir para logar no sistema, depois altere sua"
                    + " senha.\n\nSenha: " + senhaNova + " .\n\nEste email não é monitorado, favor não responder.";
            EnvioDeEmail.enviarEmail("Recuperar Senha", corpoEmail, emailRecuperaSenha);

            JsfUtil.addSuccessMessage("Um email com as instruções foi enviado para: " + emailRecuperaSenha);
        } else {
            JsfUtil.addErrorMessage("Email não cadastrado!");
        }
    }

    public boolean isEhPrimeiroLogin() {
        return ehPrimeiroLogin;
    }

    public void setEhPrimeiroLogin(boolean ehPrimeiroLogin) {
        this.ehPrimeiroLogin = ehPrimeiroLogin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaNovaSenha() {
        return confirmaNovaSenha;
    }

    public void setConfirmaNovaSenha(String confirmaNovaSenha) {
        this.confirmaNovaSenha = confirmaNovaSenha;
    }

    public String getEmailRecuperaSenha() {
        return emailRecuperaSenha;
    }

    public void setEmailRecuperaSenha(String emailRecuperaSenha) {
        this.emailRecuperaSenha = emailRecuperaSenha;
    }

}
