package model;

import exceptions.ValidationException;

public class Transacao {

    private Usuario usuario;
    private double valor;

    public Transacao(Usuario usuario, double valor) {
        this.usuario = usuario;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void depositar() throws ValidationException {
        usuario.depositar(valor);
    }

    public void sacar() throws ValidationException {
        usuario.sacar(valor);
    }
}
