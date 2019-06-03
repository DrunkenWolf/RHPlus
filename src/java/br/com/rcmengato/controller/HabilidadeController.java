/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Habilidade;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class HabilidadeController implements Serializable {

    private final Dao habilidadeDao;
    private List<Habilidade> habilidades;
    private Habilidade novaHabilidade;
    private List<Habilidade> habilidadesTecnicas;

    public HabilidadeController() {
        this.habilidadeDao = new Dao(Habilidade.class);
        this.novaHabilidade = new Habilidade();
    }

    public List<Habilidade> getHabilidades() {
        if (habilidades == null) {
            habilidades = habilidadeDao.executeResultList("habilidades", new QueryParameter());
        }
        return habilidades;
    }

    public Habilidade getNovaHabilidade() {
        return novaHabilidade;
    }

    public void setNovaHabilidade(Habilidade novaHabilidade) {
        this.novaHabilidade = novaHabilidade;
    }

    public List<Habilidade> getHabilidadesTecnicas() {
        if (habilidadesTecnicas == null) {
            habilidadesTecnicas = habilidadeDao.executeResultList("habilidades_tecnicas", new QueryParameter());
        }
        return habilidadesTecnicas;
    }

    public List<Habilidade> getHabilidadesIdioma() {
        return habilidadeDao.executeResultList("habilidades_idioma", new QueryParameter());
    }

    public List<Habilidade> getHabilidadesDominio() {
        return habilidadeDao.executeResultList("habilidades_dominio", new QueryParameter());
    }

    public void salvarHabilidade() {
        habilidadeDao.edit(novaHabilidade);
        habilidades = null; //seta a lista como nulo para que o get busque do banco
        limparHabilidade();
        RequestContext.getCurrentInstance().execute("PF('dlgNovaHabilidade').hide()");
    }

    public void limparHabilidade() {
        novaHabilidade = new Habilidade();
    }

    public void removerHabilidade() {
        novaHabilidade = (Habilidade) habilidadeDao.edit(novaHabilidade);
        habilidadeDao.remove(novaHabilidade);
        limparHabilidade();
        habilidades = null; //seta a lista como nulo para que o get busque do banco
    }
    
    public Habilidade findbyId(Integer id){
        return (Habilidade) habilidadeDao.findById(id);
    }
    
        @FacesConverter(value = "habilidadeConverter", forClass = Habilidade.class)
    public static class HabilidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HabilidadeController controller = (HabilidadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "habilidadeController");
            try {
                return controller.findbyId(getKey(value));
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Habilidade) {
                Habilidade o = (Habilidade) object;
                return getStringKey(o.getCodigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Habilidade.class.getName()});
                return null;
            }
        }
    }
}
