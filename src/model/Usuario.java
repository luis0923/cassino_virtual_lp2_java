package model;

import abstracts.Entidade;
import exceptions.ValidationException;

public class Usuario extends Entidade {

    private String nome;
    private double saldo;

    public Usuario(int id, String nome, double saldo) {
        super(id);
        this.nome = nome;
        this.saldo = saldo;
    }

    public Usuario(String nome, double saldo) {
        super(0);
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Nome: " + nome + " | Saldo: " + saldo;
    }

    public String paraTexto() {
        return getId() + ";" + nome + ";" + saldo;
    }

    public static Usuario deTexto(String linha) {
        String[] partes = linha.split(";");

        int id = Integer.parseInt(partes[0]);
        String nome = partes[1];
        double saldo = Double.parseDouble(partes[2]);

        return new Usuario(id, nome, saldo);
    }

    public void depositar(double valor) throws ValidationException {
        if (valor <= 0) {
            throw new ValidationException("O valor do depósito deve ser maior que zero.");
        }

        saldo += valor;
    }

    public void sacar(double valor) throws ValidationException {
        if (valor <= 0) {
            throw new ValidationException("O valor do saque deve ser maior que zero.");
        }

        if (valor > saldo) {
            throw new ValidationException("Saldo insuficiente. Saldo atual: R$ " + saldo);
        }

        saldo -= valor;
    }
}
