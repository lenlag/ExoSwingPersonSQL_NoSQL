package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.PersonDTO;
import services.IPersonService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class CreateOrUpdate extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private IPersonService personService;
	private PersonDTO person;
	private Boolean isUpdate;
	private JTextField txtName;
	private JTextField txtPrenom;
	private JTextField txtAge;
	private JTextField txtNum;

	/**
	 * @wbp.parser.constructor
	 */
	public CreateOrUpdate(IPersonService service, PersonDTO person) {
		super();
		this.personService = service;
		this.person = person;
		isUpdate = true;
		initialize();
	}

	public CreateOrUpdate(IPersonService service) {
		super();
		this.personService = service;
		this.person = new PersonDTO("", "", "", 0);
		isUpdate = false;
		initialize();
	}

	/**
	 * Create the dialog.
	 */
	public void initialize() {
		setResizable(false);
		getContentPane().setBackground(SystemColor.activeCaption);
		setBounds(100, 100, 304, 223);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.menu);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblName = new JLabel("Name :");
		lblName.setBounds(10, 35, 46, 14);
		contentPanel.add(lblName);

		JLabel lblFirstName = new JLabel("FirstName : ");
		lblFirstName.setBounds(10, 60, 46, 14);
		contentPanel.add(lblFirstName);

		JLabel lblAge = new JLabel("Age :");
		lblAge.setBounds(10, 85, 46, 14);
		contentPanel.add(lblAge);

		txtName = new JTextField();
		txtName.setBounds(87, 32, 86, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		txtPrenom = new JTextField();
		txtPrenom.setBounds(87, 57, 86, 20);
		contentPanel.add(txtPrenom);
		txtPrenom.setColumns(10);

		txtAge = new JTextField();
		txtAge.setHorizontalAlignment(SwingConstants.LEFT);
		txtAge.setBounds(87, 82, 86, 20);
		contentPanel.add(txtAge);
		txtAge.setColumns(10);

		JLabel lblNum = new JLabel("Num:");
		lblNum.setBounds(10, 11, 46, 14);
		contentPanel.add(lblNum);

		txtNum = new JTextField();
		txtNum.setBounds(87, 8, 86, 20);
		contentPanel.add(txtNum);
		txtNum.setColumns(10);
		initTxtField();
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.menu);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);

				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// ***************//
						if (txtNum.getText().length() != 0) {
							person.setNum(txtNum.getText());

						} else {
							showJDialogErrorNum();
							return;
						}
						// ***************//
						if (txtName.getText().length() != 0) {
							person.setName(txtName.getText());

						} else {
							showJDialogErrorName();
							return;
						}

						// ***************//
						if (txtPrenom.getText().length() != 0) {
							person.setFirstName(txtPrenom.getText());

						} else {
							showJDialogErrorFirstName();
							return;
						}

						// ***************//
						if (isInteger(txtAge.getText())) {
							Integer age = Integer.valueOf(txtAge.getText());
							person.setAge(age);
						} else {

							showJDialogErrorAge();
							return;

						}

						// ***************//
						if (isUpdate) {
							try {
								personService.update(person);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} else {
							try {
								personService.save(person);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						dispose();

					}

				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}

	public void initTxtField() {
		if (isUpdate) {
			txtNum.setText(person.getNum());
			txtName.setText(person.getName());
			txtPrenom.setText(person.getFirstName());
			txtAge.setText(person.getAge() + "");
		}

	}

	private void showJDialogErrorNum() {
		JOptionPane.showMessageDialog(getContentPane(), "Erreur, Veuillez entrée un Num", "Erreur, Num vide",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showJDialogErrorName() {
		JOptionPane.showMessageDialog(getContentPane(), "Erreur, Veuillez entrée un Nom", "Erreur, Nom vide",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showJDialogErrorFirstName() {
		JOptionPane.showMessageDialog(getContentPane(), "Erreur, Veuillez entrée un Prenom", "Erreur, Model vide",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showJDialogErrorAge() {
		JOptionPane.showMessageDialog(getContentPane(), "Erreur, Veuillez entrée une année correcte",
				"Erreur, Année non valide", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);

		} catch (NumberFormatException e) {
			return false;

		} catch (NullPointerException e) {
			return false;

		}
		// only got here if we didn't return false
		return true;
	}
}
