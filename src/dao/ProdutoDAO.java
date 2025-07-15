package dao;

import config.ConexaoMySQL;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {


    private static Connection conn = ConexaoMySQL.getConnection();

    // 4 metodos CRUD

    public ArrayList<Produto> listar() {


        try {
            ArrayList<Produto> produtos = new ArrayList<>();
            String sql = "SELECT * FROM produtos;";
            // Connection conn;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id_produtos");
                String nome = rs.getString("nome_produto");
                String descricao = rs.getString("descricao");

                Produto produto = new Produto(id, nome, descricao);

                produtos.add(produto);
            }
            return produtos;

            //produtos.add(new model.Setor(id,nome,descricao));



        } catch(SQLException e)    {
            System.out.println("Deu ruim em listar os clientes. " + e.getMessage());

            return null;

        }
    }



    public Produto buscarPorId(Integer id) {
        try {
            String sql = "SELECT * FROM produtos WHERE id_produtos = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Integer idProduto = rs.getInt("id_produtos");
                String nome = rs.getString("nome_produto");
                String descricao = rs.getString("descricao");

                Produto produto = new Produto(idProduto, nome, descricao);
                return produto;


                // model.Setor setor = new model.Setor(idSetor, nome, descricao);
                // return setor;

            }


        } catch (SQLException e) {
            System.out.println("Erro ao buscar o produto pelo ID. "  + e.getMessage());

        }


        return null;
    }

    public Boolean cadastrar(Produto produto) {
        try {

            String sql = "INSERT INTO produtos (nome_produto, descricao) VALUES(?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            int qtdeLinha = ps.executeUpdate();

            if (qtdeLinha > 0){
                return true;
            }

        }catch (SQLException e){
            System.out.println("Erro ao cadastrar produto. " + e.getMessage());

        }

        return false;
    }

    public Boolean atualizar(Produto produto) {

        try {
            String sql = "UPDATE produto SET nome_produto = ?, descricao = ? WHERE id_produto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());

            int qtdeAtualizacoes = ps.executeUpdate();

            if (qtdeAtualizacoes > 0) {
                return true;
            }



        }catch (SQLException e){
            System.out.println("Erro ao atualizar o produto. " + e.getMessage());

        }

        return false;
    }

    public Boolean remover(Integer id) {
        try {
            String sql = "DELETE FROM produto WHERE id_Produto = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            Produto produtoRetornado = buscarPorId(id);

            if (produtoRetornado != null)  {
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e){
            System.out.println("Erro ao deletar o produto. " + e.getMessage());

        }

        return false;
    }


}
