package com.feedhenry.armark;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.fh.JSONArray;
import org.json.fh.JSONObject;

import modelo.Contrato;

/**
 * Created by ASUS on 22/10/2016.
 */
public class CloudCall_Post {


    int IDWEB ;
    public String RAZONSOCIAL,NIT,DESCRIPCION,DIRECCION,TELEFONO,CORREO,POSICIONGPS,LOGO ,
            MARCADOR,REGISTRO,MODIFICADOR,VISIBLE,ACTIVO,TAGS,X,Y;

    public String REFERENCIA,NOMBRE,VALORANTES,VALORDESPUES,DESCUENTO,
                    FECHAINICIO,FECHAFIN,IMAGEN,IDALMACEN,IDUSUARIO,IDCATEGORIA;

    public String MODIFICACAON;

    private static final String TAG =""; //InitFragment.class.getName();
    public CloudCall_Post() {
    }

    public void Post_Almacen( final Context context){
        try {
            JSONObject params = new JSONObject("{fecha: 2015-09-11}");


            FHCloudRequest request = FH.buildCloudRequest("almacenes", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    //v.setEnabled(true);
                    //response.setText(fhResponse.getJson().toString());

                    int Id;
                    String RazonSocial;
                    String resp = "";


                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);


                         IDWEB = row.getInt("Id");
                         RAZONSOCIAL = row.getString("RazonSocial");
                         NIT = row.getString("Nit");
                         DESCRIPCION = row.getString("Descripcion");
                         DIRECCION = row.getString("Direccion");
                         TELEFONO = row.getString("Telefono");
                         CORREO = row.getString("Correo");
                         POSICIONGPS = row.getString("PosicionGps");
                         LOGO = row.getString("Logo");
                         MARCADOR = row.getString("Marcador");
                         REGISTRO = row.getString("Registro");
                         MODIFICADOR = row.getString("Modificacion");
                         VISIBLE = row.getString("Visible");
                         ACTIVO = row.getString("Activo");
                         TAGS = row.getString("Tags");
                         X = row.getString("X");
                         Y = row.getString("Y");


                        String id = String.valueOf(IDWEB);  // convertimos el archivo int a string

                        Boolean existe_Registro = BuscarRegistro(context,id,"almacen");  //  buscamos si el registro ya esta agregado o no, devulve un boolean
                        if(!existe_Registro){  //  si me devuelve false entonces , ingresamos el registro,  si devuelve true seguimos el ciclo
                            insertar_registros_almacen(context);
                        }

                    }

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    //v.setEnabled(true);
                   // response.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }
    public void Post_Promociones(final Context context){

        try {
            JSONObject params = new JSONObject("{fecha: '2015-09-11' }");

            FHCloudRequest request = FH.buildCloudRequest("promociones", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");

                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);

                        IDWEB = row.getInt("Id");
                        REFERENCIA = row.getString("Referencia");
                        NOMBRE = row.getString("Nombre");
                        DESCRIPCION = row.getString("Descripcion");
                        VALORANTES = row.getString("ValorAntes");
                        VALORDESPUES = row.getString("ValorDespues");
                        DESCUENTO = row.getString("Descuento");
                        FECHAINICIO = row.getString("FechaInicio");
                        FECHAFIN = row.getString("FechaFin");
                        IMAGEN = row.getString("Imagen");
                        IDALMACEN = row.getString("IdAlmacen");
                        IDUSUARIO = row.getString("IdUsuario");
                        IDCATEGORIA = row.getString("IdCategoria");
                        ACTIVO = row.getString("Activo");
                        TAGS = row.getString("Tags");
                        X = row.getString("X");
                        Y = row.getString("Y");

                        String id = String.valueOf(IDWEB);  // convertimos el archivo int a string

                        Boolean existe_Registro = BuscarRegistro(context,id,"promociones");  //  buscamos si el registro ya esta agregado o no, devulve un boolean
                        if(!existe_Registro){  //  si me devuelve false entonces , ingresamos el registro,  si devuelve true seguimos el ciclo
                            insertar_registros_Promociones(context);
                        }

                    }



                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());

                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }


    }

    public void Post_Categorias (final Context context){
        try {
            JSONObject params = new JSONObject("{fecha: '2015-09-11' }");

            FHCloudRequest request = FH.buildCloudRequest("categorias", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");


                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);

                        IDWEB = row.getInt("Id");
                        NOMBRE = row.getString("Nombre");
                        IDCATEGORIA = row.getString("IdCategoria");
                        IMAGEN = row.getString("Imagen");
                        REGISTRO = row.getString("Registro");
                        MODIFICACAON = row.getString("Modificacion");
                        VISIBLE = row.getString("Visible");
                        ACTIVO = row.getString("Activo");

                        String id = String.valueOf(IDWEB);  // convertimos el archivo int a string

                        Boolean existe_Registro = BuscarRegistro(context,id,"categoria");  //  buscamos si el registro ya esta agregado o no, devulve un boolean
                        if(!existe_Registro){  //  si me devuelve false entonces , ingresamos el registro,  si devuelve true seguimos el ciclo
                            insertar_registros_Categorias(context);
                        }

                    }



                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());

                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }



    private Boolean BuscarRegistro(Context context, String id,String tabs) {

        // Obtenemos el getcontresolver del contentprovider
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor;
        String selection = "";
        Uri uricontenidobase = null;


        switch (tabs){
            case "promociones":{
                //Obtenemos la uri del calendario Temporal
                uricontenidobase = Contrato.Armark_promociones.URI_CONTENIDO;
                selection = Contrato.Armark_promociones.IDWEB+ "=?";
                break;
            }
            case "almacen":{
                uricontenidobase = Contrato.Armark_almacen.URI_CONTENIDO;
                selection = Contrato.Armark_almacen.IDWEB+ "=?";
                break;
            }
            case "categoria":{
                uricontenidobase = Contrato.Armark_categorias.URI_CONTENIDO;
                selection = Contrato.Armark_categorias.IDWEB+ "=?";
                break;
            }
        }
        Log.d("tag", uricontenidobase.toString());

        String[] arg = new String[]{id};

        cursor = contentResolver.query(uricontenidobase, null, selection, arg, null);

        if (null == cursor) {
           // dato no valido
            return false;
        } else if (cursor.getCount() < 1) {

            //  si la fila no existe retornamos false
            return false;

        } else {

            // si existe un dato ya con esta posicion enviamos true

            return true;
        }
    }

    private void insertar_registros_Promociones(Context context) {
        // el content provider genera todos los cambios en un content resolver
        ContentResolver contentResolver = context.getContentResolver();

        // creamos un contenedor de valores nuevos
        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_promociones.IDWEB,IDWEB);
        values.put(Contrato.Armark_promociones.REFERENCIA,REFERENCIA);
        values.put(Contrato.Armark_promociones.NOMBRE,NOMBRE);
        values.put(Contrato.Armark_promociones.DESCRIPCION,DESCRIPCION);
        values.put(Contrato.Armark_promociones.VALORANTES,VALORANTES);

        values.put(Contrato.Armark_promociones.VALORDESPUES,VALORDESPUES);
        values.put(Contrato.Armark_promociones.DESCUENTO,DESCUENTO);
        values.put(Contrato.Armark_promociones.FECHAINICIO,FECHAINICIO);
        values.put(Contrato.Armark_promociones.FECHAFIN,FECHAFIN);
        values.put(Contrato.Armark_promociones.IMAGEN,IMAGEN);

        values.put(Contrato.Armark_promociones.IDALMACEN,IDALMACEN);
        values.put(Contrato.Armark_promociones.IDUSUARIO,IDUSUARIO);
        values.put(Contrato.Armark_promociones.IDCATEGORIA,IDCATEGORIA);
        values.put(Contrato.Armark_promociones.ACTIVO,ACTIVO);
        values.put(Contrato.Armark_promociones.TAGS,TAGS);

        values.put(Contrato.Armark_promociones.X,X);
        values.put(Contrato.Armark_promociones.Y,Y);

        // insertamos la nueva fila con sus nuevos valores
        contentResolver.insert(Uri.parse(String.valueOf(Contrato.Armark_promociones.URI_CONTENIDO)),values);

    }
    private void insertar_registros_almacen(Context context) {

        // el content provider genera todos los cambios en un content resolver
        ContentResolver contentResolver = context.getContentResolver();

        // creamos un contenedor de valores nuevos
        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_almacen.IDWEB,IDWEB);
        values.put(Contrato.Armark_almacen.RAZONSOCIAL,RAZONSOCIAL);
        values.put(Contrato.Armark_almacen.NIT,NIT);
        values.put(Contrato.Armark_almacen.DESCRIPCION,DESCRIPCION);
        values.put(Contrato.Armark_almacen.DIRECCION,DIRECCION);

        values.put(Contrato.Armark_almacen.TELEFONO,TELEFONO);
        values.put(Contrato.Armark_almacen.CORREO,CORREO);
        values.put(Contrato.Armark_almacen.POSICIONGPS,POSICIONGPS);
        values.put(Contrato.Armark_almacen.LOGO,LOGO);
        values.put(Contrato.Armark_almacen.MARCADOR,MARCADOR);

        values.put(Contrato.Armark_almacen.REGISTRO,REGISTRO);
        values.put(Contrato.Armark_almacen.MODIFICADOR,MODIFICADOR);
        values.put(Contrato.Armark_almacen.VISIBLE,VISIBLE);
        values.put(Contrato.Armark_almacen.ACTIVO,ACTIVO);
        values.put(Contrato.Armark_almacen.TAGS,TAGS);

        values.put(Contrato.Armark_almacen.X,X);
        values.put(Contrato.Armark_almacen.Y,Y);

        // insertamos la nueva fila con sus nuevos valores
        contentResolver.insert(Uri.parse(String.valueOf(Contrato.Armark_almacen.URI_CONTENIDO)),values);

    }
    private void insertar_registros_Categorias(Context context) {
        // el content provider genera todos los cambios en un content resolver
        ContentResolver contentResolver = context.getContentResolver();

        // creamos un contenedor de valores nuevos
        ContentValues values = new ContentValues();
        values.put(Contrato.Armark_categorias.IDWEB,IDWEB);
        values.put(Contrato.Armark_categorias.NOMBRE,NOMBRE);
        values.put(Contrato.Armark_categorias.IDCATEGORIAS,IDCATEGORIA);
        values.put(Contrato.Armark_categorias.IMAGEN,IMAGEN);
        values.put(Contrato.Armark_categorias.REGISTRO,REGISTRO);

        values.put(Contrato.Armark_categorias.MODIFICACION,MODIFICACAON);
        values.put(Contrato.Armark_categorias.VISIBLE,VISIBLE);
        values.put(Contrato.Armark_categorias.ACTIVO,ACTIVO);


        // insertamos la nueva fila con sus nuevos valores
        contentResolver.insert(Uri.parse(String.valueOf(Contrato.Armark_categorias.URI_CONTENIDO)),values);
    }
}
