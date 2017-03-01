package com.feedhenry.armark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarUsuario extends AppCompatActivity {

    private Button BtnRegistrar;
    private Button BtnVolver;

    private TextView TxtNombre;
    private TextView TxtApellidos;
    private TextView TxtEmail;
    private TextView TxtUsuario;
    private TextView TxtPassword;
    private TextView TxtCelular;

    private View mProgressView;
    private View mLoginFormView;

    private static final String TAG = "";

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.BtnRegistrar));

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        BtnRegistrar = (Button) findViewById(R.id.BtnRegistrar);

        Inicializar();

        BtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Nombre = TxtNombre.getText().toString();
                String Apellidos = TxtApellidos.getText().toString();
                String Celular = TxtCelular.getText().toString();
                String Email = TxtEmail.getText().toString();
                String Password = TxtPassword.getText().toString();

                if (Validacion(Nombre, Apellidos, Celular, Email, Password)) {
                    Registrar(v, Nombre, Apellidos, Celular, Email, Password);
                }

            }
        });
/*
        BtnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });*/
    }

    private void Inicializar() {
        TxtNombre = (TextView) findViewById(R.id.TxtNombres);
        TxtApellidos = (TextView) findViewById(R.id.TxtApellidos);
        TxtCelular = (TextView) findViewById(R.id.TxtTelefono);
        TxtEmail = (TextView) findViewById(R.id.TxtEmail);
        TxtPassword = (TextView) findViewById(R.id.TxtPassword);
        BtnVolver = (Button) findViewById(R.id.btn_back);

        mLoginFormView = findViewById(R.id.registrar_form);
        mProgressView = findViewById(R.id.progressBar);
    }

    private boolean Validacion(String Nombre, String Apellidos, String Celular, String Email, String Password) {

        if (!isValidString(Nombre)) {
            Toast.makeText(this, "Digite su nombre", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidString(Apellidos)) {
            Toast.makeText(this, "Digite su(s) apellido(s)", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidString(Celular)) {
            Toast.makeText(this, "Digite su numero de celular", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidEmail(Email)) {
            Toast.makeText(this, "Digite un Email valido!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(Password)) {
            Toast.makeText(this, "La contraseña debe tener minimo 6 caracteres!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidString(String valor) {
        return !TextUtils.isEmpty(valor);
    }

    private boolean isValidEmail(String Email) {
        return !TextUtils.isEmpty(Email) && android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    private boolean isValidPassword(String Password) {
        return !TextUtils.isEmpty(Password) && Password.length() > 5;
    }

    private void Registrar(final View v, String Nombre, String Apellidos, String Celular, String Email, final String Password) {
        try {

            mProgressView.setVisibility(View.VISIBLE);
            mLoginFormView.setVisibility(View.GONE);

            String[] arrayUser = Email.split("@");
            final String Usuario = arrayUser[0];

            String Data = "{ Nombre: '" + Nombre + "', Apellido: '" + Apellidos + "', Telefono: '" + Celular + "', Email: '" + Email + "', Usuario: '" + Usuario + "',  Contrasena: '" + Password + "', IdFb: '1', IdRol: '1' }";

            org.json.fh.JSONObject params = new org.json.fh.JSONObject(Data);

            FHCloudRequest request = FH.buildCloudRequest("registroUsuario", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");

                    v.setEnabled(true);

                    try {
                        JSONObject obj = new JSONObject(fhResponse.getJson().toString());
                        org.json.JSONObject jsonRespuesta = obj.getJSONObject("Respuesta");
                        String Estado = jsonRespuesta.getString("Estado");
                        if (Estado.equals("OK")) {
                            GuardarPrefences(Usuario, Password);
                            goClasePrincipal();
                        } else if (Estado.equals("ERROR")) {
                            String Mensaje = jsonRespuesta.getString("Mensaje");
                            Toast.makeText(v.getContext(), "Alerta, " + Mensaje, Toast.LENGTH_LONG).show();
                            Limpiar();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), "No se pudo registrar el usuario, error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Limpiar();
                    }

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    Toast.makeText(v.getContext(), "No se registrar el usuario, error: " + fhResponse.getErrorMessage(), Toast.LENGTH_LONG).show();

                    Limpiar();
                }
            });
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "No se pudo registrar el usuario, error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage(), e.getCause());
            Limpiar();
        }

    }

    private void Limpiar() {
        TxtNombre.setText("");
        TxtApellidos.setText("");
        TxtEmail.setText("");
        TxtCelular.setText("");
        TxtPassword.setText("");

        mProgressView.setVisibility(View.GONE);
        mLoginFormView.setVisibility(View.VISIBLE);
    }

    private void goLogin() {
        Intent intent = new Intent(this, Loggin.class);
        startActivity(intent);
    }

    private void GuardarPrefences(String Usuario, String Password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", Usuario);
        editor.putString("password", Password);
        editor.commit();
        editor.apply();
    }

    private void goClasePrincipal() {

        Intent intent = new Intent(this, MainActivity.class);

        // AÑADIMOS banderas que nos permite limpiar el recorrido anterior, cuando presionemos atras no nos devuelve al login.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

        startActivity(intent);

    }

}
