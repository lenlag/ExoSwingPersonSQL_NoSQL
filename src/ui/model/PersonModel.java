package ui.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractListModel;

import model.PersonDTO;
import persistance.excpetion.DaoException;
import services.IPersonService;


public class PersonModel  extends AbstractListModel<PersonDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPersonService service;
	List<PersonDTO> superList;
	

	public PersonModel(IPersonService personService) throws ClassNotFoundException, DaoException, SQLException, IOException {
		super();
		//this.superList = personServiceSQL.list();
		service = personService;
	}

	@Override
	public int getSize() {
		//return superList.size();
		int size = 0;
		try {
			size = service.list().size();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	@Override
	public PersonDTO getElementAt(int index) {
		PersonDTO personDTO = null;
		try {
			personDTO =  service.list().get(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return personDTO;
	}

}
