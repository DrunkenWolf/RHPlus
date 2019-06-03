/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.EstadoConstantes;
import br.com.rcmengato.model.FormacaoAcademica;
import br.com.rcmengato.model.Habilidade;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.FuncionarioHabilidade;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class FuncionarioController implements Serializable {

    private Funcionario funcionario;
    private FuncionarioHabilidade funcHabilidade;
    private final Dao funcionaioDao;
    private final Dao habilidadeDao;
    private List<Funcionario> funcionarios;
    private Funcionario funcEditar;
    private javax.servlet.http.Part foto;
    @Inject
    private SessionController sessionController;
    private boolean mostraFreelancers;
    private List<String> niveisFormacao = Arrays.asList("Graduação", "Doutorado", "Especialização",
            "Mestrado", "MBA", "Pós-doutorado", "Técnologo");
    private FormacaoAcademica novaFormacao;

    public FuncionarioController() {
        this.novaFormacao = new FormacaoAcademica();
        this.funcionario = new Funcionario();
        this.funcionaioDao = new Dao(Funcionario.class);
        this.habilidadeDao = new Dao(Habilidade.class);
        this.funcHabilidade = new FuncionarioHabilidade();
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public FuncionarioHabilidade getFuncHabilidade() {
        return funcHabilidade;
    }

    public void setFuncHabilidade(FuncionarioHabilidade funcHabilidade) {
        this.funcHabilidade = funcHabilidade;
    }

    public Part getFoto() {
        return foto;
    }

    public void setFoto(Part foto) {
        this.foto = foto;
    }

    public boolean isMostraFreelancers() {
        return mostraFreelancers;
    }

    public void setMostraFreelancers(boolean mostraFreelancers) {
        this.mostraFreelancers = mostraFreelancers;
    }

    public void salvar() {
        funcionario.setDisponivel(true);
        funcionario.setEstado(EstadoConstantes.DISPONIVEL);
        funcionaioDao.edit(funcionario);
        funcionarios = null;
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("index?faces-redirect=true");
    }

    /**
     * Este método salva o objeto funcEditar e redireciona a página para o index
     * do funcionário. Ele é invocado na página editar pelo botão de confirmar
     * ou de sair,
     */
    public void editar() {
        funcionaioDao.edit(funcEditar);
        funcionarios = null;
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("index?faces-redirect=true");
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public List<Funcionario> getFuncionarios() {
        if (funcionarios == null) {
            funcionarios = funcionaioDao.executeResultList("Funcionario.findAll", new QueryParameter());
        }
        return funcionarios;
    }

    public void addFormacao(int editar) {
        if (editar == 0) {
            funcionario.getFormacaoAcademica().add(novaFormacao);
            novaFormacao = new FormacaoAcademica();
        } else if (editar == 1) {
            funcEditar.getFormacaoAcademica().add(novaFormacao);
            novaFormacao = new FormacaoAcademica();
        }
    }

    public void removerFormacao(int editar) {
        if (editar == 0) {
            funcionario.getFormacaoAcademica().remove(novaFormacao);
            novaFormacao = new FormacaoAcademica();
        } else if (editar == 1) {
            funcEditar.getFormacaoAcademica().remove(novaFormacao);
            novaFormacao = new FormacaoAcademica();
        }
    }

    /**
     * Metódo adiciona a habilidade no funcionario e vice-versa.
     *
     * @param opcao significa em qual objeto sera adicionada a habilidade, se
     * for 0 - é no objeto funcionario (usado na pagina de cadastrar) se for 1 é
     * no objeto funcEditar (usado na pagina de edicao)
     */
    public void addConhecimento(int opcao) {
        System.out.println("addConhecimento...\nopcao: " + opcao);
        //if (!funcionario.getHabilidades().contains(funcHabilidade)) {
        if (opcao == 0) {
            funcionario.getHabilidades().add(funcHabilidade);
            funcHabilidade.setFuncionario(funcionario);
        } else if (opcao == 1) {
            funcEditar.getHabilidades().add(funcHabilidade);
            funcHabilidade.setFuncionario(funcEditar);
        }
//}
//        else{
//            JsfUtil.addErrorMessage("Você já inseriu essa habilidade");
//        }
        funcHabilidade = new FuncionarioHabilidade();
    }

    public void removeConhecimento(int editar) {
        if (editar == 0) {
            funcionario.getHabilidades().remove(funcHabilidade);
            funcHabilidade = new FuncionarioHabilidade();
        }else if(editar == 1){
            funcEditar.getHabilidades().remove(funcHabilidade);
            funcHabilidade = new FuncionarioHabilidade();
        }
    }

    public void fotoUploadHandler() {
        System.out.println("foto upload handler...");
        System.out.println(foto.getName());
    }

    public String limparFuncionario() {
        this.funcionario = new Funcionario();
        this.funcEditar = new Funcionario();
        return "index?faces-redirect=true";
    }

    public void excluirFuncioario() {
        funcionario = (Funcionario) funcionaioDao.edit(funcionario);
        funcionaioDao.remove(funcionario);
    }

    public String preparaHistorico() {
        sessionController.setFuncTemp(funcionario);
        return "historico?faces-redirect=true";
    }

    public Funcionario findbyId(Integer key) {
        return (Funcionario) funcionaioDao.findById(key);
    }

    @FacesConverter(value = "funcionarioConverter", forClass = Funcionario.class)
    public static class FuncionarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FuncionarioController controller = (FuncionarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "funcionarioController");
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
            if (object instanceof Funcionario) {
                Funcionario o = (Funcionario) object;
                return getStringKey(o.getCodigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Funcionario.class.getName()});
                return null;
            }
        }
    }

    public void mostrarFreelancers() {
        this.funcionarios = funcionaioDao.executeResultList("Funcionario.findAllFreelancer", new QueryParameter());
        this.mostraFreelancers = true;
    }

    public void ocultaFreelancers() {
        this.funcionarios = null;
        this.mostraFreelancers = false;
    }

    public Funcionario getFuncEditar() {
        return funcEditar;
    }

    public void setFuncEditar(Funcionario funcEditar) {
        this.funcEditar = funcEditar;
    }

    public List<String> getNiveisFormacao() {
        return niveisFormacao;
    }

    public void setNiveisFormacao(List<String> niveisFormacao) {
        this.niveisFormacao = niveisFormacao;
    }

    public FormacaoAcademica getNovaFormacao() {
        return novaFormacao;
    }

    public void setNovaFormacao(FormacaoAcademica novaFormacao) {
        this.novaFormacao = novaFormacao;
    }

    public String preparaEditar() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).setAttribute("funcEditar", funcEditar);
        return "editar?faces-redirect=true";
    }

    @PostConstruct
    public void init() {
        funcEditar = (Funcionario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("funcEditar");
    }
}
