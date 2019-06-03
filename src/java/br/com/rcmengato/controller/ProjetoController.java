/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Cargo;
import br.com.rcmengato.model.Depoimento;
import br.com.rcmengato.model.EstadoConstantes;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.FuncionarioGrau;
import br.com.rcmengato.model.FuncionarioHabilidade;
import br.com.rcmengato.model.Habilidade;
import br.com.rcmengato.model.Projeto;
import br.com.rcmengato.model.ProjetoFuncionario;
import br.com.rcmengato.model.Ranking;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class ProjetoController implements Serializable {

    private final Dao projetoDao;
    private final Dao funcDao;
    private Projeto projeto;
    private List<Projeto> projetos;
    private List<Habilidade> habSelecionadas;
    private List<Cargo> cargosSelecionados;
    private List<FuncionarioGrau> funcionarioAptos;
    private java.util.Date dataInicio, dataTermino;
    private final double VALOR_NIVEL_BASICO = 0.5;
    private final double VALOR_NIVEL_INTERMEDIARIO = 0.7;
    private final double VALOR_NIVEL_AVANCADO = 1;
    private Ranking ranking;
    @Inject
    private SessionController sessionController;
    private List<Ranking> rankFuncionarios, freelancers;
    private final ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
    private Panel panelEquipe;
    private int numFunc;
    private boolean mostraFinalizados;
    private DataTable listRanking;
    private boolean exibeAjuda;

    public ProjetoController() {
        this.projeto = new Projeto();
        this.projetoDao = new Dao(Projeto.class);
        this.funcDao = new Dao(Funcionario.class);
        this.habSelecionadas = new ArrayList<>();
        this.rankFuncionarios = new ArrayList<>();
        this.freelancers = new ArrayList<>();
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public List<Projeto> getProjetos() {
        if (projetos == null) {
            projetos = projetoDao.executeResultList("Projeto.findAll", new QueryParameter());
        }
        return projetos;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public boolean isExibeAjuda() {
        return exibeAjuda;
    }

    public void setExibeAjuda(boolean exibeAjuda) {
        this.exibeAjuda = exibeAjuda;
    }

    public List<Habilidade> getHabSelecionadas() {
        return habSelecionadas;
    }

    public void setHabSelecionadas(List<Habilidade> habSelecionadas) {
        this.habSelecionadas = habSelecionadas;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public List<FuncionarioGrau> getFuncionarioAptos() {
        return funcionarioAptos;
    }

    public void setFuncionarioAptos(List<FuncionarioGrau> funcionarioAptos) {
        this.funcionarioAptos = funcionarioAptos;
    }

    public List<Cargo> getCargosSelecionados() {
        return cargosSelecionados;
    }

    public void setCargosSelecionados(List<Cargo> cargosSelecionados) {
        this.cargosSelecionados = cargosSelecionados;
    }

    public List<Ranking> getRankFuncionarios() {
        return rankFuncionarios;
    }

    public void setRankFuncionarios(List<Ranking> rankFuncionarios) {
        this.rankFuncionarios = rankFuncionarios;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public Panel getPanelEquipe() {
        return panelEquipe;
    }

    public void setPanelEquipe(Panel panelEquipe) {
        this.panelEquipe = panelEquipe;
    }

    public List<Ranking> getFreelancers() {
        return freelancers;
    }

    public void setFreelancers(List<Ranking> freelancers) {
        this.freelancers = freelancers;
    }

    public DataTable getListRanking() {
        return listRanking;
    }

    public void setListRanking(DataTable listRanking) {
        this.listRanking = listRanking;
    }

    public boolean isMostraFinalizados() {
        return mostraFinalizados;
    }

    public void setMostraFinalizados(boolean mostraFinalizados) {
        this.mostraFinalizados = mostraFinalizados;
    }

    public void exibirAjuda() {
        this.exibeAjuda = true;
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("tabCargos")) {
            if (!existeFuncionarioSuficiente()) {
                JsfUtil.addErrorMessage("Não há funcionários suficiente para atender todos os cargos.");
                return event.getOldStep();
            }
            calcFuncionarioRank();
        }
        if (event.getOldStep().equals("tabAlocar")) {
            if (projeto.getFuncionarios().isEmpty()) {
                JsfUtil.addErrorMessage("Você precisa selecionar alguém para a equipe.");
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    /**
     * Este método verifica se o número de cargos necessários é maior que o
     * número de funcionários;
     *
     * @return True se o número de funcionários for maior que o número de cargos
     * selecionados e false se o número de cargos for maior.
     *
     */
    public boolean existeFuncionarioSuficiente() {
        List<Funcionario> totalFuncionarios = funcDao.findAll();
        if (totalFuncionarios.size() < cargosSelecionados.size()) {
            return false;
        }
        return true;
    }

    public void salvarProjeto() {
        projeto.setStatus(verificaStatusProjeto(projeto));
        for (ProjetoFuncionario f : projeto.getFuncionarios()) {
            funcDao.edit(f.getFuncionario());
        }
        projetoDao.edit(projeto);

        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("index?faces-redirect=true");
    }

    /**
     * Método que calcula o grau de compatibilidade dos funcionários com os
     * cargos que o usuário selecionou. Ele utiliza os funcionários cadastrados
     * na base de dados. A saída deste método é um List da classe Ranking.
     */
    public void calcFuncionarioRank() {
        List<Funcionario> funcionarios = funcDao.findAll();
        numFunc = funcionarios.size();
        int exp = 0, numExp = 0;
        float grau = 0;
        funcionarioAptos = new ArrayList<>();
        //lista de cargos selecionados pelo usuário
        for (int k = 0; k < cargosSelecionados.size(); k++) {

            habSelecionadas.clear();
            habSelecionadas.addAll(cargosSelecionados.get(k).getHabilidades());
            int numHab = habSelecionadas.size();
            //lista de todos os funcionários cadastrados
            for (int i = 0; i < funcionarios.size(); i++) {
                List<FuncionarioHabilidade> habilidades;
                habilidades = funcionarios.get(i).getHabilidades();
                //lista de habilidades dos cargos selecionados
                for (int j = 0; j < habSelecionadas.size(); j++) {
                    //lista de habilidades do funcionário que esta sendo verificado
                    for (int l = 0; l < habilidades.size(); l++) {
                        //compara se a habilidade do funcionário é igual a habilidade do cargo
                        try {
                            if (Objects.equals(habilidades.get(l).getHabilidade().getCodigo(), habSelecionadas.get(j).getCodigo())) {
                                //se as habilidades forem iguais é preciso verificar o nível do funcionário nessa habilidade em questão
                                //dependendo do nível (Básico, Intermediário ou Avançado) é atribuída uma nota para o funcionário
                                switch (habilidades.get(l).getNivel()) {
                                    case "Básico":
                                        grau += VALOR_NIVEL_BASICO;
                                        break;
                                    case "Intermediário":
                                        grau += VALOR_NIVEL_INTERMEDIARIO;
                                        break;
                                    case "Avançado":
                                        grau += VALOR_NIVEL_AVANCADO;
                                        break;
                                }
                            }
                        } catch (NullPointerException ex) {
                            
                        }
                    }
                }
                for (Depoimento d : funcionarios.get(i).getDepoimentos()) {
                    if (d.getCargo().equals(cargosSelecionados.get(k).getNome())) {
                        exp += d.getDesempenho();
                        numExp++;
                    }
                }
                if (numExp != 0) {
                    exp = exp / numExp;
                }
                grau = (grau / numHab) * 100;
                if (funcionarios.get(i).isEhFreelancer()) {
                    freelancers.add(new Ranking(cargosSelecionados.get(k), funcionarios.get(i), grau, exp));
                } else {
                    rankFuncionarios.add(new Ranking(cargosSelecionados.get(k), funcionarios.get(i), grau, exp));
                }
                grau = 0;
                exp = 0;
                numExp = 0;
            }
        }
//        criarDatatable();
    }

    public void alocarFuncionario() {
        if (ranking.getFuncionario().isDisponivel()) {
            if (cargosSelecionados.contains(ranking.getCargo())) {
                ranking.getFuncionario().setDisponivel(false);
                ranking.getFuncionario().setEstado(EstadoConstantes.PROJETO);
                ProjetoFuncionario funcionario = new ProjetoFuncionario(projeto, ranking.getFuncionario(), ranking.getCargo());
                projeto.getFuncionarios().add(funcionario);
                cargosSelecionados.remove(ranking.getCargo());
                JsfUtil.addSuccessMessage("Usuário alocado com sucesso.");
            } else {
                JsfUtil.addErrorMessage("Esse cargo já esta ocupado.");
            }
        } else {
            JsfUtil.addErrorMessage("O usuário selecionado não está disponível.");
        }
        limparFuncionario();
    }

    public void limparFuncionario() {
        ranking = new Ranking();
    }

    public void limparProjeto() {
        projeto = new Projeto();
    }

    public String verificaStatusProjeto(Projeto p) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataInicioProjeto = LocalDate.parse(df.format(p.getDataInicio()), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dataTerminoProjeto = LocalDate.parse(df.format(p.getDataTermino()), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        Period periodo = Period.between(dataInicioProjeto, dataTerminoProjeto);
        if (dataAtual.isBefore(dataInicioProjeto)) {
            return "Em espera";
        } else if (dataAtual.isAfter(dataTerminoProjeto)) {
            return "Finalizado";
        } else if (dataAtual.isAfter(dataInicioProjeto) && dataAtual.isBefore(dataTerminoProjeto)) {
            return "Em execução";
        } else if (dataAtual.isEqual(dataInicioProjeto) || dataAtual.isEqual(dataTerminoProjeto)) {
            return "Em execução";
        }
        return "Indefinido";
    }

    public void preparaEncerrarProjeto() {
        if (projeto.getStatus().equals("Em execução")) {
            sessionController.setTemp(projeto);
            handler.performNavigation("encerrarProjeto?faces-redirect=true");
        } else {
            JsfUtil.addErrorMessage("O projeto precisa estar 'Em execução' para encerrá-lo.");
        }
    }

    /**
     * executa a query que traz os projetos com status de Finalizado e os
     * Projetos normais, é enviado um objeto vazio de QueryParameter pois essa
     * query nao precisa de parametros
     */
    public void carregaProjetoFinalizados() {
        this.projetos = funcDao.executeResultList("Projeto.findAllFinalizados", new QueryParameter());
        mostraFinalizados = true;
    }

    /**
     * seta a lista de projetos como null para que o método getProjetos carregue
     * a lista, seta false na variavel auxiliar para mudar o texto na interface.
     */
    public void ocultaProjetosFinalizados() {
        mostraFinalizados = false;
        projetos = null;
    }

    public void listaFreelancers() {
        if (!freelancers.isEmpty()) {
            rankFuncionarios.addAll(freelancers);
        }
        freelancers.clear();
    }

    public void cancelar() {
        handler.performNavigation("index?faces-redirect=true");
    }
}
