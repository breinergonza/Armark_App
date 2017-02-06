package com.feedhenry.armark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarUsuario extends AppCompatActivity {

    private Button BtnRegistrar;

    private TextView TxtNombre;
    private TextView TxtApellidos;
    private TextView TxtEmail;
    private TextView TxtUsuario;
    private TextView TxtPassword;

    private View mProgressView;
    private View mLoginFormView;

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        BtnRegistrar = (Button) findViewById(R.id.BtnRegistrar);

        Inicializar();

        BtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
    }

    private void Inicializar(){
        TxtNombre = (TextView) findViewById(R.id.TxtNombres);
        TxtApellidos = (TextView) findViewById(R.id.TxtApellidos);
        TxtEmail = (TextView) findViewById(R.id.TxtEmail);
        TxtUsuario = (TextView) findViewById(R.id.TxtUsuario);
        TxtPassword = (TextView) findViewById(R.id.TxtPassword);

        mLoginFormView = findViewById(R.id.registrar_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void Registrar(){
        try {

            mProgressView.setVisibility(View.VISIBLE);
            mLoginFormView.setVisibility(View.GONE);

            String Nombre = TxtNombre.getText().toString();
            String Apellidos = TxtApellidos.getText().toString();
            String Email = TxtEmail.getText().toString();
            String Usuario = TxtUsuario.getText().toString();
            String Password = TxtPassword.getText().toString();

            /*
            {
  "Nombre": "Testing",
  "Apellido": "Testing",
  "Sexo": 1,
  "Direccion": "Cll 16",
  "Telefono": "1122",
  "Email": "b@g.com",
  "Usuario": "bgonza",
  "FechaNac": "1980-12-11",
  "Contrasena": "",
  "IdFb": "1",
  "IdRol": 1,
  "Registro": "2016-11-03T16:08:21.9214246+00:00"
}
            * */

            org.json.fh.JSONObject params = new org.json.fh.JSONObject("{correo: '"+ Usuario +"', password: '"+ Password +"' }");

            FHCloudRequest request = FH.buildCloudRequest("registroUsuario", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");

                    //v.setEnabled(true);

                    try {
                        JSONObject obj = new JSONObject(fhResponse.getJson().toString());

                        org.json.JSONObject jsonRespuesta = obj.getJSONObject("Respuesta");

                        String Estado =jsonRespuesta.getString("Estado");

                        if(Estado.equals("OK")){
                            /*GuardarPrefences(Usuario,Password);
                            goClasePrincipal();*/
                        }else{
                            /*
                            Toast.makeText(v.getContext(), "Correo o contraseña incorrectos!", Toast.LENGTH_LONG).show();
                            */
                            Limpiar();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        /*
                        Toast.makeText(v.getContext(), "No se pudo iniciar sesión, error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        */
                        Limpiar();
                    }

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    /* v.setEnabled(true);
                    Toast.makeText(v.getContext(), "No se pudo iniciar sesión, error: " + fhResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                    */
                    Limpiar();
                }
            });
        } catch (Exception e) {
            /*
            Toast.makeText(v.getContext(), "No se pudo iniciar sesión, error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            */
            Log.e(TAG, e.getMessage(), e.getCause());
            Limpiar();
        }

    }

    private void Limpiar(){
        TxtNombre.setText("");
        TxtApellidos.setText("");
        TxtEmail.setText("");
        TxtUsuario.setText("");
        TxtPassword.setText("");
    }

    private void goLogin() {
        Intent intent = new Intent(this,Loggin.class);
        startActivity(intent);
    }

}
