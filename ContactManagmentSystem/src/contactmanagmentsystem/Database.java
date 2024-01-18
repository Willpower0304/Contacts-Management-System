package contactmanagmentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Database {
    
    private String url = "jdbc:mysql://localhost/contacts management system";
    private String user = "root";
    private String pass = "";
    private Statement statement;
    
    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                ResultSet.CONCUR_READ_ONLY);
    }
    
    public ArrayList<Contact> getContacts() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        String select = "SELECT * FROM `contacts`;";
        ResultSet rs = statement.executeQuery(select);
        while (rs.next()) {
            Contact c = new Contact();
            c.setID(rs.getInt("ID"));
            c.setPrimerNombre(rs.getString("Primer Nombre"));
            c.setUltimoNombre(rs.getString("Ultimo Nombre"));
            c.setTelefono(rs.getString("Telefono"));
            c.setCorreo(rs.getString("Correo"));
            contacts.add(c);
        }
        return contacts;
    }
    
    public void insetContact(Contact c) throws SQLException {
         // Verificar si el número de teléfono o correo electrónico ya existen en la base de datos
    if (contactExists(c.getTelefono(), c.getCorreo())) {
        JOptionPane.showMessageDialog(null, "El número de teléfono o correo electrónico ya"
                + " existen.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
        String insert = "INSERT INTO `contacts`(`ID`, `Primer Nombre`, `Ultimo Nombre`, "
                + "`Telefono`, `Correo`) VALUES ('"+c.getID()+"','"+c.getPrimerNombre()+"',"
                + "'"+c.getUltimoNombre()+"','"+c.getTelefono()+"','"+c.getCorreo()+"')";
        statement.execute(insert);
    }
    
    public void updateContact(Contact c) throws SQLException {
        String update = "UPDATE `contacts` SET `Primer Nombre`='"+c.getPrimerNombre()+"',"
                + "`Ultimo Nombre`='"+c.getUltimoNombre()+"',`Telefono`='"+c.getTelefono()+"',"
                + "`Correo`='"+c.getCorreo()+"' WHERE `ID` = "+c.getID()+" ;";
         statement.execute(update);
    }
    
    public void deleteContact(Contact c) throws SQLException {
        String delete = "DELETE FROM `contacts` WHERE `ID` = "+c.getID()+";";
        statement.execute(delete);
    }
    
    public int getNextID() throws SQLException {
        int id = 0;
        ArrayList<Contact> contacts = getContacts();
        if (contacts.size()!=0) {
            Contact last = contacts.get(contacts.size()-1);
            id = last.getID()+1;
        }
        return id;
    }
    
    // método para verificar si el número de teléfono o correo electrónico ya existen
    private boolean contactExists(String telefono, String correo) throws SQLException {
    String query = "SELECT * FROM `contacts` WHERE `Telefono`='" + telefono + "' OR `Correo`='" + correo + "'";
    ResultSet rs = statement.executeQuery(query);
    return rs.next(); // Devuelve true si hay algún resultado, es decir, el contacto ya existe
    }
    
}
