package file;

import dao.SetorDAO;
import model.Setor;

import java.io.*;
import java.util.ArrayList;

public class EscritaArquivo {
    public static void main(String[] args) {
        String caminho = "src/file/novo_arquivo.csv";
        File arquivo = new File(caminho);
        SetorDAO setorDAO = new SetorDAO();
        ArrayList<Setor> setores = setorDAO.listar();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {

            for (Setor setor : setores) {
                bw.write(setor.toString());
                bw.newLine();
            }

        }catch (IOException e){
            System.out.println("Erro ao escrever o arquivo.");

        }
    }
}
