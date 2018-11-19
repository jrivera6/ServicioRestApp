package pe.jrivera6.serviciorestapp.models;

public class Usuario {

    private Long id;
    private String nombres;
    private String correo;
    private String contraseña;

    public Usuario() {
    }

    public Usuario(String nombres, String correo, String contraseña) {
        this.nombres = nombres;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
