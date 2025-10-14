package org.example.tarea.Factory;

import org.example.tarea.DAO.UsuarioDAO;

public abstract class DAOFactory {

    public static final int MYSQL = 1;
    public static final int POSTGRESQL = 2;
    public static final int ORACLE = 3;

    public abstract UsuarioDAO getUsuarioDAO();

    public static DAOFactory getDAOFactory(int type) {
        switch (type) {
            case MYSQL:
                return new MySQLDAOFactory();
            case POSTGRESQL:
                throw new UnsupportedOperationException("PostgreSQL no implementado aún");
            case ORACLE:
                throw new UnsupportedOperationException("Oracle no implementado aún");
            default:
                return null;
        }
    }
}