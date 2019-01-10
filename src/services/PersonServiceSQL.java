package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entitie.Person;
import model.PersonDTO;
import persistance.dao.PersonDAO;
import persistance.excpetion.DaoException;

public class PersonServiceSQL implements IPersonService{

	// BagnoleDao bagnoleDao = new BagnoleDao();
	PersonDAO personDAO = new PersonDAO();

	public List<PersonDTO> list() throws ClassNotFoundException, DaoException, SQLException, IOException {
		List<PersonDTO> listToReturn = new ArrayList<>(); 
		for (Person person : personDAO.findList()) {
			listToReturn.add(new PersonDTO(person));
		}
		return listToReturn;
	}

	public void save(PersonDTO person) throws Exception {
		personDAO.create(person.getPerson());
	}

	public PersonDTO getById(String idS) throws Exception {
		Long id = Long.valueOf(idS);
		Person person = personDAO.findById(id);
		return new PersonDTO(person);
	}

	public void delete(String idS) throws Exception {
		Long id = Long.valueOf(idS);
		personDAO.deleteById(id);
	}

	public void update(PersonDTO personEntity) throws Exception {
		personDAO.updateById(personEntity.getPerson());
	}

}
