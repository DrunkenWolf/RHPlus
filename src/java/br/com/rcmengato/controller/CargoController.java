/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Cargo;
import br.com.rcmengato.model.Habilidade;
import br.com.rcmengato.model.Projeto;
import br.com.rcmengato.model.ProjetoFuncionario;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ConfigurableNavigationHandler;
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
public class CargoController implements Serializable {

    private Cargo cargo;
    private Habilidade habilidade;
    private List<Cargo> lstCargos;
    private final Dao cargoDao;
    private final Dao projDao;

    public CargoController() {
        this.cargo = new Cargo();
        this.cargoDao = new Dao(Cargo.class);
        this.projDao = new Dao(Projeto.class);
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Cargo> getLstCargos() {
        if (lstCargos == null) {
            lstCargos = cargoDao.findAll();
        }
        return lstCargos;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }

    public void limparCargo() {
        this.cargo = new Cargo();
        this.habilidade = new Habilidade();
    }

    public void adicionaHabilidade() {
        if (!cargo.getHabilidades().contains(habilidade)) {
            cargo.getHabilidades().add(habilidade);
        } else {
            JsfUtil.addErrorMessage("Esse cargo já possui essa habilidade.");
        }
    }

    public void removeHabilidade() {
        cargo.getHabilidades().remove(habilidade);
    }

    public void salvarCargo() {
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        cargoDao.edit(cargo);
        lstCargos = null;
        JsfUtil.addSuccessMessage("Cargo salvo com sucesso!");
        limparCargo();
    }

    public void removerCargo() {
        cargo = (Cargo) cargoDao.edit(cargo);
        List<Projeto> projetos = projDao.findAll();
        boolean achou = false;
        for (Projeto p : projetos) {
            for (ProjetoFuncionario pf : p.getFuncionarios()) {
                if (pf.getCargo().equals(cargo)) {
                    achou = true;
                    break;
                }
            }
        }
        if (!achou) {
            cargoDao.remove(cargo);
            lstCargos = null;
            JsfUtil.addSuccessMessage("Cargo deletado com sucesso!");
        }
        else{
            JsfUtil.addErrorMessage("Esse cargo não pode ser excluído. Verifique se não há algum projeto refenciando o cargo.");
        }
        limparCargo();
        
    }

    public Cargo findById(Integer key) {
        return (Cargo) cargoDao.findById(key);
    }

    @FacesConverter(value = "cargoConverter", forClass = Cargo.class)
    public static class CargoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CargoController controller = (CargoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cargoController");
            try {
                return controller.findById(getKey(value));
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
            if (object instanceof Cargo) {
                Cargo o = (Cargo) object;
                return getStringKey(o.getCodigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cargo.class.getName()});
                return null;
            }
        }
    }
}
