package org.example.tarea.Factory;

import org.example.tarea.DAO.UsuarioDAO;
import org.example.tarea.DAO.UsuarioDAOImpl;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}