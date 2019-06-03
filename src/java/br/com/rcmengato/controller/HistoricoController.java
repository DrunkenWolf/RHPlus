/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.persistence.Dao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.rcmengato.model.Depoimento;
import br.com.rcmengato.persistence.QueryParameter;
import java.util.Collections;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class HistoricoController implements Serializable{
    
    private Funcionario funcionario;
    @Inject
    private SessionController sessionController;
    private final Dao funcDao, depoDao;
    private PieChartModel pizza;
    
    private List<Depoimento> depoimentos;

    public HistoricoController() {
        pizza = new PieChartModel();
        this.funcDao = new Dao(Funcionario.class);
        this.depoDao = new Dao(Depoimento.class);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Depoimento> getDepoimentos() {
        return depoimentos;
    }

    public void setDepoimentos(List<Depoimento> depoimentos) {
        this.depoimentos = depoimentos;
    }

    public PieChartModel getPizza() {
        return pizza;
    }

    public void setPizza(PieChartModel pizza) {
        this.pizza = pizza;
    }
    
    @PostConstruct
    public void init(){
        funcionario = sessionController.getFuncTemp();
        funcionario = (Funcionario) funcDao.findById(funcionario.getCodigo());
        depoimentos = depoDao.executeResultList("Depoimento.findByFuncionario", new QueryParameter("codFuncionario", funcionario.getCodigo()));
        Collections.sort(depoimentos);
        sessionController.setTemp(new Object());
        criarGrafico();
    }
    
    private void criarGrafico(){
        int positivos=0,negativos =0;
        for(Depoimento d: depoimentos){
            if(d.getDesempenho().equals("POSITIVO")){
                positivos++;
            }
            else if(d.getDesempenho().equals("NEGATIVO")){
                negativos++;
            }
        }
        
        pizza.set("Positivos", positivos);
        pizza.set("Negativos", negativos);
        pizza.setTitle("Desempenhos");
        pizza.setLegendPosition("w");
    }
}
