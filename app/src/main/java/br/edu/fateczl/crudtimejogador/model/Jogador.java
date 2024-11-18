package br.edu.fateczl.crudtimejogador.model;

import java.time.LocalDate;


public class Jogador {
    /*
     *@author: Kelvin Santos Guimarães
     */
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private float altura, peso;
    private Time time;

    public Jogador() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Data de Nascimento: " + dataNascimento + ", altura: " + altura + ", peso: " + peso + ", time: " + time.getNome();
    }
}
