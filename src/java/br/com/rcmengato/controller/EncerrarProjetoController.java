/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.controller;

import br.com.rcmengato.model.Funcionario;
import br.com.rcmengato.model.Projeto;
import br.com.rcmengato.persistence.Dao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import br.com.rcmengato.model.Depoimento;
import br.com.rcmengato.model.EstadoConstantes;
import br.com.rcmengato.model.FuncionarioHabilidade;
import br.com.rcmengato.model.ProjetoFuncionario;
import br.com.rcmengato.persistence.QueryParameter;
import br.com.rcmengato.util.JsfUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ronaldo
 */
@Named
@ViewScoped
public class EncerrarProjetoController implements Serializable {

    private Projeto projeto;
    private final Dao projDao;
    @Inject
    private SessionController sessionController;
    private ProjetoFuncionario funcionario;
    private Depoimento depoimento;
    private List<Depoimento> depoimentos;
    private final Dao depoDao;
    private final Dao funcDao;
    private boolean encerrou = false;
    private FuncionarioHabilidade novaHabilidade;

    public EncerrarProjetoController() {
        this.projDao = new Dao(Projeto.class);
        this.depoDao = new Dao(Depoimento.class);
        this.depoimento = new Depoimento();
        this.funcDao = new Dao(Funcionario.class);
        this.novaHabilidade = new FuncionarioHabilidade();
        this.depoimentos = new ArrayList<>();
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public ProjetoFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(ProjetoFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public Depoimento getDepoimento() {
        return depoimento;
    }

    public void setDepoimento(Depoimento depoimento) {
        this.depoimento = depoimento;
    }

    public boolean isEncerrou() {
        return encerrou;
    }

    public void setEncerrou(boolean encerrou) {
        this.encerrou = encerrou;
    }

    public void onRowSelect(SelectEvent event) {
        funcionario = (ProjetoFuncionario) event.getObject();
//        System.out.println("fun cod: " + funcionario.getCodigo());
    }

    public void onRowUnselect(UnselectEvent event) {
//        funcionario.setFuncionario(new Funcionario());
    }

    @PostConstruct
    public void init() {
        projeto = (Projeto) sessionController.getTemp();
        sessionController.setTemp(null);
    }

    public void salvarDepoimento() {
        depoimento.setProjeto(projeto);
        depoimento.setFuncionario(funcionario.getFuncionario());
        depoimento.setCargo(funcionario.getCargo().getNome());
        depoimentos.add(depoimento);
        projeto.getFuncionarios().remove(funcionario);
        if (projeto.getFuncionarios().isEmpty()) {
            encerrarProjeto();
            limparProjeto();
        }
        limparFuncionario();
        limparDepoimento();
    }

    public void limparDepoimento() {
        this.depoimento = new Depoimento();
    }

    public void limparFuncionario() {
        funcionario.setFuncionario(new Funcionario());
    }

    public void limparProjeto() {
        projeto = new Projeto();
    }

    public void limpaHabilidade() {
        novaHabilidade = new FuncionarioHabilidade();
    }

    public FuncionarioHabilidade getNovaHabilidade() {
        return novaHabilidade;
    }

    public void setNovaHabilidade(FuncionarioHabilidade novaHabilidade) {
        this.novaHabilidade = novaHabilidade;
    }

    public void encerrarProjeto() {
        System.out.println("encerrar projeto...");
        //busca o projeto do banco
        projeto = (Projeto) projDao.findById(projeto.getCodigo());

        //salva os depoimentos
        for(Depoimento d:depoimentos){
            depoDao.edit(d);
        }
        //limpa a lista de depoimentos
        depoimentos.clear();

        //seta o projeto como finalizado e seta a data atual no campo DataEncerrado
        projeto.setStatus("Finalizado");
        projeto.setDataEncerrado(new Date());
        //salva o projeto
        projDao.edit(projeto);
        //altera o status dos funcionarios que estavam no projeto como disponíveis
        for (ProjetoFuncionario f : projeto.getFuncionarios()) {
            f.getFuncionario().setDisponivel(true);
            f.getFuncionario().setEstado(EstadoConstantes.DISPONIVEL);
            f.getFuncionario().setRatingGeral(calculaRatingGeral(f.getFuncionario()));
            funcDao.edit(f.getFuncionario());
        }
        //flag para alterar o botao na pagina
        encerrou = true;
        limparFuncionario();
        JsfUtil.addSuccessMessage("Avaliações de desempenho salvas com sucesso!");
    }

    public Integer calculaRatingGeral(Funcionario f) {
        f = (Funcionario) funcDao.findById(f.getCodigo());
        Integer rating = 0;
        List<Depoimento> depoimentos = depoDao.executeResultList("Depoimento.findByFuncionario", new QueryParameter("codFuncionario", f.getCodigo()));
        Integer numProjetos = depoimentos.size();
        System.out.println("numProjetos: " + numProjetos);
        for (Depoimento d : depoimentos) {
            rating += d.getDesempenho();
        }
        try {
            rating = rating / numProjetos;
        } catch (ArithmeticException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }

        System.out.println("rating: " + rating);
        return rating;
    }

    public void salvarHabilidade() {
        novaHabilidade.setFuncionario(funcionario.getFuncionario());
        for (FuncionarioHabilidade fh : funcionario.getFuncionario().getHabilidades()) {
            if (fh.getHabilidade().getNome().equals(novaHabilidade.getHabilidade().getNome())) {
                funcionario.getFuncionario().getHabilidades().remove(fh);
                funcionario.getFuncionario().getHabilidades().add(novaHabilidade);
                funcDao.edit(funcionario.getFuncionario());
                novaHabilidade = new FuncionarioHabilidade();
                return;
            }
        }
        funcionario.getFuncionario().getHabilidades().add(novaHabilidade);
        funcDao.edit(funcionario.getFuncionario());
        novaHabilidade = new FuncionarioHabilidade();
    }
    
    public void removerHabilidade(){
        funcionario.getFuncionario().getHabilidades().remove(novaHabilidade);
        funcDao.edit(funcionario.getFuncionario());
        limpaHabilidade();
    }
}
