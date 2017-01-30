package com.feedhenry.armark.fragmentos;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feedhenry.armark.R;

import modelo.Contrato;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detalles_almacenes_fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String DETALLE_ALMACEN = "ARG_PAGE";
    public static final String IDALMACEN = "ARG_IDPROMOCION";
    private int mPage;
    private String idalmacen,razonSocial,direccion,correo,telefono,imagen_almacen;
    private static final int LOADER_SUB_ALMACENES =5 ;
    private TextView txt_razonSocial,txt_direccion,txt_correo,txt_telefono;
    private ImageView img_logo_almacen;


    public static Detalles_almacenes_fragment newInstance(int page, String idalmacen) {
        Detalles_almacenes_fragment detalles_almacenes_fragment = new Detalles_almacenes_fragment();
        Bundle args = new Bundle();
        args.putInt(DETALLE_ALMACEN,page);
        args.putString(IDALMACEN,idalmacen);
        detalles_almacenes_fragment.setArguments(args);

        return detalles_almacenes_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(DETALLE_ALMACEN);
        idalmacen = getArguments().getString(IDALMACEN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_almacenes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txt_razonSocial = (TextView)getActivity().findViewById(R.id.txt_razonsocial_almacen);
        txt_direccion = (TextView)getActivity().findViewById(R.id.txt_direccion_almacen);
        txt_correo = (TextView)getActivity().findViewById(R.id.txt_correo_almacen);
        txt_telefono = (TextView)getActivity().findViewById(R.id.txt_telefono_almacen);
        img_logo_almacen = (ImageView)getActivity().findViewById(R.id.img_sub_almacen);

        // Iniciar loader

        getActivity().getSupportLoaderManager().restartLoader(LOADER_SUB_ALMACENES,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String SelPromocion = Contrato.Armark_almacen.IDWEB+"=?";
        String[] arg = new String[]{idalmacen};
        return new CursorLoader(getContext(), Contrato.Armark_almacen.URI_CONTENIDO,null,SelPromocion,arg,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data==null){
            // error  dato nulo
        }else if(data.getCount()<=0){
            // no hay registro
        }
        else {
            data.moveToNext();
            razonSocial = data.getString(2);
            direccion = data.getString(5);
            telefono = data.getString(6);
            correo = data.getString(7);
            imagen_almacen = data.getString(9);


            txt_razonSocial.setText(razonSocial);
            txt_direccion.setText(direccion);
            txt_telefono.setText(telefono);
            txt_correo.setText(correo);
            if(!img_logo_almacen.equals(null)) {
                Glide.with(getActivity()).load(imagen_almacen).centerCrop().into(img_logo_almacen);

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
