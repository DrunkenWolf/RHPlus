/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.EstadoConstantes;
import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Treinamento;
import br.com.rcmengato.persistence.Dao;
import br.com.rcmengato.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class TreinamentoController implements Serializable {

    private final Dao treinamentoDao, funcDao;
    private List<Treinamento> treinamentos;
    private Treinamento treinamento;
    private Funcionario funcionario;

    public TreinamentoController() {
        this.treinamentoDao = new Dao(Treinamento.class);
        this.funcDao = new Dao(Funcionario.class);
        this.treinamento = new Treinamento(true);
        this.funcionario = new Funcionario();
    }

    public void salvarTreinamento() {
        for (Funcionario f : treinamento.getFuncionarios()) {
            if (!f.isDisponivel()) {
                if (!treinamento.getFuncionarios().contains(f)) {
                    JsfUtil.addErrorMessage("O funcionário " + f.getNome() + " não está diponível");
                    return;
                }
            }
            f.setDisponivel(false);
            f.setEstado(EstadoConstantes.TREINAMENTO);
            funcDao.edit(f);
        }
        if (treinamento.getCodigo() == null) {
            treinamento.setAtivo(true);
        }
        treinamentoDao.edit(treinamento);
        treinamentos = null;
        JsfUtil.addSuccessMessage("Treinamento salvo com sucesso!");
        limparTreinamento();
        RequestContext.getCurrentInstance().execute("PF('dlgNovoTreinamento').hide()");
    }

    public void excluirTreinamento() {
        if (!treinamento.getFuncionarios().isEmpty()) {
            for (Funcionario f : treinamento.getFuncionarios()) {
                f.setDisponivel(true);
                f.setEstado(EstadoConstantes.DISPONIVEL);
                funcDao.edit(f);
            }
        }
        treinamento.getFuncionarios().clear();
        treinamento = (Treinamento) treinamentoDao.edit(treinamento);
        treinamentoDao.remove(treinamento);
        treinamentos = null;
        JsfUtil.addSuccessMessage("Treinamento excluído com sucesso.");
    }

    public void limparTreinamento() {
        this.treinamento = new Treinamento();
        this.funcionario = new Funcionario();
    }

    public Treinamento getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Treinamento treinamento) {
        this.treinamento = treinamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Treinamento> getTreinamentos() {
        if (treinamentos == null) {
            treinamentos = treinamentoDao.findAll();
        }
        return treinamentos;
    }

}
