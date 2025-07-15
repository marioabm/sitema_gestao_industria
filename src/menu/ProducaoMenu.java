/*package menu;

public class ProducaoMenu {
}*/

package menu;

import dao.ProducaoDAO;
import model.Producao;
import java.util.Scanner;
import java.util.ArrayList;

public class ProducaoMenu {

    private static ProducaoDAO dao = new ProducaoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- MENU PRODUÇÃO ---");
            System.out.println("[1] Listar produções");
            System.out.println("[2] Buscar produção por ID");
            System.out.println("[3] Cadastrar produção");
            System.out.println("[4] Atualizar produção");
            System.out.println("[5] Remover produção");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    listarProducoes();
                    break;
                case 2:
                    buscarPorId();
                    break;
                case 3:
                    cadastrarProducao();
                    break;
                case 4:
                    atualizarProducao();
                    break;
                case 5:
                    removerProducao();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void listarProducoes() {
        ArrayList<Producao> producoes = dao.listar();
        if (producoes != null && !producoes.isEmpty()) {
            for (Producao producao : producoes) {
                System.out.println(producao);
            }
        } else {
            System.out.println("Nenhuma produção encontrada.");
        }
    }

    private static void buscarPorId() {
        System.out.print("Digite o ID da produção: ");
        int id = scanner.nextInt();
        Producao producao = dao.buscarPorId(id);
        if (producao != null) {
            System.out.println(producao);
        } else {
            System.out.println("Produção não encontrada.");
        }
    }

    private static void cadastrarProducao() {
        System.out.print("ID do produto: ");
        Integer idProduto = scanner.nextInt();
        System.out.print("ID do funcionário: ");
        Integer idFuncionario = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        System.out.print("Data da produção (YYYY-MM-DD): ");
        String dataProducao = scanner.nextLine();
        System.out.print("Quantidade: ");
        Integer quantidade = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        Producao producao = new Producao(idProduto, idFuncionario, dataProducao, quantidade);
        if (dao.cadastrar(producao)) {
            System.out.println("Produção cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar produção.");
        }
    }

    private static void atualizarProducao() {
        System.out.print("ID da produção a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        Producao producao = dao.buscarPorId(id);
        if (producao == null) {
            System.out.println("Produção não encontrada.");
            return;
        }
        System.out.print("Novo ID do produto: ");
        Integer idProduto = scanner.nextInt();
        System.out.print("Novo ID do funcionário: ");
        Integer idFuncionario = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        System.out.print("Nova data da produção (YYYY-MM-DD): ");
        String dataProducao = scanner.nextLine();
        System.out.print("Nova quantidade: ");
        Integer quantidade = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        producao.setIdProdutos(idProduto);
        producao.setIdFuncionario(idFuncionario);
        producao.setDataProducao(dataProducao);
        producao.setQuantidade(quantidade);
        if (dao.atualizar(producao)) {
            System.out.println("Produção atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar produção.");
        }
    }

    private static void removerProducao() {
        System.out.print("ID da produção a remover: ");
        int id = scanner.nextInt();
        if (dao.remover(id)) {
            System.out.println("Produção removida com sucesso!");
        } else {
            System.out.println("Erro ao remover produção.");
        }
    }
}