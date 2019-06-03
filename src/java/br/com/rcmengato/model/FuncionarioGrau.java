/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.model;

/**
 *
 * @author ronaldo
 */
public class FuncionarioGrau implements Comparable<FuncionarioGrau>{
    
    private Funcionario func;
    private double grau;

    public FuncionarioGrau(Funcionario func, double grau) {
        this.func = func;
        this.grau = grau;
    }

    public FuncionarioGrau() {
    }
    
    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }

    public double getGrau() {
        return grau;
    }

    public void setGrau(double grau) {
        this.grau = grau;
    }

    @Override
    public String toString() {
        return "FuncionarioGrau:" + "func=" + func.getNome() + "\t | grau=" + grau;
    }

    @Override
    public int compareTo(FuncionarioGrau o) {
        if(this.grau > o.grau){
            return -1;
        }
        if(this.grau < o.grau){
            return 1;
        }
        return 0;
    }
    
    
}
