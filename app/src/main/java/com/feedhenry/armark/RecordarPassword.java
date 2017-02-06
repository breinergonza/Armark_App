package com.feedhenry.armark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordarPassword extends AppCompatActivity {

    private Button BtnRegresar;
    private TextView TxtCorreo;
    private Button BtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar_password);

        BtnRegresar = (Button) findViewById(R.id.btn_reset_password);
        TxtCorreo = (TextView) findViewById(R.id.email);
        BtnBack = (Button) findViewById(R.id.btn_back);

        BtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TxtCorreo.getText().toString().length() > 0){
                    Toast.makeText(v.getContext(), "La información fue enviada al correo [ " + TxtCorreo.getText().toString() + " ] ", Toast.LENGTH_LONG).show();
                    VolverLimpiar();
                }
            }
        });

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolverLimpiar();
            }
        });
    }

    private void VolverLimpiar(){
        goLogin();
        TxtCorreo.setText("");
    }

    private void goLogin() {
        Intent intent = new Intent(this,Loggin.class);
        startActivity(intent);
    }
}
