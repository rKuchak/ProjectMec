/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.EnderecoModel;
import com.app.model.PessoaFisicaModel;
import com.app.model.TelefoneModel;
import com.app.util.Criptografia;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macuser
 */
public class PessoaFisicaDAO {

    private static PessoaFisicaDAO pessoaFisicaDAO = null;

    public static final PessoaFisicaDAO getInstance() {
        if (pessoaFisicaDAO == null) {
            pessoaFisicaDAO = new PessoaFisicaDAO();
        }
        return pessoaFisicaDAO;
    }

    public boolean isExisteEmail(Connection conn, String email) throws SQLException {
        boolean isExiste = false;
        String query = "select * from pessoa p where p.email = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, email);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            isExiste = true;
        }
        rs.close();
        prep.close();

        return isExiste;
    }

    public boolean isExisteEmailOutroUsuario(Connection conn, String email, int idPessoa) throws SQLException {
        boolean isExiste = false;
        String query = "select * from pessoa p where p.email = ? and p.id != ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, email);
        prep.setInt(2, idPessoa);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            isExiste = true;
        }
        rs.close();
        prep.close();

        return isExiste;
    }

    public int savePessoa(Connection conn, PessoaFisicaModel pessoaFisicaModel) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Criptografia criptografia = new Criptografia();
        int idPessoa = 0;
        String query = "INSERT into pessoa (nome, data_nascimento, sexo, login, senha, email, my_number, data_cadastro) values (?,?,?,?,?,?,?, now())";
        PreparedStatement prep = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        prep.setString(1, pessoaFisicaModel.getNome());
        prep.setString(2, pessoaFisicaModel.getDataNascimento());
        prep.setString(3, pessoaFisicaModel.getSexo());
        prep.setString(4, pessoaFisicaModel.getLogin());
        prep.setString(5, criptografia.senhaCriptografada(pessoaFisicaModel.getSenha()));
        prep.setString(6, pessoaFisicaModel.getEmail());
        prep.setString(7, pessoaFisicaModel.getMyNumber());
        prep.execute();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            idPessoa = rs.getInt(1);
        }
        rs.close();
        prep.close();

        return idPessoa;
    }

    public void saveAddress(Connection conn, int idPessoa, EnderecoModel endereco) throws SQLException {
        String query = "INSERT into endereco (id_pessoa, provincia, cidade, ds_endereco, bairro, cep, status) VALUES (?,?,?,?,?,?,1)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idPessoa);
        prep.setString(2, endereco.getProvincia());
        prep.setString(3, endereco.getCidade());
        prep.setString(4, endereco.getDsEndereco());
        prep.setString(5, endereco.getBairro());
        prep.setString(6, endereco.getCep());
        prep.execute();
        prep.close();
    }

    public void savePhone(Connection conn, int idPessoa, TelefoneModel telefone) throws SQLException {
        String query = "INSERT into telefone (id_pessoa, numero, tipo_telefone) VALUES (?,?,?)";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idPessoa);
        prep.setString(2, telefone.getNrTelefone());
        prep.setString(3, telefone.getTipoTelefone());
        prep.execute();
        prep.close();
    }

    public List<PessoaFisicaModel> searchAll(Connection conn, PessoaFisicaModel pessoaFisicaModel) throws SQLException {
        List<PessoaFisicaModel> listaPessoa = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from pessoa p");
        sb.append(" where 1 = 1");
        if (pessoaFisicaModel.getNome() != null && !pessoaFisicaModel.getNome().equals("")) {
            sb.append(" and p.nome like '%" + pessoaFisicaModel.getNome() + "%'");
        }
        if (pessoaFisicaModel.getEmail() != null && !pessoaFisicaModel.getEmail().equals("")) {
            sb.append(" and p.email = '" + pessoaFisicaModel.getEmail() + "'");
        }
        if (pessoaFisicaModel.getSexo() != null && !pessoaFisicaModel.getSexo().equals("")) {
            sb.append(" and p.sexo = '" + pessoaFisicaModel.getSexo() + "'");
        }

        System.out.println(sb.toString());
        PreparedStatement prep = conn.prepareStatement(sb.toString());
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            PessoaFisicaModel pessoa = new PessoaFisicaModel();
            pessoa.setId(rs.getInt("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setSexo(rs.getString("sexo"));
            pessoa.setDataNascimento(rs.getString("data_nascimento"));
            pessoa.setMyNumber(rs.getString("my_number"));

            listaPessoa.add(pessoa);
        }
        rs.close();
        prep.close();

        return listaPessoa;

    }

    public PessoaFisicaModel obterDadosPessoaPorId(Connection conn, String idPessoa) throws SQLException {
        String query = "select p.id, p.nome, p.data_nascimento, p.sexo, p.email, p.my_number, p.data_cadastro, "
                + " ec.provincia, ec.cidade, ec.ds_endereco, ec.bairro, ec.cep, ec.status, "
                + " t.numero, t.tipo_telefone"
                + " from pessoa p, endereco ec, telefone t"
                + " where p.id = ec.id_pessoa"
                + " and p.id = t.id_pessoa"
                + " and p.id = ?"
                + " and ec.status = 1";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, idPessoa);
        ResultSet rs = prep.executeQuery();
        PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
        if (rs.next()) {
            pessoaFisicaModel.setId(rs.getInt("id"));
            pessoaFisicaModel.setNome(rs.getString("nome"));
            pessoaFisicaModel.setDataNascimento(rs.getString("data_nascimento"));
            pessoaFisicaModel.setSexo(rs.getString("sexo"));
            pessoaFisicaModel.setEmail(rs.getString("email"));
            pessoaFisicaModel.setMyNumber(rs.getString("my_number"));
            pessoaFisicaModel.setDataCadastro(rs.getString("data_cadastro"));

            EnderecoModel endereco = new EnderecoModel();
            endereco.setProvincia(rs.getString("provincia"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setCep(rs.getString("cep"));
            endereco.setDsEndereco(rs.getString("ds_endereco"));
            endereco.setBairro(rs.getString("bairro"));
            endereco.setStatus(rs.getInt("status"));
            pessoaFisicaModel.setEndereco(endereco);

            TelefoneModel telefone = new TelefoneModel();
            telefone.setNrTelefone(rs.getString("numero"));
            telefone.setTipoTelefone(rs.getString("tipo_telefone"));
            pessoaFisicaModel.setTelefone(telefone);
        }
        rs.close();
        prep.close();

        return pessoaFisicaModel;
    }

    public void updatePessoa(Connection conn, PessoaFisicaModel pessoaFisicaModel) throws SQLException {
        String query = "UPDATE pessoa SET nome=?, data_nascimento=?, sexo=?, email=?, my_number=? WHERE id=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, pessoaFisicaModel.getNome());
            prep.setString(2, pessoaFisicaModel.getDataNascimento());
            prep.setString(3, pessoaFisicaModel.getSexo());
            prep.setString(4, pessoaFisicaModel.getEmail());
            prep.setString(5, pessoaFisicaModel.getMyNumber());
            prep.setInt(6, pessoaFisicaModel.getId());
            prep.execute();
        }
    }

    public void updateAddress(Connection conn, int idPessoa, EnderecoModel endereco) throws SQLException {
        String query = "UPDATE endereco SET provincia=?, cidade=?, ds_endereco=?, cep=?, bairro=? WHERE id_pessoa=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, endereco.getProvincia());
            prep.setString(2, endereco.getCidade());
            prep.setString(3, endereco.getDsEndereco());
            prep.setString(4, endereco.getCep());
            prep.setString(5, endereco.getBairro());
            prep.setInt(6, idPessoa);
            prep.execute();
        }
    }

    public void updatePhone(Connection conn, int idPessoa, TelefoneModel telefone) throws SQLException {
        String query = "UPDATE telefone SET numero=?, tipo_telefone=? WHERE id_pessoa=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, telefone.getNrTelefone());
            prep.setString(2, telefone.getTipoTelefone());
            prep.setInt(3, idPessoa);
            prep.execute();
        }
    }

    public void deletePessoa(Connection conn, String idPessoa) throws SQLException {
        String query = "DELETE FROM pessoa WHERE id=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, idPessoa);
            prep.execute();
        }
    }

    public void deletePhone(Connection conn, String idPessoa) throws SQLException {
        String query = "DELETE FROM telefone WHERE id_pessoa=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, idPessoa);
            prep.execute();
        }
    }

    public void deleteAddress(Connection conn, String idPessoa) throws SQLException {
        String query = "DELETE FROM endereco WHERE id_pessoa=?";
        try ( PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, idPessoa);
            prep.execute();
        }
    }

    public String obterNomePessoaPorId(Connection conn, int idPessoa) throws SQLException {
        String query = "select nome from pessoa where id = ?";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, idPessoa);
        ResultSet rs = prep.executeQuery();
        String nomeUsuario = null;
        if (rs.next()) {
            nomeUsuario = rs.getString("nome");
        }
        rs.close();
        prep.close();

        return nomeUsuario;
    }

    public boolean isExisteVinculo(Connection conn, String idPessoa) throws SQLException {
        String query = "select id_pessoa from venda_veiculo v where v.id_pessoa = ?"
                + " union"
                + " select id_pessoa from shaken s where s.id_pessoa = ?";
        
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, idPessoa);
        prep.setString(2, idPessoa);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            return true;
        }
        rs.close();
        prep.close();
        
        return false;
    }

}
