package com.feedhenry.armark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class ArAlmacenActivity extends AppCompatActivity {

    private ArchitectView av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_almacen);

        av = (ArchitectView) this.findViewById(R.id.architectView);

        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(Constantes.WIKITUDE_SDK_KEY);

        av.onCreate( config );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        av.onPostCreate();

        try {
            this.av.load( "file:///android_asset/Imagen/index.html" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        av.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        av.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        av.onPause();
    }
}
