/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.TipoUsuario;
import br.com.rcmengato.persistence.Dao;
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

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class TipoUsuarioController implements Serializable{
    private TipoUsuario tipoUsuario;
    private List<TipoUsuario> tiposUsuario;
    private final Dao tipoUsuarioDao;

    public TipoUsuarioController() {
        this.tipoUsuarioDao = new Dao(TipoUsuario.class);
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<TipoUsuario> getTiposUsuario() {
        if(tiposUsuario == null){
            this.tiposUsuario = tipoUsuarioDao.findAll();
        }
        return tiposUsuario;
    }
    
    public TipoUsuario findByKey(Integer key){
        return (TipoUsuario) tipoUsuarioDao.findById(key);
    }
    
    @FacesConverter(value = "tipoUsuarioConverter", forClass = TipoUsuario.class)
    public static class TipoUsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoUsuarioController controller = (TipoUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoUsuarioController");
            try {
                return controller.findByKey(getKey(value));
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
            if (object instanceof TipoUsuario) {
                TipoUsuario o = (TipoUsuario) object;
                return getStringKey(o.getCodigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoUsuario.class.getName()});
                return null;
            }
        }
    }
}
