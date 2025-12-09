package model;

import java.util.Random;

public class Cassino {

    private String nome;
    private final Random random = new Random();

    public Cassino(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String jogarDados(Usuario usuario, double valorAposta, int numeroEscolhido) {
        if (numeroEscolhido < 1 || numeroEscolhido > 6) {
            return "Erro: o número deve estar entre 1 e 6.";
        }

        if (usuario.getSaldo() < valorAposta) {
            return "Saldo insuficiente para essa aposta.";
        }

        int resultado = random.nextInt(6) + 1;

        if (resultado == numeroEscolhido) {
            double premio = valorAposta * 3;
            usuario.setSaldo(usuario.getSaldo() + premio);
            return "Vitória! O dado caiu em " + resultado + ". Você ganhou R$" + premio;
        } else {
            usuario.setSaldo(usuario.getSaldo() - valorAposta);
            return "Derrota! O dado caiu em " + resultado + ". Você perdeu R$" + valorAposta;
        }
    }
}
