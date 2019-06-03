/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.filtro;

import br.com.rcmengato.controller.SessionController;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ronaldo
 */
public class Filtro implements PhaseListener {

    @Inject
    private SessionController sessionController;

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String pagina = facesContext.getViewRoot().getViewId();
//        System.out.println("pagina: " + pagina);
        if (pagina.contains("/index.xhtml")) {
            return;
        }
        if (!sessionController.isLogado()) {
//            System.out.println("nao ta logado");
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "/index.html?faces-redirect=true");
            facesContext.renderResponse();
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
