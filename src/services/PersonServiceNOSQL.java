package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.PersonDTO;
import persistance.dao.PersonNoSqlDAO;
import persistance.excpetion.DaoException;

public class PersonServiceNOSQL implements IPersonService {

	private PersonNoSqlDAO noSQLDAO = new PersonNoSqlDAO();

	@Override
	public List<PersonDTO> list() throws ClassNotFoundException, DaoException, SQLException, IOException {
		List<PersonDTO> listToReturn = new ArrayList<>();

		for (Map<String, Object> mapPerson : noSQLDAO.list()) {
			listToReturn.add(new PersonDTO(mapPerson));
		}
		return listToReturn;
	}

	@Override
	public void save(PersonDTO person) throws Exception {
		Map<String, Object> mapPerson = person.getMapPerson();
		noSQLDAO.create(mapPerson);

	}

	@Override
	public PersonDTO getById(String idS) throws Exception {
		Map<String, Object> mapPerson = noSQLDAO.getById(idS);
		return new PersonDTO(mapPerson);
	}

	@Override
	public void update(PersonDTO person) throws Exception {
		Map<String, Object> mapPerson = person.getMapPerson();
		noSQLDAO.updateById(mapPerson);

	}

	@Override
	public void delete(String idS) throws Exception {
		noSQLDAO.deleteById(idS);

	}

}
