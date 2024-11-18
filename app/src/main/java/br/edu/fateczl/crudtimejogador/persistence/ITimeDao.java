package br.edu.fateczl.crudtimejogador.persistence;

import java.sql.SQLException;

public interface ITimeDao {

    public TimeDao open() throws SQLException;
    public void close() throws SQLException;
}
