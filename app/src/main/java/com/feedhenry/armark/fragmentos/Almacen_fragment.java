package com.feedhenry.armark.fragmentos;


import android.content.Context;
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

import com.feedhenry.armark.Adaptadores.Adaptador_Almacen;
import com.feedhenry.armark.R;

import modelo.Contrato;


/**
 * A simple {@link Fragment} subclass.
 */
public class Almacen_fragment extends Fragment implements Adaptador_Almacen.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{



    public static final String ALMACEN = "ARG_PAGE";
    public static final String IDALMACEN = "ARG_PAGE";//  recibimos eL ID DEL ALMACEN , SI LO HAY
    public static final String VARCONTROL = "ARG_PAGE";//  recibimos la variable control, sabemos que intenet nos llama
    private int mPage;
    private RecyclerView listaUI;
    private LinearLayoutManager linearLayoutManager;
    private Adaptador_Almacen adaptadorAlmacen;
    private String idalmacen,varControl; // capturar datos de los bundel

    private static final int LOADER_ALMACEN = 2;

    Context context;

    public static Almacen_fragment newInstance(int page) {
        // Required empty public constructor
        Almacen_fragment almacen_fragment = new Almacen_fragment();
        Bundle args = new Bundle();
        args.putInt(ALMACEN,page);
       // args.putString(IDALMACEN,idalmacen);
       // args.putString(VARCONTROL,varControl);
        almacen_fragment.setArguments(args);

        return almacen_fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ALMACEN);
       // idalmacen = getArguments().getString(IDALMACEN);
       // varControl = getArguments().getString(VARCONTROL);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_almacen_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listaUI = (RecyclerView)getActivity().findViewById(R.id.my_Recycler_View_Almacenes);
        listaUI.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        listaUI.setLayoutManager(linearLayoutManager);

        adaptadorAlmacen = new Adaptador_Almacen(getContext(),this);
        adaptadorAlmacen.notifyDataSetChanged();
        listaUI.setAdapter(adaptadorAlmacen);
        Log.e("error","almacen");
        //getActivity().getSupportLoaderManager().restartLoader(1, null, this);


        getActivity().getSupportLoaderManager().initLoader(LOADER_ALMACEN,null,this);

       /* Inicio_feedHenry_sdk iniciar = new Inicio_feedHenry_sdk(getContext());
        iniciar.InicializarFH(getContext(),informacion);*/


    }

    @Override
    public void onClick(Adaptador_Almacen.ViewHolder holder, String idAlmacen) {

    }

  @Override
    public void onPause() {
        super.onPause();
        getActivity().getSupportLoaderManager().destroyLoader(LOADER_ALMACEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(LOADER_ALMACEN, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().getSupportLoaderManager().destroyLoader(LOADER_ALMACEN);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {



        return new CursorLoader(getContext(), Contrato.Armark_almacen.URI_CONTENIDO,null,null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adaptadorAlmacen != null) {
            adaptadorAlmacen.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptadorAlmacen.swapCursor(null);
    }
}
