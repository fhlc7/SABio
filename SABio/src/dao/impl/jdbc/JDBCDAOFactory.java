package dao.impl.jdbc;

import java.util.Properties;

import dao.DAOException;
import dao.DAOFactory;
import dao.spec.ITreinoDAO;
import dao.spec.IUsuarioDAO;

public class JDBCDAOFactory extends DAOFactory {

	private Properties properties;

	public JDBCDAOFactory(Properties properties) {
		this.properties = properties;
	}

	public IUsuarioDAO getUserDAO() throws DAOException {
		return new UsuarioJDBCDAO(this.properties);
	}

	public ITreinoDAO getAccountDAO() throws DAOException {
		return new TreinoJDBCDAO(this.properties);
	}
}