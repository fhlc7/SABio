package dao.impl.jdbc;

import dao.DAOException;
import dao.DAOFactory;
import dao.spec.IInstrutorDAO;
import dao.spec.IUsuarioDAO;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import vo.InstrutorVO;
import vo.ObjectVO;
import vo.UsuarioVO;

public abstract class InstrutorJDBCDAO extends GenericJDBCDAO implements IInstrutorDAO {

    public InstrutorJDBCDAO(Properties properties) throws DAOException {
        super(properties);
    }

    @Override
    public void insert(ObjectVO vo) throws DAOException {
        String sql = "INSERT INTO " + this.getTableName()
                + " (LOGIN, NOME, CPF, RG, ENDERECO, CARGA_HORARIA, NUMERO_CARTEIRA_TRABALHO, REGISTRO_PROFISSIONAL, DATA_CONTRATACAO) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            InstrutorVO instrutor = (InstrutorVO) vo;
            PreparedStatement stmt = this.getConnection().prepareStatement(sql);

            stmt.setString(1, instrutor.getUsuario().getLogin());
            stmt.setString(2, instrutor.getNome());
            stmt.setString(3, instrutor.getCPF());
            stmt.setString(4, instrutor.getRG());
            stmt.setString(5, instrutor.getEndereco());
            stmt.setInt(6, instrutor.getCargaHoraria());
            stmt.setString(7, instrutor.getNumeroCarteiraTrabalho());
            stmt.setString(8, instrutor.getRegistroProfissional());
            Date dt = new Date(instrutor.getDataContratacao().getTime().getTime());
            stmt.setDate(9, dt);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(ObjectVO vo) throws DAOException {
        String sql = "UPDATE " + this.getTableName()
                + " SET NOME=?, CPF=?, RG=?, ENDERECO=?, CARGA_HORARIA=?, NUMERO_CARTEIRA_TRABALHO=?, REGISTRO_PROFISSIONAL=?, DATA_CONTRATACAO=? WHERE LOGIN=? ";
        try {
            InstrutorVO instrutor = (InstrutorVO) vo;
            PreparedStatement stmt = this.getConnection().prepareStatement(sql);

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCPF());
            stmt.setString(3, instrutor.getRG());
            stmt.setString(4, instrutor.getEndereco());
            stmt.setInt(5, instrutor.getCargaHoraria());
            stmt.setString(6, instrutor.getNumeroCarteiraTrabalho());
            stmt.setString(7, instrutor.getRegistroProfissional());
            Date dt = new Date(instrutor.getDataContratacao().getTime().getTime());
            stmt.setDate(8, dt);
            stmt.setString(9, instrutor.getUsuario().getLogin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    @Override
    public void delete(InstrutorVO vo) throws DAOException {
        String sql = "DELETE FROM " + this.getTableName() + " WHERE LOGIN = '"
                + vo.getUsuario().getLogin() + "'";
      
        Statement stmt;
        try {
            stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InstrutorJDBCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    @Override
    public InstrutorVO SelectByLogin(String login) throws DAOException {
       ObjectVO vo = null;
        String sql = "SELECT * FROM " + this.getTableName() + " WHERE LOGIN = '"
                + login + "'";
        try {
            Statement stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                vo = this.createVO(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return (InstrutorVO) vo;
    }

    @Override
    public String getTableName() {
        return "INSTRUTOR";
    }

    @Override
    protected ObjectVO createVO(ResultSet rs) throws DAOException {
        try {
            String login = rs.getString("LOGIN");
            String nome = rs.getString("NOME");
            String cpf = rs.getString("CPF");
            String rg = rs.getString("RG");
            String endereco = rs.getString("ENDERECO");
            int carga_horaria = rs.getInt("CARGA_HORARIA");
            String numero_carteira_trabalho = rs.getString("NUMERO_CARTEIRA_TRABALHO");
            String registro_profissional = rs.getString("REGISTRO_PROFISSIONAL");
            Date dt = rs.getDate("DATA_CONTRATACAO");

            IUsuarioDAO userDAO = DAOFactory.getInstance().getUsuarioDAO();
            UsuarioVO user = (UsuarioVO) userDAO.selectByLogin(login);

            Calendar cal = new GregorianCalendar();
            cal.setTime(dt);

            return new InstrutorVO(user, nome, cpf, cal, rg, endereco, registro_profissional, numero_carteira_trabalho, carga_horaria);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}