package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import manager.CfgManager;
import model.PersonDTO;
import persistance.excpetion.DaoException;
import services.IPersonService;
import services.PersonServiceFactory;
import ui.model.PersonModel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class Main {

	private JFrame frame;
	private IPersonService personService;
	private PersonModel personModel;
	private PersonDTO selectedPerson;
	private CreateOrUpdate createOrUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {

		askSelectDB();
		try {
			initialize();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
	}

	private void askSelectDB() {
		CfgManager manager = new CfgManager();
		String[] services = manager.getServices();

		String selectedService = (String) JOptionPane.showInputDialog(
				null, 								// parent
				"Choisissez la base de donnée", 	// message
				"Sélection BDD", 					// titre
				JOptionPane.QUESTION_MESSAGE, 		// type de message (icone)
				null, 							// icône, (null conserve icône par défaut)
				services, services[0]);				// valeur par défaut


		if (selectedService != null) { // Traitement si pas annulé
			// écrire dans le service.cfg le Service qui à était sélectionné
			try {
				manager.saveSelectedService(selectedService);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// faire un truck ??

		}

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private void initialize()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		

		personService = PersonServiceFactory.newInstance();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 213, 239);
		frame.getContentPane().add(scrollPane);

		try {
			personModel = new PersonModel(personService); // create model & add list
		} catch (ClassNotFoundException | DaoException | SQLException | IOException e) {
			e.printStackTrace();
		}

		JList<PersonDTO> list = new JList<>(); // create JList
		scrollPane.setViewportView(list);
		list.setModel(personModel); // add model to the JList

		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(279, 9, 145, 67);
		frame.getContentPane().add(btnCreate);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(279, 86, 145, 67);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(279, 164, 145, 67);
		frame.getContentPane().add(btnDelete);

		addListener(list, btnCreate, btnUpdate, btnDelete);
	}

	private void addListener(JList<PersonDTO> list, JButton btnCreate, JButton btnUpdate, JButton btnDelete) {

		// add listener on the JList to save the selected one
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedPerson = list.getSelectedValue();
			}
		});

		// add listener onto the Add btn.
		// Call the JDialog and display
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				createOrUpdate = new CreateOrUpdate(personService);
				createOrUpdate.setModal(true);
				createOrUpdate.setVisible(true);

				list.updateUI();
				// System.out.println(list.getModel().getSize());
			}
		});

		// add listener onto the Update btn.
		// Call the JDialog, pass the Selected person and display
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//setDefaultSelectedBagnole();
				if(selectedPerson != null) {

					createOrUpdate = new CreateOrUpdate(personService, selectedPerson);
					createOrUpdate.setModal(true);
					createOrUpdate.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(frame.getContentPane(), "Erreur, Veuillez sélectionné une personne", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}

				list.updateUI();
			}
		});

		// add listener onto the Delete btn.
		// Call the JOptionPane, display it. And delete the selected one if Yes
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int rep = JOptionPane.showConfirmDialog(frame, "Etes vous sur ?", "Suppression Person",
						JOptionPane.YES_NO_OPTION);

				if (rep == JOptionPane.YES_OPTION) {
					try {
						personService.delete(selectedPerson.getId());
						selectedPerson = null;

					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, "La Person sélectioné n'éxiste pas... ", "ERREUR",
								JOptionPane.QUESTION_MESSAGE);
						System.out.println("La Person sélectioné n'éxiste pas");
					}
				}
				list.updateUI();
			}
		});
	}
/*
	// if no person is selected, take the first on the list
	private void setDefaultSelectedBagnole() {

		if (selectedPerson == null) {
			try {
				selectedPerson = personService.list().get(0);
			} catch (ClassNotFoundException | DaoException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	/*
	 * //get the list from the db private void initListToPrint() { try { listToPrint
	 * = personServiceSQL.list(); } catch (ClassNotFoundException | DaoException |
	 * SQLException | IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */
}
