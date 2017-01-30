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
import com.feedhenry.armark.Sub_menus.Sub_menu_promociones;

/**
 * Created by ASUS on 24/10/2016.
 */
public class Adaptadores_Promociones extends RecyclerView.Adapter<Adaptadores_Promociones.ViewHolder> {

    private final Context contexto;
    private Cursor items;
    public OnItemClickListener escuchaPromociones;

    public Adaptadores_Promociones(Context contexto, OnItemClickListener escuchaPromociones) {
        this.contexto = contexto;
        this.escuchaPromociones = escuchaPromociones;
    }

    public interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idPromociones);
    }

    @Override
    public Adaptadores_Promociones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_promociones,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adaptadores_Promociones.ViewHolder holder, int position) {

        items.moveToPosition(position);
        String s;

        // asignacion de ui
        s= items.getString(ConsultaPromociones.NOMBRE);
        holder.txt_nombrePromo.setText(s);

        s= items.getString(ConsultaPromociones.DESCRIPCION);
        holder.txt_descrpcionPromo.setText(s);

        s= items.getString(ConsultaPromociones.DESCUENTO);
        holder.txt_descuentoPromo.setText(s);

        s= items.getString(ConsultaPromociones.IMAGEN);
        Glide.with(contexto).load(s).centerCrop().into(holder.img_promociones);

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

        // tomamos referencia de ui
        private ImageView img_promociones;
        private TextView txt_nombrePromo,txt_descrpcionPromo,txt_descuentoPromo;
        public LinearLayout linearLayout_button_promociones;
        public Boolean flag_control = false;
        public Button btnDetalles,btnAlmacen;
        public CardView cardView_promociones;

        public ViewHolder(final View itemView) {
            super(itemView);

            img_promociones = (ImageView)itemView.findViewById(R.id.img_promociones);
            txt_nombrePromo = (TextView)itemView.findViewById(R.id.txt_nombre_promociones);
            txt_descrpcionPromo = (TextView)itemView.findViewById(R.id.txt_descripcion_promociones);
            txt_descuentoPromo = (TextView)itemView.findViewById(R.id.txt_descuentoPromociones);
            btnDetalles = (Button)itemView.findViewById(R.id.btndetallesPromociones);
            btnAlmacen = (Button)itemView.findViewById(R.id.btnalmacenPromociones);
            cardView_promociones = (CardView)itemView.findViewById(R.id.cardview_Promociones);
            linearLayout_button_promociones = (LinearLayout)itemView.findViewById(R.id.layout_button_promociones);

            //  colocamos escuchadores de los sub menus

            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),Sub_menu_promociones.class);
                    //intent.putExtra("identificador", obtenerIdPromociones(getAdapterPosition()));
                    intent.putExtra("idpromociones",obtenerIdPromociones(getAdapterPosition()));
                    intent.putExtra("varcontrol","PROMOCIONES");
                    intent.putExtra("idalmacen",obtenerIdAlmacen_promocion(getAdapterPosition()));
                    contexto.startActivity(intent);
                }
            });

            btnAlmacen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),Sub_menu_promociones.class);
                    //intent.putExtra("identificador", obtenerIdPromociones(getAdapterPosition()));
                    intent.putExtra("idpromociones",obtenerIdPromociones(getAdapterPosition()));
                    intent.putExtra("varcontrol","ALMACENES");
                    intent.putExtra("idalmacen",obtenerIdAlmacen_promocion(getAdapterPosition()));
                    contexto.startActivity(intent);
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaPromociones.onClick(this,obtenerIdPromociones(getAdapterPosition()));
            // preguntamos por la bandera de control , saber si esta mostrando el menu editar y eliminar o no
            if (!flag_control){                                                       // si la bandera es false  ( NO se ha presionado boton)
                Animation desplaza = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar); // identificamos la animacion desplzar a la  derecha
                flag_control = true;                                                // invertimos bandera, se presiono
                txt_nombrePromo.setText(items.getString(ConsultaPromociones.NOMBRE));  // no se por que tengo que hacer esto para que funcione la animacion. jajajajaja  luego averiguo //// TODO: 28/10/2016
                desplaza.setFillAfter(true);                                       //accion para que la animacion no se restablezca
                cardView_promociones.setAnimation(desplaza);                            //iniciamos animacion  ir a la derecha cardview
                linearLayout_button_promociones.setVisibility(View.VISIBLE);     // colocamo visibles los botones editar y eliminar
                linearLayout_button_promociones.setClickable(true);            //  activamos que puedas darle click


            }
            else {                                                          //  si la bandera esta true,  boton esta presionado
                Animation desplaza_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.deslizar_back);  // identificamos animacion ir a la izquierda
                Animation alpha_back = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.alpha_back);  // identificamos animacion desaparcer
                linearLayout_button_promociones.setClickable(false);                           //  desabilitamos que se pueda dar click
                linearLayout_button_promociones.setAnimation(alpha_back);                      // iniciamos animacion desaparecer de botones editar y eliminar
                flag_control= false;                                                    //  colcoamos control en false ,
                cardView_promociones.setAnimation(desplaza_back);                                   //iniciamos animacion para ir atras del cardview
                linearLayout_button_promociones.setVisibility(View.INVISIBLE);                 // colocamos invisible  los botones editar y eliminar
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // ingresa aqui cuando un elemento del recycler view esta mostrandose
        holder.linearLayout_button_promociones.setVisibility(View.INVISIBLE);
        holder.linearLayout_button_promociones.setClickable(false);
        holder.cardView_promociones.clearAnimation();
        holder.flag_control= false;
    }

    private String obtenerIdPromociones(int adapterPosition) {
        if (items != null) {
            if (items.moveToPosition(adapterPosition)) {
                return items.getString(ConsultaPromociones.ID_PROMOCIONES);
            }
        }

        return null;
    }

    private String obtenerIdAlmacen_promocion(int adapterPosition) {
        if (items != null) {
            if (items.moveToPosition(adapterPosition)) {
                return items.getString(ConsultaPromociones.IDALMACEN);
            }
        }

        return null;
    }

    interface ConsultaPromociones {
        int ID_PROMOCIONES = 0;
        int IDWEB = 1;
        int REFERENCIA = 2;
        int NOMBRE = 3;
        int DESCRIPCION = 4;
        int VALORANTES = 5;
        int VALORDESPUES = 6;
        int DESCUENTO= 7;
        int FECHAINICIO = 8;
        int FECHAFIN = 9;
        int IMAGEN = 10;
        int IDALMACEN = 11;
        int IDUSUARIO = 12;
        int IDCATEGORIA = 13;
        int ACTIVO = 14;
        int TAGS = 15;
        int X = 16;
        int Y = 17;
    }
}
