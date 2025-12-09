package dao;

import abstracts.AbstractDAO;
import model.Aposta;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class ApostaDAO extends AbstractDAO<Aposta> {

    private static final String ARQUIVO = "apostas.txt";

    public ApostaDAO() {
        super();
        carregar();
    }

    @Override
    public void create(Aposta aposta) {
        registros.add(aposta);
        salvar();
    }

    @Override
    public void update(Aposta aposta) {
        salvar();
    }

    @Override
    public void delete(Aposta aposta) {
        registros.remove(aposta);
        salvar();
    }

    public List<Aposta> getHistoricoDoUsuario(String nomeUsuario) {
        return registros.stream()
                .filter(a -> a.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
                .collect(Collectors.toList());
    }

    private void salvar() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Aposta aposta : registros) {
                escritor.write(aposta.paraTexto());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar apostas: " + e.getMessage());
        }
    }

    private void carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    registros.add(Aposta.deTexto(linha));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar apostas: " + e.getMessage());
        }
    }
}
