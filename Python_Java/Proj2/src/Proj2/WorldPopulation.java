package Proj2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class WorldPopulation {

	private JFrame frame;

	private DefaultListModel<String> CountryDLM;
	private DefaultListModel<String> SelectedDLM;
	private DefaultTableModel populationDLM;
	private JList list_country;
	private JList list_selected;
	private JButton btnAll;
	private JTextField text_date;
	private JTable table_country;
	private JScrollPane scrollPane_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorldPopulation window = new WorldPopulation();
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
	public WorldPopulation() {
		
		SelectedDLM = new DefaultListModel<String>();
		DBConnect x = new DBConnect();
		CountryDLM = x.getData();

		
		
		//JOptionPane.showConfirmDialog(null, "Done");
		initialize();
		list_selected.setModel(SelectedDLM);
		list_country.setModel(CountryDLM);
		
		
		JButton btnGetPopulation = new JButton("Get Population");
		btnGetPopulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddPopulation();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGetPopulation.setBounds(526, 28, 215, 25);
		frame.getContentPane().add(btnGetPopulation);
		
		populationDLM = new DefaultTableModel();
		
		btnAll = new JButton("All");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAll();
			}
		});
		btnAll.setBounds(239, 235, 74, 45);
		frame.getContentPane().add(btnAll);
		
		text_date = new JTextField();
		text_date.setText("2015-12-24");
		text_date.setBounds(118, 29, 116, 22);
		frame.getContentPane().add(text_date);
		text_date.setColumns(10);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(526, 73, 718, 405);
		frame.getContentPane().add(scrollPane_3);
		
		table_country = new JTable();
		scrollPane_3.setViewportView(table_country);
		populationDLM.addColumn("Country");
		populationDLM.addColumn("Population");
		table_country.setModel(populationDLM);
		
		JButton button = new JButton("Threading");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddPopulationThread();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setBounds(752, 26, 215, 25);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("Clear");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClearPopulation();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_1.setBounds(979, 25, 215, 25);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("Clear");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SelectedClear();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_2.setBounds(239, 308, 74, 45);
		frame.getContentPane().add(button_2);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		list_country = new JList();
		
		frame = new JFrame();
		
		frame.setBounds(100, 100, 1274, 571);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterDate = new JLabel("Enter Date:");
		lblEnterDate.setBounds(30, 32, 116, 16);
		frame.getContentPane().add(lblEnterDate);
		
		JButton btnNewButton_1 = new JButton(">>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddSelected();
			}
		});
		btnNewButton_1.setBounds(239, 158, 74, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(144, 263, 2, 2);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 69, 215, 409);
		frame.getContentPane().add(scrollPane_1);
		
		
		scrollPane_1.setViewportView(list_country);
	
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(325, 69, 177, 409);
		frame.getContentPane().add(scrollPane_2);
		
		list_selected = new JList();
		list_selected.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) 
					RemoveSelected();
			}
		});
		scrollPane_2.setViewportView(list_selected);
	
	}

	private void AddSelected() {
		SelectedDLM.addElement(list_country.getSelectedValue().toString());
	}	
	private void AddAll() {
		for (int i = 0; i < CountryDLM.size(); i++)
		{
			SelectedDLM.addElement(CountryDLM.getElementAt(i).toString());
		}
	}	

	private void RemoveSelected() {
		SelectedDLM.remove(list_selected.getSelectedIndex());
		
	}
	private void AddPopulation() throws IOException  {
		for (int i =0; i < SelectedDLM.size();i++ ) {
			String rslt = WEB.getPopulation(SelectedDLM.getElementAt(i).toString(),text_date.getText()); // list_selected.getSelectedValue().toString(), "2015-12-24");
			populationDLM.addRow(new Object[]{SelectedDLM.getElementAt(i).toString(),rslt});
		}

	}
	private void AddPopulationThread() throws IOException  {
		ExecutorService es = Executors.newFixedThreadPool(4);
		for (int i =0; i < SelectedDLM.size() ;i++ ) {
			es.execute(new WEB(SelectedDLM.getElementAt(i).toString(),text_date.getText(),populationDLM));
			//String rslt = WEB.getPopulation(SelectedDLM.getElementAt(i).toString(),text_date.getText()); // list_selected.getSelectedValue().toString(), "2015-12-24");
			//populationDLM.addRow(new Object[]{SelectedDLM.getElementAt(i).toString(),rslt});
		}
		es.shutdown();
	}
	
	private void ClearPopulation() throws IOException  {
		int nRows = populationDLM.getRowCount();
		for (int i = nRows - 1; i >= 0 ;i-- ) {
			populationDLM.removeRow(i);
		}
	}
	
	private void SelectedClear() throws IOException  {
		SelectedDLM.removeAllElements();
	}
	
	protected JButton getBtnAll() {
		return btnAll;
	}
	public JTextField getText_date() {
		return text_date;
	}
	public JScrollPane getScrollPane() {
		return scrollPane_3;
	}
}

