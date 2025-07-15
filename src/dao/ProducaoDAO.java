package dao;

import config.ConexaoMySQL;
import model.Producao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducaoDAO {
    private static Connection conn = ConexaoMySQL.getConnection();

    public ArrayList<Producao> listar() {
        try {
            ArrayList<Producao> producoes = new ArrayList<>();
            String sql = "SELECT * FROM producao;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_producao");
                Integer idProdutos = rs.getInt("id_produtos");
                Integer idFuncionario = rs.getInt("id_funcionario");
                String dataProducao = rs.getString("data_producao");
                Integer quantidade = rs.getInt("quantidade");
                Producao producao = new Producao(id, idProdutos, idFuncionario, dataProducao, quantidade);
                producoes.add(producao);
            }
            return producoes;
        } catch (SQLException e) {
            System.out.println("Erro ao listar as produções. " + e.getMessage());
            return null;
        }
    }

    public Producao buscarPorId(Integer id) {
        try {
            String sql = "SELECT * FROM producao WHERE id_producao = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer idProducao = rs.getInt("id_producao");
                Integer idProdutos = rs.getInt("id_produtos");
                Integer idFuncionario = rs.getInt("id_funcionario");
                String dataProducao = rs.getString("data_producao");
                Integer quantidade = rs.getInt("quantidade");
                Producao producao = new Producao(idProducao, idProdutos, idFuncionario, dataProducao, quantidade);
                return producao;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a produção pelo ID. " + e.getMessage());
        }
        return null;
    }

    public Boolean cadastrar(Producao producao) {
        try {
            String sql = "INSERT INTO producao (id_produtos, id_funcionario, data_producao, quantidade) VALUES(?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, producao.getIdProdutos());
            ps.setInt(2, producao.getIdFuncionario());
            ps.setString(3, producao.getDataProducao());
            ps.setInt(4, producao.getQuantidade());
            int qtdeLinha = ps.executeUpdate();
            if (qtdeLinha > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produção. " + e.getMessage());
        }
        return false;
    }

    public Boolean atualizar(Producao producao) {
        try {
            String sql = "UPDATE producao SET id_produtos = ?, id_funcionario = ?, data_producao = ?, quantidade = ? WHERE id_producao = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, producao.getIdProdutos());
            ps.setInt(2, producao.getIdFuncionario());
            ps.setString(3, producao.getDataProducao());
            ps.setInt(4, producao.getQuantidade());
            ps.setInt(5, producao.getIdProducao());
            int qtdeAtualizacoes = ps.executeUpdate();
            if (qtdeAtualizacoes > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a produção. " + e.getMessage());
        }
        return false;
    }

    public Boolean remover(Integer id) {
        try {
            String sql = "DELETE FROM producao WHERE id_producao = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            Producao producaoRetornada = buscarPorId(id);
            if (producaoRetornada != null) {
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar a produção. " + e.getMessage());
        }
        return false;
    }
}




