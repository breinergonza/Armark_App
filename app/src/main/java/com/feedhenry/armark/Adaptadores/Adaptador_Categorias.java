package com.feedhenry.armark.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feedhenry.armark.R;

/**
 * Created by ASUS on 24/10/2016.
 */
public class Adaptador_Categorias extends RecyclerView.Adapter<Adaptador_Categorias.ViewHolder> {

    private final Context contexto;
    private Cursor items;
    public OnItemClickListener escuchaCategorias;

    public Adaptador_Categorias(Context contexto, OnItemClickListener escuchaCategorias) {
        this.contexto = contexto;
        this.escuchaCategorias = escuchaCategorias;
    }

    public interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idCategorias);
    }

    @Override
    public Adaptador_Categorias.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_categorias,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adaptador_Categorias.ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // asignacion de ui
        s= items.getString(ConsultaCategorias.NOMBRE);
        holder.txt_nombreCategoria.setText(s);

        s= items.getString(ConsultaCategorias.IMAGEN);
        Glide.with(contexto).load(s).centerCrop().into(holder.img_categorias);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.getCount();
        }
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }
    public Cursor getCursor() {
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img_categorias;
        private TextView txt_nombreCategoria;
        public LinearLayout linearLayout_button_categorias;
        public Boolean flag_control = false;
        public Button btnAlmacenesCategorias,btnPromocionesCategorias;
        public CardView cardView_categorias;

        public ViewHolder(View itemView) {
            super(itemView);

            img_categorias = (ImageView)itemView.findViewById(R.id.img_categorias);
            txt_nombreCategoria = (TextView)itemView.findViewById(R.id.txt_nombre_categoria);

            btnAlmacenesCategorias = (Button)itemView.findViewById(R.id.btnalmacencategorias);
            btnPromocionesCategorias = (Button)itemView.findViewById(R.id.btnpromocionescategorias);
            cardView_categorias = (CardView)itemView.findViewById(R.id.cardview_Categorias);
            linearLayout_button_categorias = (LinearLayout)itemView.findViewById(R.id.layout_button_categorias);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaCategorias.onClick(this,obtenerIdCategoria(getAdapterPosition()));
            // preguntamos por la bandera de control , saber si esta mostrando el menu editar y eliminar o no
            if (!flag_control){                                                       // si la bandera es false  ( NO se ha presionado boton)
                Animation desplaza = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar); // identificamos la animacion desplzar a la  derecha
                flag_control = true;                                                // invertimos bandera, se presiono
                txt_nombreCategoria.setText(items.getString(ConsultaCategorias.NOMBRE));  // no se por que tengo que hacer esto para que funcione la animacion. jajajajaja  luego averiguo //// TODO: 28/10/2016
                desplaza.setFillAfter(true);                                       //accion para que la animacion no se restablezca
                cardView_categorias.setAnimation(desplaza);                            //iniciamos animacion  ir a la derecha cardview
                linearLayout_button_categorias.setVisibility(View.VISIBLE);     // colocamo visibles los botones editar y eliminar
                linearLayout_button_categorias.setClickable(true);            //  activamos que puedas darle click


            }
            else {                                                          //  si la bandera esta true,  boton esta presionado
                Animation desplaza_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar_back);  // identificamos animacion ir a la izquierda
                Animation alpha_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.alpha_back);  // identificamos animacion desaparcer
                linearLayout_button_categorias.setClickable(false);                           //  desabilitamos que se pueda dar click
                linearLayout_button_categorias.setAnimation(alpha_back);                      // iniciamos animacion desaparecer de botones editar y eliminar
                flag_control= false;                                                    //  colcoamos control en false ,
                cardView_categorias.setAnimation(desplaza_back);                                   //iniciamos animacion para ir atras del cardview
                linearLayout_button_categorias.setVisibility(View.INVISIBLE);                 // colocamos invisible  los botones editar y eliminar
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // ingresa aqui cuando un elemento del recycler view esta mostrandose
        holder.linearLayout_button_categorias.setVisibility(View.INVISIBLE);
        holder.linearLayout_button_categorias.setClickable(false);
        holder.cardView_categorias.clearAnimation();
        holder.flag_control= false;
    }

    private String obtenerIdCategoria(int adapterPosition) {

        if (items != null) {
            if (items.moveToPosition(adapterPosition)) {
                return items.getString(ConsultaCategorias.ID_CATEGORIAS);
            }
        }

        return null;
    }

    interface ConsultaCategorias {
        int ID_CATEGORIAS = 0;
        int IDWEB = 1;
        int NOMBRE = 2;
        int IDCATEGORIA = 3;
        int IMAGEN = 4;
        int REGISTRO = 5;
        int MODIFICACION = 6;
        int VISIBLE= 7;
        int ACTIVO = 8;

    }
}
