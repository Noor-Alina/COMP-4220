package project.UI;

import java.sql.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import project.BookManagement;


public class SellBooks extends JFrame {
	
	private int emp_id;
	private String emp_fname;
	private JLabel appNameLbl;
	private JTextField studIdTxtField;
	private JTextField isbnTxtField;
	private JTextField cardCodeTxtField;

	public SellBooks(int id, String name) {
		this.emp_id = id;
		this.emp_fname = name;
		
		setBackground(new Color(217, 217, 217));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 500);
		
		JPanel header = new JPanel();
		header.setFont(new Font("Tahoma", Font.PLAIN, 13));
		header.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(header, BorderLayout.NORTH);
		header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		header.setLayout(new BorderLayout(5, 5));
		
		// App Name Label
		appNameLbl = new JLabel("App Name");
		appNameLbl.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		appNameLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
		appNameLbl.setHorizontalTextPosition(SwingConstants.CENTER);
		appNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		appNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		header.add(appNameLbl, BorderLayout.WEST);
		
		// Horizontal Line
		JSeparator headerSeparator = new JSeparator();
		headerSeparator.setForeground(new Color(0, 0, 0));
		header.add(headerSeparator, BorderLayout.SOUTH);
		

		JSplitPane splitPane = new JSplitPane();
		splitPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		splitPane.setDividerSize(1);
		splitPane.setBorder(null);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		header.add(splitPane, BorderLayout.EAST);
		
		// Employee Name
		JLabel empNameLbl = new JLabel(emp_fname);
		empNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		empNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane.setLeftComponent(empNameLbl);
		
		// Logout Button
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBackground(new Color(255, 255, 255));
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        Login tt = new Login();
				tt.setVisible(true);
				dispose();
			}
		});
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logoutBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		splitPane.setRightComponent(logoutBtn);
		
		// body
		JPanel body = new JPanel();
		getContentPane().add(body, BorderLayout.CENTER);
		GridBagLayout gbl_body = new GridBagLayout();
		gbl_body.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_body.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_body.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_body.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		body.setLayout(gbl_body);
		
		// student id
		JLabel studIdLbl = new JLabel("Student ID");
		GridBagConstraints gbc_studIdLbl = new GridBagConstraints();
		gbc_studIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_studIdLbl.gridx = 10;
		gbc_studIdLbl.gridy = 4;
		body.add(studIdLbl, gbc_studIdLbl);
		
		studIdTxtField = new JTextField();
		GridBagConstraints gbc_studIdTxtField = new GridBagConstraints();
		gbc_studIdTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_studIdTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studIdTxtField.gridx = 12;
		gbc_studIdTxtField.gridy = 4;
		body.add(studIdTxtField, gbc_studIdTxtField);
		studIdTxtField.setColumns(10);
		
		// isbn-10
		JLabel isbnLbl = new JLabel("ISBN-10");
		GridBagConstraints gbc_isbnLbl = new GridBagConstraints();
		gbc_isbnLbl.insets = new Insets(0, 0, 5, 5);
		gbc_isbnLbl.gridx = 10;
		gbc_isbnLbl.gridy = 5;
		body.add(isbnLbl, gbc_isbnLbl);
		
		isbnTxtField = new JTextField();
		GridBagConstraints gbc_isbnTxtField = new GridBagConstraints();
		gbc_isbnTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_isbnTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_isbnTxtField.gridx = 12;
		gbc_isbnTxtField.gridy = 5;
		body.add(isbnTxtField, gbc_isbnTxtField);
		isbnTxtField.setColumns(10);
		
		// card code
		JLabel cardCodeLbl = new JLabel("Card Code");
		GridBagConstraints gbc_cardCodeLbl = new GridBagConstraints();
		gbc_cardCodeLbl.insets = new Insets(0, 0, 5, 5);
		gbc_cardCodeLbl.gridx = 10;
		gbc_cardCodeLbl.gridy = 6;
		body.add(cardCodeLbl, gbc_cardCodeLbl);
		
		cardCodeTxtField = new JTextField();
		GridBagConstraints gbc_cardCodeTxtField = new GridBagConstraints();
		gbc_cardCodeTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_cardCodeTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cardCodeTxtField.gridx = 12;
		gbc_cardCodeTxtField.gridy = 6;
		body.add(cardCodeTxtField, gbc_cardCodeTxtField);
		cardCodeTxtField.setColumns(10);
		
		// sell button
		JButton sellBtn = new JButton("Sell");
		sellBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookManagement bm = new BookManagement();
					String result = bm.sell(Long.parseLong(studIdTxtField.getText()), Long.parseLong(isbnTxtField.getText()), emp_id, Long.parseLong(cardCodeTxtField.getText()));			
					JOptionPane.showMessageDialog(null, result);
					setVisible(false);
				}catch(Exception e1) {System.out.println(e1);}
			}
		});
		GridBagConstraints gbc_sellBtn = new GridBagConstraints();
		gbc_sellBtn.insets = new Insets(0, 0, 0, 5);
		gbc_sellBtn.gridx = 11;
		gbc_sellBtn.gridy = 7;
		body.add(sellBtn, gbc_sellBtn);
		
	}

}