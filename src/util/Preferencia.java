package util;

import java.io.*;

public class Preferencia {

    private static final String ARQUIVO = "prefs.txt";

    public static void salvarNome(String nome) {
        try (FileWriter escritor = new FileWriter(ARQUIVO)) {
            escritor.write(nome);
        } catch (IOException e) {

        }
    }

    public static String carregarNome() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            return leitor.readLine();
        } catch (IOException e) {
            return "Jogador";
        }
    }
}
