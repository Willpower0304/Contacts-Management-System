package contactmanagmentsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ContactsList {
    
    private GridLayout gridLayout;
    private JPanel table;
    private Database database;
    
    public ContactsList(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Sistema de Gestion de Contactos");
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(77, 50, 50, 50));
        top.setBackground(null);
        
        JLabel title = new JLabel("Bienvenido al Sistema de Gestion de Contactos");
        title.setFont(new Font("Calibri", Font.BOLD, 35));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        top.add(title, BorderLayout.CENTER);
        
        JButton newContact = new JButton("Nuevo Contacto");
        newContact.setFont(new Font("Tahoma", Font.BOLD, 18));
        newContact.setBackground(new Color(88, 179, 88));
        newContact.setForeground(Color.white);
        newContact.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        newContact.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new OpenContact(new Contact(), "new", database, ContactsList.this);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.toString());
                }
            }
        });
        top.add(newContact, BorderLayout.EAST);
        
        frame.add(top, BorderLayout.NORTH);
        
        gridLayout = new GridLayout(8, 1, 0, 0);
        table = new JPanel(gridLayout);
        table.setBackground(Color.white);
        try {
            refresh(database.getContacts());
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1.toString());
        }
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        sp.setBackground(null);
        frame.add(sp, BorderLayout.CENTER);
        
        frame.setVisible(true);
        
    }
    
    public void refresh(ArrayList<Contact> contacts) {
        table.removeAll();
        table.repaint();
        table.revalidate();
        
        int rows = contacts.size();
        if (rows<8) rows=8;
        gridLayout.setRows(rows);
        
        for (int i=0; i<contacts.size(); i++) {
            Contact c = contacts.get(i);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            panel.setBackground(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            if (i%2 == 0) panel.setBackground(Color.decode("#e5e5e5"));
            panel.setPreferredSize(new Dimension(100, 55));
            panel.add(GUI.label(c.getPrimerNombre()+" "+c.getUltimoNombre()));
            panel.add(GUI.label(c.getTelefono()));
            panel.add(GUI.label(c.getCorreo()));
            JButton view = GUI.button("Mostrar", new Color(88, 179, 99));
            view.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        new OpenContact(c, "view", database, ContactsList.this);
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, e1.toString());
                    }
                }
            });
            panel.add(view);
            JButton edit = GUI.button("Editar", new Color(63, 134, 196));
            edit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        new OpenContact(c, "edit", database, ContactsList.this);
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, e1.toString());
                    }
                }
            });
            panel.add(edit);
            JButton delete = GUI.button("Eliminar", new Color(208, 11, 3));
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Mostrar un JOptionPane de confirmación
                int confirmResult = JOptionPane.showConfirmDialog(null,
                        "¿Seguro que desea eliminar este contacto?", "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        database.deleteContact(c);
                        refresh(database.getContacts());
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, e1.toString());
                    }
                }
            }
            });
            panel.add(delete);
            table.add(panel);
        }
    }
    
}
