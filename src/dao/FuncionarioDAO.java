package dao;

import config.ConexaoMySQL;
import model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

    public class FuncionarioDAO {
        private static Connection conn = ConexaoMySQL.getConnection();

        public ArrayList<Funcionario> listar() {
            try {
                ArrayList<Funcionario> funcionarios = new ArrayList<>();
                String sql = "SELECT * FROM funcionario;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Integer id = rs.getInt("id_funcionario");
                    String nome = rs.getString("nome");
                    String sobrenome = rs.getString("sobrenome");
                    Integer idSetor = rs.getInt("id_setor");
                    Funcionario funcionario = new Funcionario(id, nome, sobrenome, idSetor);
                    funcionarios.add(funcionario);
                }
                return funcionarios;
            } catch (SQLException e) {
                System.out.println("Erro ao listar os funcionários. " + e.getMessage());
                return null;
            }
        }

        public Funcionario buscarPorId(Integer id) {
            try {
                String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Integer idFuncionario = rs.getInt("id_funcionario");
                    String nome = rs.getString("nome");
                    String sobrenome = rs.getString("sobrenome");
                    Integer idSetor = rs.getInt("id_setor");
                    Funcionario funcionario = new Funcionario(idFuncionario, nome, sobrenome, idSetor);
                    return funcionario;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar o funcionário pelo ID. " + e.getMessage());
            }
            return null;
        }

        public Boolean cadastrar(Funcionario funcionario) {
            try {
                String sql = "INSERT INTO funcionario (nome, sobrenome, id_setor) VALUES(?,?,?);";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, funcionario.getNome());
                ps.setString(2, funcionario.getSobrenome());
                ps.setInt(3, funcionario.getIdSetor());
                int qtdeLinha = ps.executeUpdate();
                if (qtdeLinha > 0) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar funcionário. " + e.getMessage());
            }
            return false;
        }

        public Boolean atualizar(Funcionario funcionario) {
            try {
                String sql = "UPDATE funcionario SET nome = ?, sobrenome = ?, id_setor = ? WHERE id_funcionario = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, funcionario.getNome());
                ps.setString(2, funcionario.getSobrenome());
                ps.setInt(3, funcionario.getIdSetor());
                ps.setInt(4, funcionario.getIdFuncionario());
                int qtdeAtualizacoes = ps.executeUpdate();
                if (qtdeAtualizacoes > 0) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar o funcionário. " + e.getMessage());
            }
            return false;
        }

        public Boolean remover(Integer id) {
            try {
                String sql = "DELETE FROM funcionario WHERE id_funcionario = ?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                Funcionario funcionarioRetornado = buscarPorId(id);
                if (funcionarioRetornado != null) {
                    ps.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao deletar o funcionário. " + e.getMessage());
            }
            return false;
        }
    }



