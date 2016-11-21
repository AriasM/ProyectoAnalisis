package com.peditualmuerzo.dataAccess;

import android.content.Context;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.peditualmuerzo.dominio.UsuarioServicio;

/**
 * Created by victo on 20/11/2016.
 */

public class UsuarioDataFireBase implements UsuarioData {

    private DatabaseReference ref;
    private DatabaseReference mensajeRef;
    private String estadoTransaccion;

    public UsuarioDataFireBase(){

        ref = FirebaseDatabase.getInstance().getReference();
        mensajeRef = ref.child("UsuarioServicio");
    }

    @Override
    public String nuevoUsuario(UsuarioServicio usuarioServicio) {

        this.mensajeRef.push().setValue(usuarioServicio, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    estadoTransaccion = "NO se pudo registrar el nuevo usuario..." + databaseError.getMessage();

                } else {

                    estadoTransaccion = "El usuario ha sido registrado exitosamente...";
                }
            }
        });

        return estadoTransaccion;
    }
}
