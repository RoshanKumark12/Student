import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CrudApp {
    private JPanel Main;
    private JTextField inpName;
    private JTextField inpRoll;
    private JButton addButton;
    private JButton deleteButton;
    private JTextArea display;
    private JTextField inpNumber;
    private JTextField serInp;
    private JButton searchButton;
    private JButton updateButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CrudApp");
        frame.setContentPane(new CrudApp().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public CrudApp() {
        connect();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name, rollNo, phoneNo;
                try {
                    name = inpName.getText();
                    rollNo = inpRoll.getText();
                    phoneNo = inpNumber.getText();
                    pst = con.prepareStatement("insert into student_info(student_name,student_roll_number,student_mobile_number)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, rollNo);
                    pst.setString(3, phoneNo);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added...");
                    inpName.setText("");
                    inpRoll.setText("");
                    inpNumber.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int id = Integer.parseInt(serInp.getText());
                    pst = con.prepareStatement("select student_name,student_roll_number,student_mobile_number from student_info where sno = ?");
                    pst.setInt(1,id);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()){
                        String name = rs.getString(1);
                        String rollNo = rs.getString(2);
                        String mobileNo = rs.getString(3);
                        display.setText("");
                        display.append("Name: - "+ name);
                        display.append("\nRoll Number: - "+ rollNo);
                        display.append("\nMobile Number: - "+ mobileNo);
                        inpName.setText(name);
                        inpRoll.setText(rollNo);
                        inpNumber.setText(mobileNo);
                    } else {
                        display.setText("No Information Found");
                        inpName.setText("");
                        inpRoll.setText("");
                        inpNumber.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Search...");
                    }
                } catch (NumberFormatException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name, rollNo, phoneNo;
                int id;
                try {
                    name = inpName.getText();
                    rollNo = inpRoll.getText();
                    phoneNo = inpNumber.getText();
                    id = Integer.parseInt(serInp.getText());
                    pst = con.prepareStatement("update student_info set student_name=?,student_roll_number=?,student_mobile_number = ? where sno = ?");
                    pst.setString(1, name);
                    pst.setString(2, rollNo);
                    pst.setString(3, phoneNo);
                    pst.setInt(4, id);
                    int rs = pst.executeUpdate();
                    if(rs!=0) {
                        JOptionPane.showMessageDialog(null, "Update Success...");
                        display.setText("Update Success fully");
                        inpName.setText("");
                        inpRoll.setText("");
                        inpNumber.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null,"Enter Update Data...");
                    }
                } catch (SQLException | NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int id;
                try {
                    id = Integer.parseInt(serInp.getText());
                    System.out.println(id);
                    pst = con.prepareStatement("delete from student_info where sno = ?");
                    pst.setInt(1,id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Recode Deleted...");
                    display.setText("Delete Success fully");
                    inpName.setText("");
                    inpRoll.setText("");
                    inpNumber.setText("");
                } catch (SQLException | NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    Connection con;
    PreparedStatement pst;
    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student_info", "root","");
            System.out.println("Success");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
