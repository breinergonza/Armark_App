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

import com.feedhenry.armark.Adaptadores.Adaptador_Categorias;
import com.feedhenry.armark.R;

import modelo.Contrato;

/**
 * A simple {@link Fragment} subclass.
 */
public class Categorias_fragment extends Fragment implements Adaptador_Categorias.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final String PRODUCTOS = "ARG_PAGE";
    private int mPage;

    private RecyclerView listaUI;
    private LinearLayoutManager linearLayoutManager;
    private Adaptador_Categorias adaptadorCategorias;
    private String idCategoria; //  para recibir el identificadr del torneo

    private static final int LOADER_CATEGORIA = 3;


    public static Categorias_fragment newInstance(int page) {
        // Required empty public constructor
        Categorias_fragment productos_fragment = new Categorias_fragment();
        Bundle args = new Bundle();
        args.putInt(PRODUCTOS,page);
        productos_fragment.setArguments(args);
        return productos_fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PRODUCTOS);
        Log.d("oncreate","resta");
        //idTorneo = (String) getArguments().get(idTorneoFrag);//  pasamos el bundel a string


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catergorias_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listaUI = (RecyclerView)getActivity().findViewById(R.id.my_Recycler_View_Categorias);
        listaUI.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        listaUI.setLayoutManager(linearLayoutManager);

        adaptadorCategorias = new Adaptador_Categorias(getContext(),this);
        adaptadorCategorias.notifyDataSetChanged();
        listaUI.setAdapter(adaptadorCategorias);
        Log.e("error","categorias");
        //getActivity().getSupportLoaderManager().restartLoader(1, null, this);


        getActivity().getSupportLoaderManager().initLoader(LOADER_CATEGORIA,null,this);

       /* Inicio_feedHenry_sdk iniciar = new Inicio_feedHenry_sdk(getContext());
        iniciar.InicializarFH(getContext(),informacion);*/


    }

    @Override
    public void onClick(Adaptador_Categorias.ViewHolder holder, String idCategorias) {

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getSupportLoaderManager().destroyLoader(LOADER_CATEGORIA);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(LOADER_CATEGORIA, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().getSupportLoaderManager().destroyLoader(LOADER_CATEGORIA);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), Contrato.Armark_categorias.URI_CONTENIDO,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adaptadorCategorias != null) {
            adaptadorCategorias.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptadorCategorias.swapCursor(null);
    }
}
