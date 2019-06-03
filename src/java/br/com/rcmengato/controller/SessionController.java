/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Usuario;
import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.dialog.Dialog;

/**
 *
 * @author ronaldo
 */
@Named
@SessionScoped
public class SessionController implements Serializable {

    private Usuario usuarioLogado;
    private Object temp;
    private Funcionario funcTemp;
    private Dialog dlgMeusDados;
    private String textoAjuda;
    private int tipoUsuarioFuncionario;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public int getTipoUsuarioFuncionario() {
        return tipoUsuarioFuncionario;
    }

    public void setTipoUsuarioFuncionario(int tipoUsuarioFuncionario) {
        this.tipoUsuarioFuncionario = tipoUsuarioFuncionario;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Object getTemp() {
        return temp;
    }

    public void setTemp(Object temp) {
        this.temp = temp;
    }

    public Funcionario getFuncTemp() {
        return funcTemp;
    }

    public void setFuncTemp(Funcionario funcTemp) {
        this.funcTemp = funcTemp;
    }

    public String getTextoAjuda() {
        return textoAjuda;
    }

    public void setTextoAjuda(String textoAjuda) {
        this.textoAjuda = textoAjuda;
    }

    public void limparAjuda(){
        this.textoAjuda = "";
    }
    
    public boolean isLogado() {
        try {
            return usuarioLogado.getCodigo() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
    
    public Dialog getDlgMeusDados() {
        return dlgMeusDados;
    }

    public void setDlgMeusDados(Dialog dlgMeusDados) {
        this.dlgMeusDados = dlgMeusDados;
    }
    
    public void mudarIdioma(String idioma){
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = new Locale(idioma);
        //muda a locale para a tela atual
        context.getViewRoot().setLocale(locale);
        
        //muda a locale para a aplicação inteira
        context.getApplication().setDefaultLocale(locale);
    }
    
    public void mostrarDlgMeusDados(){
        dlgMeusDados.setVisible(true);
    }
}
