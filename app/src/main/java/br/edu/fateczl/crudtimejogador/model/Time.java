package br.edu.fateczl.crudtimejogador.model;

public class Time {
    /*
     *@author: Kelvin Santos Guimarães
     */

    private int codigo;
    private String nome, cidade;

    public Time() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "codigo: " + codigo + ", nome: " + nome + ", cidade: " + cidade;
    }
}
