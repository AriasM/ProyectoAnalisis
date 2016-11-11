package com.peditualmuerzo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.core.Tag;
import com.firebase.client.core.view.View;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dominio.Control;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.dominio.Referencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

public class ConsultarPlatosSolicitarActivity extends Activity {

    private TextView mensajeTextView,mensajeIngredientesPrincipal,mensajeIngredientesOpcional;
    private Button btnSolicitar, btnDOs;
    private CheckBox cbPlatoPrincipal, cbPlatoOpcional;
    public static String ingredientesPrincipal;
    public static String ingredientesOpcionales;
    public static ArrayList<Plato> platos;
    private Runnable refresh;
    private Handler mHandler;
    public static ArrayList<Control> valores=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_platos_solicitar);
        btnSolicitar = (Button) findViewById(R.id.boton);
        cbPlatoOpcional = (CheckBox) findViewById(R.id.cbPlatoOpcional);
        cbPlatoPrincipal = (CheckBox) findViewById(R.id.cbPlatoPrincipal);


        cargarDatosPlatos();


        btnSolicitar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ConsultarPlatosSolicitarActivity.this, ConfirmarSolicitudAlimentacionActivity.class);

                if(cbPlatoOpcional.isChecked() && cbPlatoPrincipal.isChecked()){
                    intent.putExtra("platos", platos);
                    startActivity(intent);
                }
                else if(cbPlatoPrincipal.isChecked()){
                    for(int i=0;i<platos.size();i++){
                        if(platos.get(i).isOpcional()){
                            platos.remove(i);
                        }
                    }
                    intent.putExtra("platos", platos);
                    startActivity(intent);
                }
                else{
                    for(int i=0;i<platos.size();i++){
                        if(!platos.get(i).isOpcional()){
                            platos.remove(i);
                        }
                    }
                    intent.putExtra("platos", platos);
                    startActivity(intent);
                }

            }
        });

    }

    public void cargarDatosPlatos(){

        mensajeTextView = (TextView) findViewById(R.id.mensajeTextView);
        mensajeIngredientesPrincipal = (TextView) findViewById(R.id.mensajeIngredientesPrincipal);
        mensajeIngredientesOpcional = (TextView) findViewById(R.id.mensajeIngredientesOpcional);

        platos = new ArrayList();
        ingredientesPrincipal = "";
        ingredientesOpcionales = "";

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference raizPlato = database.getReference(PedidoDataFirebase.PLATILLO_REFERENCE);
        raizPlato.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> objectMap = (HashMap<String, String>) dataSnapshot.getValue();
                int contador = 0;
                for(Object obj : objectMap.values()){
                    String llave = objectMap.keySet().toArray()[contador].toString();
                    String json = new Gson().toJson(obj);
                    Plato plato = new Gson().fromJson(json, Plato.class);
                    plato.setIdPlato(llave);
                    platos.add(plato);
                    contador++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference raizControl= database.getReference(PedidoDataFirebase.CONTROL_REFERENCE);
        raizControl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Control control=dataSnapshot.getValue(Control.class);
                for(int h=0;h<platos.size();h++){
                    if(platos.get(h).getDia().getNombreDia().equalsIgnoreCase(control.getDia())&&
                            platos.get(h).getSemana().getNumeroSemana()== control.getSemana()
                            && platos.get(h).isOpcional()==false){

                        for (int c=0;c<platos.get(h).getItems().size();c++){
                            ingredientesPrincipal += "* "+platos.get(h).getItems().get(c).getComponente().getNombreComponentePlato()+"\n";
                            //ensajeIngredientesPrincipal.setText(platos.get(h).getItems().get(c).getComponente().getNombreComponentePlato().toString()+","+ mensajeIngredientesPrincipal.getText().toString());
                        }

                        mensajeIngredientesPrincipal.setText(ingredientesPrincipal);
                    }
                    else if(platos.get(h).getDia().getNombreDia().equalsIgnoreCase(control.getDia())&&
                            platos.get(h).getSemana().getNumeroSemana()== control.getSemana()
                            && platos.get(h).isOpcional()==true){

                        for (int c=0;c<platos.get(h).getItems().size();c++){
                            ingredientesOpcionales += "* "+platos.get(h).getItems().get(c).getComponente().getNombreComponentePlato()+"\n";
                            //mensajeIngredientesOpcional.setText(platos.get(h).getItems().get(c).getComponente().getNombreComponentePlato().toString()+","+ mensajeIngredientesOpcional.getText().toString());
                        }
                        mensajeIngredientesOpcional.setText(ingredientesOpcionales);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("On Cancelled");
            }
        });

        /*if(mensajeIngredientesPrincipal.getText().equals("")){
            finish();
            Intent refreshActivity = getIntent();
            refreshActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(refreshActivity);
        }*/

    }

}
