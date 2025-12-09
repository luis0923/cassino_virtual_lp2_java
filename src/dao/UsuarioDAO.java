package dao;

import abstracts.AbstractDAO;
import model.Usuario;

import java.io.*;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    private static final String ARQUIVO = "usuarios.txt";

    public UsuarioDAO() {
        super();
        carregar();
    }

    public Usuario buscarPorNome(String nome) {
        for (Usuario usuario : registros) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void create(Usuario usuario) throws Exception {
        if (usuario.getId() == 0) {
            int novoId = registros.isEmpty()
                    ? 1
                    : registros.get(registros.size() - 1).getId() + 1;
            usuario.setId(novoId);
        }

        registros.add(usuario);
        salvar();
    }

    @Override
    public void update(Usuario usuario) {
        salvar();
    }

    @Override
    public void delete(Usuario usuario) throws Exception {
        boolean removido = registros.removeIf(u -> u.getId() == usuario.getId());

        if (removido) {
            salvar();
        } else {
            throw new Exception("Usuário não encontrado para remoção.");
        }
    }

    private void salvar() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario usuario : registros) {
                escritor.write(usuario.paraTexto());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
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
                    registros.add(Usuario.deTexto(linha));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}
