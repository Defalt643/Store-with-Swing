/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author ming
 */
public interface DAOInterface<T> {
    public int add(T object);
    public ArrayList<T> getAll();
    public  T get(int id);
    public void delete(int id);
    public void update(T object);
}