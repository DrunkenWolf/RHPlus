package br.com.rcmengato.persistence;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Wellington
 */
public class QueryParameter implements Serializable{
private String field;
private Object value;

    public QueryParameter() {
    }

    public QueryParameter(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public QueryParameter(String field) {
        this.field = field;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new QueryParameter(field, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryParameter other = (QueryParameter) obj;
        if ((this.field == null) ? (other.field != null) : !this.field.equals(other.field)) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.field != null ? this.field.hashCode() : 0);
        hash = 59 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s=%s", field, value);
    }



    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
    
    public static QueryParameter create(String field, Object value){
        return new QueryParameter(field,value);
    }
    
    public boolean estaVazio(){
        if(field == null){
            return true;
        }
        return false;
    }
}
