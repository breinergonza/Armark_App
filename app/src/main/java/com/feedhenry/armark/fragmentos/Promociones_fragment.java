package com.feedhenry.armark.fragmentos;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feedhenry.armark.Adaptadores.Adaptadores_Promociones;
import com.feedhenry.armark.R;

import modelo.Contrato;


/**
 * A simple {@link Fragment} subclass.
 */
public class Promociones_fragment extends Fragment implements Adaptadores_Promociones.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    public static final String PROMOCIONES = "ARG_PAGE";
    public static final String VARCONTROL = "ARG_VARCONTROL";
    public static final String IDWEBALMACENES = "ARG_IDWEBALMACEB";
    private int mPage;

    private RecyclerView listaUI;
    private LinearLayoutManager linearLayoutManager;
    private Adaptadores_Promociones adaptadorPromociones;
    private String idwebalmacenes,varcontrol; //

    private static final int LOADER_PROMOCIONES = 1;

    public static Promociones_fragment newInstance(int page,String varcontrol2,String idwebalmacenes) {
        // Required empty public constructor
        Promociones_fragment promociones_fragment = new Promociones_fragment();
        Bundle args = new Bundle();
        args.putInt(PROMOCIONES,page);
        args.putString(VARCONTROL,varcontrol2);

        args.putString(IDWEBALMACENES,idwebalmacenes);
        promociones_fragment.setArguments(args);
        return promociones_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PROMOCIONES);
        idwebalmacenes = getArguments().getString(IDWEBALMACENES);
        varcontrol = getArguments().getString(VARCONTROL);

        //idPromociones = (String) getArguments().get(idPromocionesFrag);//  pasamos el bundel a string


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promociones_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listaUI = (RecyclerView)getActivity().findViewById(R.id.my_Recycler_View_Promociones);
        listaUI.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        listaUI.setLayoutManager(linearLayoutManager);

        adaptadorPromociones = new Adaptadores_Promociones(getContext(),this);
        adaptadorPromociones.notifyDataSetChanged();
        listaUI.setAdapter(adaptadorPromociones);
        Log.e("error","promociones");

        getActivity().getSupportLoaderManager().initLoader(LOADER_PROMOCIONES,null,this);


    }


    @Override
    public void onClick(Adaptadores_Promociones.ViewHolder holder, String idPromociones) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(varcontrol!=null && varcontrol.equals("ALMACEN")){
            String SelPromocion = Contrato.Armark_promociones.IDALMACEN+"=?";
            String[] arg = new String[]{idwebalmacenes};
            return new CursorLoader(getContext(), Contrato.Armark_promociones.URI_CONTENIDO,null,SelPromocion,arg,null);
        }
        return new CursorLoader(getContext(), Contrato.Armark_promociones.URI_CONTENIDO,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adaptadorPromociones != null) {
            adaptadorPromociones.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptadorPromociones.swapCursor(null);
    }
}
