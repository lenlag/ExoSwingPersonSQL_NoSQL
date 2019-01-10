package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.PersonDTO;
import persistance.excpetion.DaoException;

public interface IPersonService {
	
	

	public List<PersonDTO> list() throws ClassNotFoundException, DaoException, SQLException, IOException ;

	public void save(PersonDTO person) throws Exception;

	public PersonDTO getById(String idS) throws Exception;

	public void delete(String idS) throws Exception;

	public void update(PersonDTO person) throws Exception ;
}
