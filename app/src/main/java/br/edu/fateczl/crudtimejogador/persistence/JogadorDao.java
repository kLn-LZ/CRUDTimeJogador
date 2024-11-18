package br.edu.fateczl.crudtimejogador.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Jogador;
import br.edu.fateczl.crudtimejogador.model.Time;

public class JogadorDao implements IJogadorDao, ICRUDDao<Jogador> {
    /*
     *@author: Kelvin Santos Guimarães
     */

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JogadorDao(Context context) {
        this.context = context;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gDao.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        database.insert("jogador", null, contentValues);
    }
    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        int res = database.update("jogador", contentValues, "Id = " +
                jogador.getId(), null);

        return res;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        database.delete("jogador", "Id = " + jogador.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String sql = "SELECT " +
                     "   j.id, " +
                     "   j.nome, " +
                     "   j.data_nasc, " +
                     "   j.altura, " +
                     "   j.peso, " +
                     "   j.TimeCodigo, " +
                     "   t.Codigo as cod_time, " +
                     "   t.nome as nome_time, " +
                     "   t.cidade as cidade_time " +
                     "FROM jogador j, time t " +
                     "WHERE j.TimeCodigo = t.Codigo " +
                     "      AND id = " + jogador.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("cod_time")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome_time")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade_time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNascimento(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nasc"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();

        String sql = "SELECT " +
                "   j.id, " +
                "   j.nome, " +
                "   j.data_nasc, " +
                "   j.altura, " +
                "   j.peso, " +
                "   j.TimeCodigo, " +
                "   t.Codigo as cod_time, " +
                "   t.nome as nome_time, " +
                "   t.cidade as cidade_time " +
                "FROM jogador j, time t " +
                "WHERE j.TimeCodigo = t.Codigo ";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Jogador jogador = new Jogador();
            Time time = new Time();

            time.setCodigo(cursor.getInt(cursor.getColumnIndex("cod_time")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome_time")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade_time")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNascimento(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nasc"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    private ContentValues getContentValues(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("data_nasc", jogador.getDataNascimento().toString());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("peso", jogador.getTime().getCodigo());

        return contentValues;
    }
}
