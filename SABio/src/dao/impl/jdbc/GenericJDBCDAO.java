package dao.impl.jdbc;

import dao.DAOException;
import dao.spec.IGenericDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import vo.ObjectVO;

public abstract class GenericJDBCDAO implements IGenericDAO {

    private Connection connection;

    public GenericJDBCDAO(Properties properties) throws DAOException {
        try {
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String senha = properties.getProperty("jdbc.password");
            if (senha == null) {
                senha = "";
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, senha);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e);
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    public final int selectLastID() throws DAOException {
        int lastID = 0;
        String sql = "SELECT MAX(ID) FROM " + this.getTableName();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return lastID;
    }

    public final void delete(int id) throws DAOException {
        try {
            String sql = "DELETE FROM " + this.getTableName() + " WHERE ID = ?";
            PreparedStatement stmt = this.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public final ObjectVO selectByID(int id) throws DAOException {
        ObjectVO vo = null;
        try {
            Statement stmt = this.getConnection().createStatement();
            String sql = "SELECT * FROM " + this.getTableName()
                    + " WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                vo = this.createVO(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return vo;
    }

    @Override
    public final List selectAll() throws DAOException {
        String sql = "SELECT * FROM " + this.getTableName();
        List<ObjectVO> list = new ArrayList<>();
        try {
            Statement stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(this.createVO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    protected abstract String getTableName();

    protected abstract ObjectVO createVO(ResultSet rs) throws DAOException;
}
