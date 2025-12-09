package model;

import abstracts.Entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aposta extends Entidade {

    private String nomeUsuario;
    private double valor;
    private String jogo;
    private String resultado;
    private LocalDateTime dataHora;

    public Aposta(int id, String nomeUsuario, double valor, String jogo, String resultado, LocalDateTime dataHora) {
        super(id);
        this.nomeUsuario = nomeUsuario;
        this.valor = valor;
        this.jogo = jogo;
        this.resultado = resultado;
        this.dataHora = dataHora;
    }

    public Aposta(String nomeUsuario, double valor, String jogo, String resultado) {
        super(0);
        this.nomeUsuario = nomeUsuario;
        this.valor = valor;
        this.jogo = jogo;
        this.resultado = resultado;
        this.dataHora = LocalDateTime.now();
    }

    public String paraTexto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return getId() + ";" +
                nomeUsuario + ";" +
                valor + ";" +
                jogo + ";" +
                resultado + ";" +
                dataHora.format(formatter);
    }

    public static Aposta deTexto(String linha) {
        String[] dados = linha.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        int id = Integer.parseInt(dados[0]);
        String nomeUsuario = dados[1];
        double valor = Double.parseDouble(dados[2]);
        String jogo = dados[3];
        String resultado = dados[4];
        LocalDateTime dataHora = LocalDateTime.parse(dados[5], formatter);

        return new Aposta(id, nomeUsuario, valor, jogo, resultado, dataHora);
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        return "[ID " + getId() + "] " +
                dataHora.format(formatter) +
                " - " + jogo +
                ": R$" + valor +
                " (" + resultado + ")";
    }
}
