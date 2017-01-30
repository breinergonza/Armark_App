package modelo;

import android.net.Uri;

/**
 * Created by ASUS on 20/10/2016.
 */
public class Contrato {

    interface ColumnasAlmacen {

        String IDWEB = "idweb";
        String RAZONSOCIAL = "razonsocial";
        String NIT = "nit";
        String DESCRIPCION = "descripcion";
        String DIRECCION = "direccion";
        String TELEFONO = "telefono";
        String CORREO = "correo";
        String POSICIONGPS = "posiciongps";
        String LOGO = "logo";
        String MARCADOR = "marcador";
        String REGISTRO = "registro";
        String MODIFICADOR = "modificador";
        String VISIBLE = "visible";
        String ACTIVO = "activo";
        String TAGS = "tags";
        String X = "x";
        String Y = "y";
    }

    interface ColumnasPromociones {
        String IDWEB = "idweb";
        String REFERENCIA = "referencia";
        String NOMBRE  ="nombre";
        String DESCRIPCION = "descripcion";
        String VALORANTES = "valorantes";
        String VALORDESPUES = "valordespues";
        String DESCUENTO = "descuento";
        String FECHAINICIO = "fechainicio";
        String FECHAFIN = "fechafin";
        String IMAGEN = "imagen";
        String IDALMACEN = "idalmacen";
        String IDUSUARIO = "idusuario";
        String IDCATEGORIA = "idcategoria";
        String ACTIVO = "activo";
        String TAGS =  "tags";
        String X = "x";
        String Y = "y";
    }

    interface ColumnasCategorias {
        String IDWEB = "idweb";
        String NOMBRE = "nombre";
        String IDCATEGORIAS = "idcategorias";
        String IMAGEN = "imagen";
        String REGISTRO = "registro";
        String MODIFICACION = "modificacion";
        String VISIBLE = "visible";
        String ACTIVO = "activo";

    }

    // Autoridad del Content Provider
    public final static String AUTORIDAD = "com.feedhenry.armark";

    // Uri base
    public final static Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);

    // String de Almacen
    public static class Armark_almacen implements ColumnasAlmacen {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_ALMACEN).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_ALMACEN;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_ALMACEN;


        /**
         * Construye una {@link Uri} para el {@link #} solicitado.
         */
        public static Uri construirUriAlmacen(String idTorneo) {
            return URI_CONTENIDO.buildUpon().appendPath(idTorneo).build();
        }

        /*public static String generarIdAlmacen() {
            return "A-" + UUID.randomUUID();
        }*/

        public static String obtenerIdAlmacen(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
    // fin de string almacen




    //  inicio string de promociones
    public static class Armark_promociones implements ColumnasPromociones {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_PROMOCIONES).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_PROMOCIONES;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_PROMOCIONES;


        /**
         * Construye una {@link Uri} para el {@link #} solicitado.
         */
        public static Uri construirUriPromociones(String idTorneo) {
            return URI_CONTENIDO.buildUpon().appendPath(idTorneo).build();
        }

        /*public static String generarIdPromociones() {
            return "A-" + UUID.randomUUID();
        }*/

        public static String obtenerIdPromociones(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    // fin de string promociones


    //  inicio string de categorias
    public static class Armark_categorias implements ColumnasCategorias {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSOS_CATEGORIAS).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSOS_CATEGORIAS;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSOS_CATEGORIAS;


        /**
         * Construye una {@link Uri} para el {@link #} solicitado.
         */
        public static Uri construirUriCategorias(String idTorneo) {
            return URI_CONTENIDO.buildUpon().appendPath(idTorneo).build();
        }

        /*public static String generarIdCategorias() {
            return "A-" + UUID.randomUUID();
        }*/

        public static String obtenerIdCategorias(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    // fin de string categorias






    // Recursosp
    public final static String RECURSO_ALMACEN = "almacen";
    public final static String RECURSO_PROMOCIONES = "promociones";
    public final static String RECURSOS_CATEGORIAS = "categorias";


}
