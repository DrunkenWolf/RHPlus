/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Cargo;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Habilidade;
import br.com.rcmengato.model.Projeto;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.persistence.QueryParameter;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class DashController implements Serializable{
    
    private Long numProjetos;
    private Long numFuncionarios;
    private Long numCargos;
    private Long numHabs;
    private final Dao projDao;
    private final Dao funcDao;
    private List<Projeto> projetosEncerrar;
    private PieChartModel grafFuncsDisponiveis;
    private final Dao habDao;
    private final Dao cargoDao;
    private boolean mostrarGraficoPizza = false, mostrarGraficoBarra = false;

    public DashController() {
        this.projDao = new Dao(Projeto.class);
        this.funcDao = new Dao(Funcionario.class);
        this.cargoDao = new Dao(Cargo.class);
        this.habDao = new Dao(Habilidade.class);
    }

    public Long getNumProjetos() {
        if(numProjetos == null){
            numProjetos = buscaTotalProjetos();
        }
        return numProjetos;
    }

    public void setNumProjetos(Long numProjetos) {
        this.numProjetos = numProjetos;
    }

    public Long getNumFuncionarios() {
        if(numFuncionarios == null){
            numFuncionarios = buscaTotalFuncs();
        }
        return numFuncionarios;
    }

    public void setNumFuncionarios(Long numFuncionarios) {
        this.numFuncionarios = numFuncionarios;
    }

    public Long getNumCargos() {
        if(numCargos == null){
            numCargos = (Long) cargoDao.executeSingleResult("Cargo.count", null);
        }
        return numCargos;
    }

    public Long getNumHabs() {
        if(numHabs == null){
            numHabs = (Long) habDao.executeSingleResult("Habilidade.count", null);
        }
        return numHabs;
    }


    public PieChartModel getGrafFuncsDisponiveis() {
        return grafFuncsDisponiveis;
    }

    public List<Projeto> getProjetosEncerrar() {
        if(projetosEncerrar == null){
            projetosEncerrar = buscarProjetos();
        }
        return projetosEncerrar;
    }

    public boolean isMostrarGraficoPizza() {
        if(buscaTotalFuncs() >= 3){
            mostrarGraficoPizza = true;
        }
        return mostrarGraficoPizza;
    }

    public boolean isMostrarGraficoBarra() {
        if(buscaTotalFuncs() >= 3 && buscaTotalProjetos() > 0){
            mostrarGraficoBarra = true;
        }
        return mostrarGraficoBarra;
    }
        
    private Long buscaTotalProjetos(){
        Long total =  (Long) projDao.executeSingleResult("Projeto.count", null);
        return total;
    }
    
    
    private Long buscaTotalFuncs(){
        Long total = (Long) funcDao.executeSingleResult("Funcionario.count", null);
        return total;
    }
    
    private List<Projeto> buscarProjetos(){
        QueryParameter[] params = new QueryParameter[2];
        LocalDate ld = LocalDate.now().plusDays(7);
        System.out.println("ld: " + ld);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date dataDps = Date.from(instant);
        
        params[0] = new QueryParameter("dataAtual", new Date());
        params[1] = new QueryParameter("dataDps", dataDps);
        List<Projeto> result = projDao.executeResultList("Projeto.terminando", params);
        int aux =0;
        for(Projeto p:result){
            if(p.getDataTermino().before(new Date())){
                System.out.println("é antes");
                aux =1;
            }
            else{
                System.out.println("é depois");
                System.out.println(p.getDataTermino() + " | " + new Date());
            }
        }
        if(aux ==1){
            JsfUtil.addWarningMessage("Existem projetos que precisam ser encerrados.");
        }
        Collections.sort(result, new Comparator<Projeto>() {

            @Override
            public int compare(Projeto o1, Projeto o2) {
                if(o1.getStatus().equals("Finalizado")){
                    return 1;
                }
                if(!o1.getStatus().equals("Finalizado")){
                    return -1;
                }
                return 0;
            }
        });
        return result;
    }
    
    
    @PostConstruct
    public void init(){
        criarGraficoFuncs();
    }
    
    private void criarGraficoFuncs(){
        grafFuncsDisponiveis = new PieChartModel();
        int totalDispo=0, totalIndisp =0;
        List<Funcionario> funcs = funcDao.findAll();
        for(Funcionario f: funcs){
            if(f.isDisponivel()){
                totalDispo++;
            }
            else if(!f.isDisponivel()){
                totalIndisp++;
            }
        }
        grafFuncsDisponiveis.set("Disponível", totalDispo);
        grafFuncsDisponiveis.set("Indisponível", totalIndisp);
        grafFuncsDisponiveis.setTitle("Funcionários");
        grafFuncsDisponiveis.setLegendPosition("w");
    }

}
