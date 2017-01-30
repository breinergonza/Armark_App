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
public class Detalle_promociones_fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String DETALLE_PROMOCIONES = "ARG_PAGE";
    public static final String IDPROMOCION = "ARG_IDPROMOCION";
    private int mPage;
    private ImageView img_sub_promocion;
    private TextView txt_referencia_promo,txt_descuento,txt_valorAntes,txt_valorDespues,txt_fechaInicial,txt_fechaFinal;
    private String imagen,referencia,valorAntes,valorDespues,descuento,fechaInicial,fechaFinal;
    private String idpromocion;


    private static final int LOADER_SUB_PROMOCIONES =4 ;

    public static Detalle_promociones_fragment newInstance(int page,String idpromocion) {
        Detalle_promociones_fragment detalle_promociones_fragment = new Detalle_promociones_fragment();
        Bundle args = new Bundle();
        args.putInt(DETALLE_PROMOCIONES,page);
        args.putString(IDPROMOCION,idpromocion);
        detalle_promociones_fragment.setArguments(args);

        return detalle_promociones_fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(DETALLE_PROMOCIONES);
        idpromocion = getArguments().getString(IDPROMOCION);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_promociones_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        img_sub_promocion = (ImageView)getActivity().findViewById(R.id.img_sub_promocion);
        txt_referencia_promo = (TextView)getActivity().findViewById(R.id.txt_referencia_promocion);
        txt_valorAntes = (TextView)getActivity().findViewById(R.id.txt_valor_antes);
        txt_valorDespues = (TextView)getActivity().findViewById(R.id.txt_valor_despues);
        txt_fechaInicial = (TextView)getActivity().findViewById(R.id.txt_fecha_inicio);
        txt_fechaFinal = (TextView)getActivity().findViewById(R.id.txt_fecha_final);
        txt_descuento = (TextView)getActivity().findViewById(R.id.txt_descuento_promocion);
        // Iniciar loader

        getActivity().getSupportLoaderManager().restartLoader(LOADER_SUB_PROMOCIONES,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String SelPromocion = "_ID"+"=?";
        String[] arg = new String[]{idpromocion};
        return new CursorLoader(getContext(), Contrato.Armark_promociones.URI_CONTENIDO,null,SelPromocion,arg,null);
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

                referencia = data.getString(2);
                valorAntes = data.getString(5);
                valorDespues = data.getString(6);
                descuento = data.getString(7);
                fechaInicial = data.getString(8);
                fechaFinal = data.getString(9);
                imagen = data.getString(10);

                txt_valorAntes.setText(valorAntes);
                txt_valorDespues.setText(valorDespues);
                txt_fechaInicial.setText(fechaInicial);
                txt_fechaFinal.setText(fechaFinal);
                txt_descuento.setText(descuento);
                txt_referencia_promo.setText(referencia);
                if(!imagen.equals(null)){
                    Glide.with(getActivity()).load(imagen).centerCrop().into(img_sub_promocion);
                }


            }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
