/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Usuario;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.util.CriptografiaSenha;
import br.com.rcmengato.util.EnvioDeEmail;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ronaldo
 */
@Named
@javax.faces.view.ViewScoped
public class UsuarioController implements Serializable {

    private Usuario usuario;
    private final Dao usuarioDao;
    private String senhaAtual, novaSenha;
    @Inject
    private SessionController sc;

    public UsuarioController() {
        this.usuarioDao = new Dao(Usuario.class);
        this.usuario = new Usuario();
    }

    public void salvarUsuario() {
//        //gera uma senha aleatória
//        UUID uuid = UUID.randomUUID();
//        String senhaNova = uuid.toString().substring(0, 20);
//        //setta a nova senha no usuario
//        usuario.setSenha(CriptografiaSenha.encripta(senhaNova));
        //esse campo vai obrigar o usuario a cadastrar uma nova senha no primeiro login
        //dele
        usuario.setMudarSenha(true);       
        usuario = (Usuario) usuarioDao.edit(usuario);
        limpaUsuario();
        RequestContext.getCurrentInstance().execute("PF('dlgCadastraUsuario').hide()");
    }

    public void deletarUsuario() {
        usuario = (Usuario) usuarioDao.edit(usuario);
        usuarioDao.remove(usuario);
    }

    public void editarUsuario() {
        usuario = (Usuario) usuarioDao.edit(usuario);
        sc.setUsuarioLogado(usuario);
        limpaUsuario();
        JsfUtil.addSuccessMessage("Seus dados foram salvos com sucesso!");
    }

    public void trocarSenha() {
        if (CriptografiaSenha.encripta(senhaAtual).equals(usuario.getSenha())) {
            //senha informada é igual a senha do usuário
            usuario.setSenha(CriptografiaSenha.encripta(novaSenha));
            editarUsuario();
        } else {
            JsfUtil.addErrorMessage("Senha informada é diferente da senha cadastrada.");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return usuarioDao.findAll();
    }

    public void limpaUsuario() {
        this.usuario = new Usuario();
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

}
