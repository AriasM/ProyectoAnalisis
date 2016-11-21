package com.peditualmuerzo.presenter;

import com.peditualmuerzo.dataAccess.UsuarioData;
import com.peditualmuerzo.dataAccess.UsuarioDataFireBase;
import com.peditualmuerzo.dominio.UsuarioServicio;

/**
 * Created by victo on 20/11/2016.
 */

public class UsuarioPresenter {

    private UsuarioDataFireBase usuarioData;

    public UsuarioPresenter(UsuarioDataFireBase usuarioData) {
        this.usuarioData = usuarioData;
    }

    public String nuevoUsuario(UsuarioServicio usuarioServicio){

        return usuarioData.nuevoUsuario(usuarioServicio);
    }
}
