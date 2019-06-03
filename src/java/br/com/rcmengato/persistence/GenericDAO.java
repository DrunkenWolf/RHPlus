/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.persistence;

import java.util.List;

/**
 *
 * @author ronaldo
 * @param <T>
 */
public interface GenericDAO<T> {
    
    void save(T entity);
    T edit(T entity);
    void remove(T entity);
    List<T> findAll();
    T findById(Object id);
    
}
