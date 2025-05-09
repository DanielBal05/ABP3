package Models;

import java.util.ArrayList;

public class Sistemas {
    private ArrayList<Usuario> usuarios;

    public Sistemas() {
        usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean verificarCredenciales(String nombre, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getContraseña().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }
}