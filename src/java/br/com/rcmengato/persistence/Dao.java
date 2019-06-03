/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rcmengato.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ronaldo
 * @version 1.2
 * @param <T>
 */
public class Dao<T> implements GenericDAO<T> {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final EntityTransaction tx;
    private final String unitName = "rhplusPU";

    private final Class<T> entityClass;

    public Dao(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.emf = Persistence.createEntityManagerFactory(unitName);
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @Override
    public void save(T entity) {
        beginTransaction();
        em.persist(entity);
        tx.commit();
    }

    /**
     * Salva ou edita um objeto.
     * @param entity
     * @return 
     */
    @Override
    public T edit(T entity) {
        beginTransaction();
        T editado = em.merge(entity);
        tx.commit();
        return editado;
    }

    @Override
    public void remove(T entity) {
        beginTransaction();
        em.remove(entity);
        tx.commit();
    }

    @Override
    public List<T> findAll() {
        beginTransaction();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        List<T> lista = em.createQuery(cq).getResultList();
        tx.commit();
        return lista;
    }

    @Override
    public T findById(Object id) {
        beginTransaction();
        T busca = em.find(entityClass, id);
        tx.commit();
        return busca;
    }

    public void beginTransaction() {
        if (!tx.isActive()) {
            tx.begin();
        }
    }

    public T executeSingleResult(String queryName, QueryParameter... values) {
        Query nq = em.createNamedQuery(queryName);
        if (values != null) {
            for (QueryParameter value : values) {
                nq.setParameter(value.getField(), value.getValue());
            }
        }
        try {
            return (T) nq.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<T> executeResultList(String queryName, QueryParameter... values) {
        Query nq = em.createNamedQuery(queryName);
        for (QueryParameter value : values) {
            if (!value.estaVazio()) {
                nq.setParameter(value.getField(), value.getValue());
            }
        }
        try {
            return nq.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
