package bb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abirami
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FetchData implements ActionListener {

    JFrame frame1;//creating object of first JFrame
    JLabel deptLabel;//creating object of JLabel
    JLabel semLabel;
    JLabel subLabel;
    JTextField d;//creating object of JTextfield
    JTextField s;
    JTextField ss;
    JButton fetchButton;//creating object of JButton
    JButton resetButton;//creating object of JButton
    
    JFrame frame2;//creating object of second JFrame
    DefaultTableModel defaultTableModel;//creating object of DefaultTableModel
    JTable table;//Creating object of JTable
    Connection connection;//Creating object of Connection class
    Statement statement;//Creating object of Statement class
    int flag=0;


    FetchData() {

        frame1 = new JFrame();
        frame1.setTitle("Search Database");//setting the title of first JFrame
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//setting default close operation
        GridBagLayout bagLayout = new GridBagLayout();//creating object of GridBagLayout
        GridBagConstraints bagConstraints = new GridBagConstraints();//creating object of GridBagConstratints
        frame1.setSize(700, 300);//setting the size of first JFrame
        frame1.setLayout(bagLayout);//setting the layout to GridBagLayout of first JFrame

        bagConstraints.insets = new Insets(15, 40, 0, 0);//Setting the padding between the components and neighboring components

      //Setting the property of JLabel and adding it to the first JFrame
        deptLabel = new JLabel("Enter Department");
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        frame1.add(deptLabel, bagConstraints);

      //Setting the property of JTextfield and adding it to the first JFrame
        d = new JTextField(15);
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 0;
        frame1.add(d, bagConstraints);
        
        semLabel = new JLabel("Enter Semester");
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 1;
        frame1.add(semLabel, bagConstraints);

      //Setting the property of JTextfield and adding it to the first JFrame
        s = new JTextField(15);
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 1;
        frame1.add(s, bagConstraints);
        
         subLabel = new JLabel("Enter Subject");
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 2;
        frame1.add(subLabel, bagConstraints);

      //Setting the property of JTextfield and adding it to the first JFrame
        ss = new JTextField(15);
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 2;
        frame1.add(ss, bagConstraints);

      //Setting the property of JButton(Fetch Data) and adding it to the first JFrame
        fetchButton = new JButton("Fetch Data");
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 3;
        bagConstraints.ipadx = 60;
        frame1.add(fetchButton, bagConstraints);

      //Setting the property of JButton(Reset Data) and adding it to the second JFrame
        resetButton = new JButton("Reset Data");
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 3;
        frame1.add(resetButton, bagConstraints);

        //adding ActionListener to both buttons
        fetchButton.addActionListener(this);
        resetButton.addActionListener(this);


        frame1.setVisible(true);//Setting the visibility of First JFrame
        frame1.validate();//Performing relayout of the First JFrame


    }

    public static void main(String[] args) {
        new FetchData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fetchButton) {

            String a = d.getText().toString();
            DB.d=a;
            String b=s.getText().toString();
            String c=ss.getText().toString();
            DB.subb=c;
            frameSecond(a,b,c);//passing the text value to frameSecond method

        }
        if (e.getSource() == resetButton) {
            d.setText("");//resetting the value of username text field
            s.setText("");//
            ss.setText("");//
            dash i=new dash();
            i.setVisible(true);
            //this.dispose();
        }

    }


    public void frameSecond(String x,String y,String z) {
    	
    	//setting the properties of second JFrame
        frame2 = new JFrame("Database Results");
        frame2.setLayout(new FlowLayout());
        frame2.setSize(600, 600);

        //Setting the properties of JTable and DefaultTableModel
        defaultTableModel = new DefaultTableModel();
        table = new JTable(defaultTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        table.setFillsViewportHeight(true);
        frame2.add(new JScrollPane(table));
       
        
        //defaultTableModel.addColumn("Department");
        defaultTableModel.addColumn("BookId");
        //defaultTableModel.addColumn("Semester");
         defaultTableModel.addColumn("Subject");
         defaultTableModel.addColumn("Author");
        defaultTableModel.addColumn("Count");
        try {
                int q=Integer.parseInt(y); 
                ResultSet rs;
                DB.sem=q;
        
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/abby", "abby", "abby");//Crating connection with database
            statement = connection.createStatement();//crating statement object
            //String query = "select * from BANK where DEPT = '" + x + "' and SEM = "+ q +" and SUM = '" + z + "'";//Storing MySQL query in A string variable
            //ResultSet resultSet = statement.executeQuery(query);//executing query and storing result in ResultSet
            PreparedStatement ps=(PreparedStatement) connection.prepareCall(" SELECT * FROM ABBY.BANK where dept=? and sem=? and sub=? and count>=1" );
               
            ps.setString(1,x );
            ps.setInt(2,q );
            ps.setString(3,z);
                //ps.setString(4,da);

            rs=ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();   


            while (rs.next()) {
                //flag=1;
                //for (int i = 1; i <= columns; i++)
                     //row.addElement( rs.getObject(i) );
            	
            	//Retrieving details from the database and storing it in the String variables
                String a = rs.getString("sub");
                String b = rs.getString("author");
                String c = rs.getString("dept");
                String d = rs.getString("count");
                String dd = rs.getString("bid");
                if (c.equals(x)) {
                    flag = 1;
                    defaultTableModel.addRow(new Object[]{dd,a,b,d});
                    //defaultTableModel.addRow(new Object[]{a,b,c,d});//Adding row in Table
                    
                    frame2.setVisible(true);//Setting the visibility of second Frame
                    frame2.validate();
                    //break;
                }
                //else continue;

            }
            

            if (flag == 0) {
                JOptionPane.showMessageDialog(null, "No records Found");//When invalid username is entered
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
