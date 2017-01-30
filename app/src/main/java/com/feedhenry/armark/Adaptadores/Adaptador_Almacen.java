package com.feedhenry.armark.Adaptadores;

import android.content.Context;
import android.content.Intent;
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
import com.feedhenry.armark.Sub_menus.Sub_menu_almacenes;

/**
 * Created by ASUS on 20/10/2016.
 */
public class Adaptador_Almacen extends RecyclerView.Adapter<Adaptador_Almacen.ViewHolder> {

    private final Context contexto;
    private Cursor items;
    public OnItemClickListener escuchaAlmacen;

    public interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlmacen);
    }

    public Adaptador_Almacen(Context contexto,OnItemClickListener escuchaAlmacen) {
        this.contexto = contexto;
        this.escuchaAlmacen = escuchaAlmacen;
    }


    @Override
    public Adaptador_Almacen.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_almacen,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Adaptador_Almacen.ViewHolder holder, int position) {
        items.moveToPosition(position);
        String s;

        // asignacion de ui
        s= items.getString(ConsultaAlmacen.RAZONSOCIAL);
        holder.viewRazonSocial.setText(s);

        s= items.getString(ConsultaAlmacen.DESCRIPCION);
        holder.viewDescripcion.setText(s);

        s= items.getString(ConsultaAlmacen.LOGO);
        Glide.with(contexto).load(s).centerCrop().into(holder.viewLogo);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Tomamos las referencias ui
        private TextView viewRazonSocial,viewDescripcion;
        private ImageView viewLogo;
        public LinearLayout linearLayout_button_almacen;
        public Boolean flag_control = false;
        public Button btnDetalles,btnPromociones,btnCategorias;
        public CardView cardView_almacenes;

        public ViewHolder(final View itemView) {
            super(itemView);

            viewRazonSocial = (TextView) itemView.findViewById(R.id.txt_razonSocial);
            viewDescripcion = (TextView)itemView.findViewById(R.id.txt_descripcion_almacen);
            viewLogo = (ImageView)itemView.findViewById(R.id.img_almcen);
            btnDetalles = (Button)itemView.findViewById(R.id.btndetalles);
            btnPromociones = (Button)itemView.findViewById(R.id.btnpromociones);
            btnCategorias = (Button)itemView.findViewById(R.id.btncategorias);
            cardView_almacenes = (CardView)itemView.findViewById(R.id.cardview_Almacenes);
            linearLayout_button_almacen = (LinearLayout)itemView.findViewById(R.id.layout_button_almacenes);
            itemView.setOnClickListener(this);


            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Sub_menu_almacenes.class);
                    intent.putExtra("idalmacen",obtenerIdAlmacen(getAdapterPosition()));
                    intent.putExtra("varcontrol","ALMACEN");
                    intent.putExtra("idwebalmacenes",obtenerIdWebAlmacenes(getAdapterPosition()));
                    contexto.startActivity(intent);

                }
            });
            btnPromociones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Sub_menu_almacenes.class);
                    intent.putExtra("idalmacen",obtenerIdAlmacen(getAdapterPosition()));
                    intent.putExtra("varcontrol","PROMOCIONES");
                    intent.putExtra("idwebalmacenes",obtenerIdWebAlmacenes(getAdapterPosition()));
                    contexto.startActivity(intent);
                }
            });
            btnCategorias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Sub_menu_almacenes.class);
                    intent.putExtra("idalmacen",obtenerIdAlmacen(getAdapterPosition()));
                    intent.putExtra("varcontrol","CATEGORIAS");
                    intent.putExtra("idwebalmacenes",obtenerIdWebAlmacenes(getAdapterPosition()));
                    contexto.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            escuchaAlmacen.onClick(this,obtenerIdAlmacen(getAdapterPosition()));

            // preguntamos por la bandera de control , saber si esta mostrando el menu editar y eliminar o no
            if (!flag_control){                                                       // si la bandera es false  ( NO se ha presionado boton)
                Animation desplaza = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar); // identificamos la animacion desplzar a la  derecha
                flag_control = true;                                                // invertimos bandera, se presiono
                viewRazonSocial.setText(items.getString(ConsultaAlmacen.RAZONSOCIAL));  // no se por que tengo que hacer esto para que funcione la animacion. jajajajaja  luego averiguo //// TODO: 28/10/2016
                desplaza.setFillAfter(true);                                       //accion para que la animacion no se restablezca
                cardView_almacenes.setAnimation(desplaza);                            //iniciamos animacion  ir a la derecha cardview
                linearLayout_button_almacen.setVisibility(View.VISIBLE);     // colocamo visibles los botones editar y eliminar
                linearLayout_button_almacen.setClickable(true);            //  activamos que puedas darle click


            }
            else {                                                          //  si la bandera esta true,  boton esta presionado
                Animation desplaza_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar_back);  // identificamos animacion ir a la izquierda
                Animation alpha_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.alpha_back);  // identificamos animacion desaparcer
                linearLayout_button_almacen.setClickable(false);                           //  desabilitamos que se pueda dar click
                linearLayout_button_almacen.setAnimation(alpha_back);                      // iniciamos animacion desaparecer de botones editar y eliminar
                flag_control= false;                                                    //  colcoamos control en false ,
                cardView_almacenes.setAnimation(desplaza_back);                                   //iniciamos animacion para ir atras del cardview
                linearLayout_button_almacen.setVisibility(View.INVISIBLE);                 // colocamos invisible  los botones editar y eliminar
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // ingresa aqui cuando un elemento del recycler view esta mostrandose
        holder.linearLayout_button_almacen.setVisibility(View.INVISIBLE);
        holder.linearLayout_button_almacen.setClickable(false);
        holder.cardView_almacenes.clearAnimation();
        holder.flag_control= false;
    }

    private String obtenerIdAlmacen(int adapterPosition) {
        if (items != null) {
            if (items.moveToPosition(adapterPosition)) {
                return items.getString(ConsultaAlmacen.ID_ALMACEN);
            }
        }

        return null;
    }
    private String obtenerIdWebAlmacenes(int adapterPosition) {
        if (items != null) {
            if (items.moveToPosition(adapterPosition)) {
                return items.getString(ConsultaAlmacen.IDWEB);
            }
        }

        return null;
    }

    interface ConsultaAlmacen {
        int ID_ALMACEN = 0;
        int IDWEB = 1;
        int RAZONSOCIAL = 2;
        int NIT = 3;
        int DESCRIPCION = 4;
        int DIRECCION = 5;
        int TELEFONO = 6;
        int CORREO= 7;
        int POSICIONGPS = 8;
        int LOGO = 9;
        int MARCADOR = 10;
        int REGISTRO = 11;
        int MODIFICADOR = 12;
        int VISIBLE = 13;
        int ACTIVO = 14;
        int TAGS = 15;
        int X = 16;
        int Y = 17;
    }
}
