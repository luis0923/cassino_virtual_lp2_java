package Cassino;

import dao.ApostaDAO;
import dao.UsuarioDAO;
import exceptions.ValidationException;
import model.Aposta;
import model.Cassino;
import model.Preferencia;
import model.Usuario;
import model.Transacao;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ApostaDAO apostaDAO = new ApostaDAO();

        Usuario usuario = null;

        System.out.println("=== SISTEMA DE CASSINO ===");

        while (usuario == null) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Digite seu usuario para acessar: ");
            String login = sc.nextLine().trim();

            if (login.isEmpty()) {
                continue;
            }

            if (login.equalsIgnoreCase("admin")) {
                System.out.print("Senha: ");
                String senha = sc.nextLine();

                if (senha.equals("admin123")) {
                    menuAdmin(usuarioDAO, sc);
                } else {
                    System.out.println("Senha incorreta.");
                }
                continue;
            }

            Usuario encontrado = usuarioDAO.buscarPorNome(login);

            if (encontrado != null) {
                usuario = encontrado;
                System.out.println("Bem-vindo ao cassino, " + usuario.getNome() + "!");
            } else {
                System.out.println("Usuário não encontrado. Criando nova conta...");
                usuario = new Usuario(login, 0);

                try {
                    usuarioDAO.create(usuario);
                    System.out.println("Conta criada com sucesso! ID: " + usuario.getId());
                } catch (Exception e) {
                    System.out.println("Erro ao criar usuário.");
                    usuario = null;
                }
            }
        }

        Preferencia preferencias = new Preferencia(usuario.getId());
        System.out.println("Tema atual: " + preferencias.getTema());

        Cassino cassino = new Cassino("Cassino UFRN");
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== " + cassino.getNome().toUpperCase() + " ===");
            System.out.println("Usuário: " + usuario.getNome() + " | Saldo: R$ " + usuario.getSaldo() + " | Tema: " + preferencias.getTema());
            System.out.println("1 - Jogar Iguana sortuda 1000");
            System.out.println("2 - Ver Saldo");
            System.out.println("3 - Ver Histórico");
            System.out.println("4 - Alterar Tema");
            System.out.println("5 - Transações");
            System.out.println("6 - Sair");
            System.out.print("Opção: ");

            if (!sc.hasNextInt()) {
                sc.next();
                continue;
            }

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Valor da aposta: R$ ");
                    double valor = sc.nextDouble();

                    System.out.print("Escolha um número (1 a 6): ");
                    int numero = sc.nextInt();
                    sc.nextLine();

                    if (valor <= 0) {
                        System.out.println("Aviso: A aposta deve ser maior que zero.");
                        break;
                    }


                    if (valor > usuario.getSaldo()) {
                        System.out.println("Aviso: Saldo insuficiente.");

                        Aposta aposta = new Aposta(
                                usuario.getNome(),
                                valor,
                                "Dados",
                                "Saldo Insuficiente"
                        );

                        try {
                            apostaDAO.create(aposta);
                        } catch (Exception e) {
                            System.out.println("Erro ao salvar aposta inválida.");
                        }

                        break;
                    }

                    try {
                        String resultado = cassino.jogarDados(usuario, valor, numero);

                        System.out.println("\n" + resultado);

                        String status = resultado.contains("Vitória") ? "Ganhou" : "Perdeu";

                        Aposta aposta = new Aposta(
                                usuario.getNome(),
                                valor,
                                "Dados",
                                status
                        );

                        apostaDAO.create(aposta);
                        usuarioDAO.update(usuario);

                    } catch (Exception e) {
                        System.out.println("Erro ao registrar aposta.");
                    }

                    break;
                case 2:
                    System.out.println("\nSaldo atual: R$ " + usuario.getSaldo());
                    break;

                case 3:
                    System.out.println("\n--- HISTÓRICO DE APOSTAS ---");
                    List<Aposta> historico = apostaDAO.getHistoricoDoUsuario(usuario.getNome());

                    if (historico.isEmpty()) {
                        System.out.println("Nenhuma aposta registrada.");
                    } else {
                        for (Aposta a : historico) {
                            System.out.println(a);
                        }
                    }
                    break;

                case 4:
                    preferencias.alternarTema();
                    System.out.println("Tema alterado para: " + preferencias.getTema());
                    break;

                case 5:
                    menuTransacoes(usuario, usuarioDAO, sc);
                    break;

                case 6:
                    System.out.println("Encerrando sistema...");
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }

    public static void menuTransacoes(Usuario usuario, UsuarioDAO usuarioDAO, Scanner sc) {
        boolean ativo = true;

        while (ativo) {
            System.out.println("\n--- TRANSAÇÕES ---");
            System.out.println("Saldo atual: R$ " + usuario.getSaldo());
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");

            if (!sc.hasNextInt()) {
                sc.next();
                continue;
            }

            int op = sc.nextInt();
            sc.nextLine();

            if (op == 3) {
                ativo = false;
                continue;
            }

            if (op != 1 && op != 2) {
                System.out.println("Opção inválida.");
                continue;
            }

            System.out.print("Valor: R$ ");
            if (!sc.hasNextDouble()) {
                sc.next();
                sc.nextLine();
                System.out.println("Valor inválido.");
                continue;
            }

            double valor = sc.nextDouble();
            sc.nextLine();

            Transacao transacao = new Transacao(usuario, valor);

            try {
                if (op == 1) {
                    transacao.depositar();
                    System.out.println("Depósito realizado com sucesso.");
                } else {
                    transacao.sacar();
                    System.out.println("Saque realizado com sucesso.");
                }

                usuarioDAO.update(usuario);
                System.out.println("Novo saldo: R$ " + usuario.getSaldo());

            } catch (ValidationException e) {
                System.out.println("Aviso: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro ao salvar transação.");
            }
        }
    }


    public static void menuAdmin(UsuarioDAO dao, Scanner sc) {
        boolean ativo = true;

        while (ativo) {
            System.out.println("\n--- ADMIN ---");
            System.out.println("1 - Listar usuários");
            System.out.println("2 - Remover usuário");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");

            String op = sc.nextLine();

            switch (op) {
                case "1":
                    List<Usuario> usuarios = dao.read();

                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        for (Usuario u : usuarios) {
                            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome() + " | Saldo: " + u.getSaldo());
                        }
                    }
                    break;

                case "2":
                    System.out.print("ID do usuário: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        Usuario u = new Usuario(id, "", 0);
                        dao.delete(u);
                        System.out.println("Usuário removido.");
                    } catch (Exception e) {
                        System.out.println("Erro ao remover usuário.");
                    }
                    break;

                case "3":
                    ativo = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
