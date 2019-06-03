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
public class Ranking implements Comparable<Ranking>{
    
    private Cargo cargo;
    private Funcionario funcionario;
    private float compatibilidade;
    private int exp;

    public Ranking(Cargo cargo, Funcionario funcionario, float compatibilidade) {
        this.cargo = cargo;
        this.funcionario = funcionario;
        this.compatibilidade = compatibilidade;
    }

    public Ranking(Cargo cargo, Funcionario funcionario, float compatibilidade, int exp) {
        this.cargo = cargo;
        this.funcionario = funcionario;
        this.compatibilidade = compatibilidade;
        this.exp = exp;
    }

    public Ranking(){
        
    }
    
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public float getCompatibilidade() {
        return compatibilidade;
    }

    public void setCompatibilidade(float compatibilidade) {
        this.compatibilidade = compatibilidade;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public int compareTo(Ranking o) {
        if(this.compatibilidade > o.compatibilidade){
            return -1;
        }
        if(this.compatibilidade < o.compatibilidade){
            return 1;
        }
        return 0;
    }
 
}
