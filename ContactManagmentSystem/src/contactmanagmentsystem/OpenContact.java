package contactmanagmentsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OpenContact {
    
    public OpenContact(Contact c, String oper, Database database, ContactsList contacts) throws SQLException {
        JFrame frame = new JFrame("Sistema de Gestion de Contactos");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel table = new JPanel(new GridLayout(6, 2, 15, 15));
        table.setBackground(Color.white);
        table.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        table.add(GUI.label("ID:"));
        JLabel id = GUI.label(String.valueOf(c.getID()));
        table.add(id);
        table.add(GUI.label("Primer Nombre:"));
        JTextField PrimerNombre = GUI.textField(c.getPrimerNombre());
        table.add(PrimerNombre);
        table.add(GUI.label("Ultimo Nombre:"));
        JTextField UltimoNombre = GUI.textField(c.getUltimoNombre());
        table.add(UltimoNombre);
        table.add(GUI.label("Numero de Telefono:"));
        JTextField Telefono = GUI.textField(c.getTelefono());
        table.add(Telefono);
        table.add(GUI.label("Correo Electronico:"));
        JTextField Correo = GUI.textField(c.getCorreo());
        table.add(Correo);

        JButton cancel = GUI.button("Cancel", new Color(208, 11, 3));
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        table.add(cancel);

        JButton save = GUI.button("Save", new Color(88, 179, 88));
        table.add(save);

        frame.add(table, BorderLayout.CENTER);

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validar que todos los campos estén llenos
                if (PrimerNombre.getText().isEmpty() || UltimoNombre.getText().isEmpty() ||
                    Telefono.getText().isEmpty() || Correo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si falta algún campo
                }

                // Si todos los campos están llenos, proceder con la acción correspondiente
                c.setID(Integer.parseInt(id.getText().toString()));
                c.setPrimerNombre(PrimerNombre.getText().toString());
                c.setUltimoNombre(UltimoNombre.getText().toString());
                c.setTelefono(Telefono.getText().toString());
                c.setCorreo(Correo.getText().toString());
                try {
                    if (oper.equals("new")) {
                        database.insetContact(c);
                    } else if (oper.equals("edit")) {
                        database.updateContact(c);
                    }
                    frame.dispose();
                    contacts.refresh(database.getContacts());
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        if (oper.equals("new")) {
            id.setText(String.valueOf(database.getNextID()));
        } else if (oper.equals("edit")) {
            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    c.setID(Integer.parseInt(id.getText().toString()));
                    c.setPrimerNombre(PrimerNombre.getText().toString());
                    c.setUltimoNombre(UltimoNombre.getText().toString());
                    c.setTelefono(Telefono.getText().toString());
                    c.setCorreo(Correo.getText().toString());
                    try {
                        database.updateContact(c);
                        frame.dispose();
                        contacts.refresh(database.getContacts());
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, e1.toString());
                    }
                }
            });
        } else if (oper.equals("view")) {
            save.setVisible(false);
            cancel.setVisible(false);
        }

        frame.setVisible(true);
    }
    
}
