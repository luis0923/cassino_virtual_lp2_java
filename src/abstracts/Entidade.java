package abstracts;

import interfaces.Identificavel;

public abstract class Entidade implements Identificavel {

    protected int id;

    public Entidade(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public abstract String toString();
}
