package com.feedhenry.armark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Loggin extends AppCompatActivity {

    public CallbackManager callbackManager;
    public LoginButton loginButtonFacebook;
    private Button BtnIniciarSesion;
    private EditText TxtUsuario;
    private EditText TxtPassword;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        InicializarComponentes();

        BtnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Usuario = TxtUsuario.getText().toString();
                String Password = TxtPassword.getText().toString();

                if(Validacion(Usuario,Password)){
                    GuardarPrefences(Usuario,Password);
                    goClasePrincipal();
                }
            }
        });

        login_con_facebook();
    }

    private void InicializarComponentes() {
        loginButtonFacebook = (LoginButton)findViewById(R.id.btn_login_facebook);
        BtnIniciarSesion = (Button) findViewById(R.id.BtnLogin);
        TxtUsuario = (EditText) findViewById(R.id.TxtUsuario);
        TxtPassword = (EditText) findViewById(R.id.TxtPassword);
    }

    private boolean Validacion(String Usuario, String Password) {

        if (!isValidUsuario(Usuario)) {
            Toast.makeText(this, "Digite su usuario", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(Password)) {
            Toast.makeText(this, "La contraseña debe tener minimo 6 caracteres!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidUsuario(String Usuario){
        return !TextUtils.isEmpty(Usuario);
    }

    private boolean isValidPassword(String Password){
        return !TextUtils.isEmpty(Password) && Password.length() > 5;
    }

    private void GuardarPrefences(String Usuario, String Password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", Usuario);
        editor.putString("password", Password);
        editor.commit();
        editor.apply();
    }

    private void login_con_facebook() {

        //************************************ Pasos para loggin por facebook ***********************************************************************
        //  Creamos el Manejador de facebook
        callbackManager = CallbackManager.Factory.create();

        // Solicitamos los permisos a facebook de los datos que vamos a requerir , aparte de los que nos ofrece
        loginButtonFacebook.setReadPermissions(Arrays.asList("email"));

        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("response",response.toString());


                                try {
                                    String email = object.getString("email");
                                    String nombre = object.getString("name");
                                    Log.d("email",email);
                                    Log.d("nomb",nombre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //  si es exitoso lo solicitado , nos redireccionamos a la clase principal (contenedor Principal)
                               goClasePrincipal();
                            }
                        }
                );

                //  estos son los parametros que estamos solicitando,  fields es necesario que lo lleve como primer parametro
                Bundle parametros = new Bundle();
                parametros.putString("fields","name,email");
                request.setParameters(parametros);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                View view = new View(getApplicationContext());
                Snackbar.make(view, "Cancelado inicio Sesión", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }

            @Override
            public void onError(FacebookException error) {
                View view = new View(getApplicationContext());
                Snackbar.make(view, "Error de inicio Sesión", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });

        //************************************FIN Pasos para loggin por facebook ********************************************************************

    }

    private void goClasePrincipal() {

        Intent intent = new Intent(this,MainActivity.class);

        // AÑADIMOS banderas que nos permite limpiar el recorrido anterior, cuando presionemos atras no nos devuelve al login.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

        /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

        startActivity(intent);

    }
    // Necesitamos generar el onactivityResult para recibir los datos del Callbackmanager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void RegistrarUsuario(View view) {
        startActivity(new Intent(this,RegistrarUsuario.class));
    }

    public void RecordarContraseña(View view) {
        startActivity(new Intent(this,RecordarPassword.class));
    }
}
