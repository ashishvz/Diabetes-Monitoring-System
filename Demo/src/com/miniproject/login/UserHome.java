package com.miniproject.login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;


public class UserHome extends JFrame {

    private static final long serialVersionUID = 1;
    private JPanel contentPane;
    private JTextField textField;
    private JButton btnNewButton,btngraph;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserHome() {

    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes) {

    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Diabetes Monitoring System");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(250, 13, 600, 150);
        contentPane.add(lblNewLabel);
        
        JLabel lbl = new JLabel("mg/dL");
        lbl.setForeground(Color.BLACK);
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lbl.setBounds(735, 170, 281,50);
        contentPane.add(lbl);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(450, 170, 281,50);
        contentPane.add(textField);
        textField.setColumns(10);

        
        JLabel lblUsername = new JLabel("Enter the reading:");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(150, 170, 300, 52);
        contentPane.add(lblUsername);
        
        
        btnNewButton = new JButton("Save");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(250, 300, 162, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String readings = textField.getText();
                if(textField.getText().isEmpty())
                {
                	JOptionPane.showMessageDialog(btnNewButton, "Please Enter the readings!!");	
                }
                else
                {
                    try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dms",
                        "root", "AShish*%34");
                    String query= "insert into dia_data(reading) values("+readings+");";
                    java.sql.Statement st =connection.createStatement();
                    int i=st.executeUpdate(query);
                    if(i>0)
                    {
                    	 JOptionPane.showMessageDialog(btnNewButton, "Succesfully saved");
                    }
                    else
                    {
                    	JOptionPane.showMessageDialog(btnNewButton, "Error in Saving");
                    }
                    
                    
                    
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                }
            }
        });
        
        btngraph = new JButton("Show Graph!");
        btngraph.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btngraph.setBounds(450, 300,300, 73);
        btngraph.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	try {
            	String query = "SELECT * from dia_data";
        		JDBCCategoryDataset dataset = new JDBCCategoryDataset(
        				"jdbc:mysql://localhost:3306/dms", "com.mysql.jdbc.Driver",
        				"root", "AShish*%34");

        		dataset.executeQuery(query);
        		JFreeChart chart = ChartFactory.createLineChart("Diabetic Graph", "Numbers Of Readings", "SugarLevels",
        				dataset, PlotOrientation.VERTICAL, true, true, false);
        		ChartPanel chartPanel = new ChartPanel(chart);
        		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        	   JFrame f = new JFrame("Chart");
        		f.setContentPane(chartPanel);
        		f.pack();
        		f.setVisible(true);
            	}
            	catch(SQLException ex) {
            		ex.printStackTrace();
            	} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
                
            }
        });

        contentPane.add(btnNewButton);
        contentPane.add(btngraph);
   

    }
}