package model;

import abstracts.Entidade;

public class Jogo extends Entidade
{

    private String nome;

    public Jogo(int id, String nome)
    {
        super(id);
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }



    @Override
    public String toString()
    {
        return "[ID " + getId() + "] " + nome;
    }
}
