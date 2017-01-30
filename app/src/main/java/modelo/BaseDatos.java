package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by ASUS on 20/10/2016.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private static final String name_bd = "Armark3";
    private static final int version = 1;

    public BaseDatos(Context context) {
        super(context, name_bd, null, version);
    }

    public interface Tabla{

        String ALMACEN = "almacen";
        String PROMOCIONES = "promociones";
        String CATEGORIAS = "categorias";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CrearTablaAlmacen(db);
        CrearTablaPromociones(db);
        CrearTablaCategorias(db);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("DROP TABLE IF EXISTS " + Tabla.ALMACEN);
            db.execSQL("DROP TABLE IF EXISTS " + Tabla.PROMOCIONES);

        } catch (SQLiteException e) {
            // Manejo de excepciones
        }
        onCreate(db);
    }
    private void CrearTablaAlmacen(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + Tabla.ALMACEN + "("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Contrato.Armark_almacen.IDWEB + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.RAZONSOCIAL + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.NIT + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.DESCRIPCION + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.DIRECCION + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.TELEFONO + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.CORREO + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.POSICIONGPS + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.LOGO + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.MARCADOR + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.REGISTRO + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.MODIFICADOR + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.VISIBLE + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.ACTIVO + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.TAGS + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.X + " TEXT NOT NULL,"
                        + Contrato.Armark_almacen.Y + " TEXT NOT NULL)");

        // REGISTRO INICIALES

        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_almacen.IDWEB,"1");
        values.put(Contrato.Armark_almacen.RAZONSOCIAL,"El vivito");
        values.put(Contrato.Armark_almacen.NIT,"18425417-1");
        values.put(Contrato.Armark_almacen.DESCRIPCION,"Ingeniero Vivito");
        values.put(Contrato.Armark_almacen.DIRECCION,"CL80 #11b-35");
        values.put(Contrato.Armark_almacen.TELEFONO,"3107078");
        values.put(Contrato.Armark_almacen.CORREO,"culevivo@hotmail.com");
        values.put(Contrato.Armark_almacen.POSICIONGPS,"--");
        values.put(Contrato.Armark_almacen.LOGO,"http://torneofacil.serticiossp.co/imagenes/torneo2.jpg");
        values.put(Contrato.Armark_almacen.MARCADOR,"--");
        values.put(Contrato.Armark_almacen.REGISTRO,"--");
        values.put(Contrato.Armark_almacen.MODIFICADOR,"--");
        values.put(Contrato.Armark_almacen.VISIBLE,"--");
        values.put(Contrato.Armark_almacen.ACTIVO,"--");
        values.put(Contrato.Armark_almacen.TAGS,"--");
        values.put(Contrato.Armark_almacen.X,"--");
        values.put(Contrato.Armark_almacen.Y,"--");
        db.insertOrThrow(Tabla.ALMACEN,null,values);

        values.put(Contrato.Armark_almacen.IDWEB,"1");
        values.put(Contrato.Armark_almacen.RAZONSOCIAL,"El vivito2");
        values.put(Contrato.Armark_almacen.NIT,"5555417-1");
        values.put(Contrato.Armark_almacen.DESCRIPCION,"Ingeniero poseidon");
        values.put(Contrato.Armark_almacen.DIRECCION,"CL80 #11b-35");
        values.put(Contrato.Armark_almacen.TELEFONO,"3107078");
        values.put(Contrato.Armark_almacen.CORREO,"culevivo@hotmail.com");
        values.put(Contrato.Armark_almacen.POSICIONGPS,"--");
        values.put(Contrato.Armark_almacen.LOGO,"http://torneofacil.serticiossp.co/imagenes/torneo2.jpg");
        values.put(Contrato.Armark_almacen.MARCADOR,"--");
        values.put(Contrato.Armark_almacen.REGISTRO,"--");
        values.put(Contrato.Armark_almacen.MODIFICADOR,"--");
        values.put(Contrato.Armark_almacen.VISIBLE,"--");
        values.put(Contrato.Armark_almacen.ACTIVO,"--");
        values.put(Contrato.Armark_almacen.TAGS,"--");
        values.put(Contrato.Armark_almacen.X,"--");
        values.put(Contrato.Armark_almacen.Y,"--");
        db.insertOrThrow(Tabla.ALMACEN,null,values);

    }

    private void CrearTablaPromociones(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Tabla.PROMOCIONES + "("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Contrato.Armark_promociones.IDWEB + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.REFERENCIA + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.NOMBRE + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.DESCRIPCION + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.VALORANTES + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.VALORDESPUES + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.DESCUENTO + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.FECHAINICIO + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.FECHAFIN + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.IMAGEN + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.IDALMACEN + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.IDUSUARIO + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.IDCATEGORIA + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.ACTIVO + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.TAGS + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.X + " TEXT NOT NULL,"
                        + Contrato.Armark_promociones.Y + " TEXT NOT NULL)");

        // REGISTRO INICIALES

        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_promociones.IDWEB,"15");
        values.put(Contrato.Armark_promociones.REFERENCIA,"El vivito");
        values.put(Contrato.Armark_promociones.NOMBRE,"CAMISAS");
        values.put(Contrato.Armark_promociones.DESCRIPCION,"BLANCAS");
        values.put(Contrato.Armark_promociones.VALORANTES,"89.000");
        values.put(Contrato.Armark_promociones.VALORDESPUES,"80.100");
        values.put(Contrato.Armark_promociones.DESCUENTO,"10%");
        values.put(Contrato.Armark_promociones.FECHAINICIO,"--");
        values.put(Contrato.Armark_promociones.FECHAFIN,"--");
        values.put(Contrato.Armark_promociones.IMAGEN,"http://torneofacil.serticiossp.co/imagenes/torneo2.jpg");
        values.put(Contrato.Armark_promociones.IDALMACEN,"1");
        values.put(Contrato.Armark_promociones.IDUSUARIO,"--");
        values.put(Contrato.Armark_promociones.IDCATEGORIA,"--");
        values.put(Contrato.Armark_promociones.ACTIVO,"--");
        values.put(Contrato.Armark_promociones.TAGS,"--");
        values.put(Contrato.Armark_promociones.X,"--");
        values.put(Contrato.Armark_promociones.Y,"--");
        db.insertOrThrow(Tabla.PROMOCIONES,null,values);
    }

    private void CrearTablaCategorias(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + Tabla.CATEGORIAS + "("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Contrato.Armark_categorias.IDWEB + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.NOMBRE + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.IDCATEGORIAS + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.IMAGEN + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.REGISTRO + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.MODIFICACION + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.VISIBLE + " TEXT NOT NULL,"
                        + Contrato.Armark_categorias.ACTIVO + " TEXT NOT NULL)");

        // REGISTRO INICIALES

        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_categorias.IDWEB,"1");
        values.put(Contrato.Armark_categorias.NOMBRE,"El vivito");
        values.put(Contrato.Armark_categorias.IDCATEGORIAS,"null");
        values.put(Contrato.Armark_categorias.IMAGEN,"http://torneofacil.serticiossp.co/imagenes/torneo2.jpg");
        values.put(Contrato.Armark_categorias.REGISTRO,"--");
        values.put(Contrato.Armark_categorias.MODIFICACION,"--");
        values.put(Contrato.Armark_categorias.VISIBLE,"null");
        values.put(Contrato.Armark_categorias.ACTIVO,"false");

        db.insertOrThrow(Tabla.CATEGORIAS,null,values);
    }
}
