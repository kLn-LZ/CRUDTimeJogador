package br.edu.fateczl.crudtimejogador.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Time;
import br.edu.fateczl.crudtimejogador.persistence.TimeDao;

public class TimeController implements IController<Time>{
    /*
     *@author: Kelvin Santos Guimarães
     */

    private final TimeDao tDao;

    public TimeController(TimeDao tDao) {
        this.tDao = tDao;
    }

    @Override
    public void inserir(Time time) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.insert(time);
    }

    @Override
    public void modificar(Time time) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.update(time);
    }

    @Override
    public void deletar(Time time) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.delete(time);
    }

    @Override
    public Time buscar(Time time) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        return tDao.findOne(time);
    }

    @Override
    public List listar() throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        return tDao.findAll();
    }
}
