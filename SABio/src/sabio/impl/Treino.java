package sabio.impl;

import java.util.List;

import vo.TreinoVO;
import sabio.SABioException;
import sabio.spec.ITreino;
import dao.DAOFactory;
import dao.spec.ITreinoDAO;

public class Treino implements ITreino {

	public void create(TreinoVO vo) throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			vo.setId(dao.selectLastID() + 1);
			dao.insert(vo);
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

	public void delete(int id) throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			dao.delete(id);
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

	public void update(TreinoVO vo) throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			dao.update(vo);
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

	public TreinoVO getTreino(int id) throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			return (TreinoVO) dao.selectByID(id);
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

	public TreinoVO getTreinoByUsuario(int id) throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			return dao.selectByUsuario(id);
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

	public List getAll() throws SABioException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			ITreinoDAO dao = factory.getAccountDAO();
			return dao.selectAll();
		} catch (Exception e) {
			throw new SABioException(e);
		}
	}

}