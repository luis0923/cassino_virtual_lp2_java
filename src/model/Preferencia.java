package model;

public class Preferencia {

    private int idUsuario;
    private String tema;
    private static final String ARQUIVO = "prefs.txt";

    public Preferencia(int idUsuario) {
        this.idUsuario = idUsuario;
        this.tema = "Claro";
        carregarPreferencia();
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
        salvarPreferencia();
    }

    public void alternarTema() {
        if ("Claro".equalsIgnoreCase(tema)) {
            setTema("Escuro");
        } else {
            setTema("Claro");
        }
    }

    private void salvarPreferencia()
    {

    }

    private void carregarPreferencia()
    {

    }
}
