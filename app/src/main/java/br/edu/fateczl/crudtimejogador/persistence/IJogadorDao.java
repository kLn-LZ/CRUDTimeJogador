package br.edu.fateczl.crudtimejogador.persistence;

import java.sql.SQLException;

public interface IJogadorDao {

    public JogadorDao open() throws SQLException;
    public void close() throws SQLException;
}
