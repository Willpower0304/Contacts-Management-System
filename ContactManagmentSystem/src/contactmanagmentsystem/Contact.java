package contactmanagmentsystem;

public class Contact {
    
    private int ID;
    private String PrimerNombre;
    private String UltimoNombre;
    private String Telefono;
    private String Correo;
    
    public Contact() {}
    
    public int getID() {return ID;}
    public String getPrimerNombre() {return PrimerNombre;}
    public String getUltimoNombre() {return UltimoNombre;}
    public String getTelefono() {return Telefono ;}
    public String getCorreo() {return Correo;}
    
    public void setID(int ID) {this.ID=ID;}
    public void setPrimerNombre(String PrimerNombre) {this.PrimerNombre=PrimerNombre;}
    public void setUltimoNombre(String UltimoNombre) {this.UltimoNombre=UltimoNombre;}
    public void setTelefono(String Telefono) {this.Telefono=Telefono;}
    public void setCorreo(String Correo) {this.Correo=Correo;}
    
}
