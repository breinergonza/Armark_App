package com.feedhenry.armark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrarUsuario extends AppCompatActivity {

    private Button BtnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        BtnRegresar = (Button) findViewById(R.id.BtnRegresar);

        BtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
    }

    private void goLogin() {
        Intent intent = new Intent(this,Loggin.class);
        startActivity(intent);
    }

}
