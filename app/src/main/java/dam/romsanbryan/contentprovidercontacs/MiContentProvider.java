package dam.romsanbryan.contentprovidercontacs;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Bryan Jesús Romero Santos on 6/02/18.
 */

public class MiContentProvider extends ContentProvider {

    /**
     * onCreate que asigna el objeto a la clase de la base de datos
     * @return true al crearlo
     */
    public boolean onCreate(){
        MBD = new MiBaseDatos(getContext());
        return true;
    }

    /**
     * Insertamos datos en la base de datos
     *
     * @param uri Uri de nuestra promotor de contenido
     * @param values Valores que queremos annadir
     * @return Registro annadido
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long registro = 0;
        try{
            if(uriMartcher.match(uri)==CONTACTOS){
                SQLDB = MBD.getWritableDatabase();
                registro = SQLDB.insert("contactos", null, values);
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR", "Argumento no admitido" +e.toString());
        }

        // Comprobar si se inserto bien el registro
        if (registro > 0){
            Log.e("INSERT", "Registro creado correctamente");
        }else{
            Log.e("ERROR", "Al insertar el registro: " +registro);
        }
        return Uri.parse("contactos/"+registro);
    }

    /**
     * Actualizamos la base de datos
     * @param uri Uri de nuestro promotor de contenido
     * @param values Valores para actualizar
     * @param selection
     * @param selectionArgs
     * @return Id del registro actualizado
     */
    @Override
    public int update(Uri uri,ContentValues values, String selection, String[] selectionArgs) {
        String id = "";
        try {
            if (uriMartcher.match(uri)==CONTACTOS_ID){
                id = uri.getLastPathSegment();
                SQLDB = MBD.getWritableDatabase();
                SQLDB.update("contactos", values, "_id="+id, selectionArgs);
            }
        }catch (IllegalArgumentException e) {
            Log.e("ERROR", "Argumento no admitido" + e.toString());
        }
        return Integer.parseInt(id);
    }

    /**
     * Borramos datos
     *
     * @param uri Uri de nuestro promotor de contenido
     * @param selection
     * @param selectionArgs
     * @return Id registro borrado
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int registro = 0;
        try {
            if (uriMartcher.match(uri)==CONTACTOS_ID){
                String id = uri.getLastPathSegment();
                SQLDB = MBD.getWritableDatabase();
                registro = SQLDB.delete("contactos", id, null);
            }
        }catch (IllegalArgumentException e) {
            Log.e("ERROR", "Argumento no admitido" + e.toString());
        }
        return registro;
    }

    /**
     * Consultas
     *
     * @param uri Uri de nuestro promotor de contenido
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return Cursor utilizado
     */
    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;

        try {
            switch (uriMartcher.match(uri)){
                case CONTACTOS_ID:
                    String id = "_id="+uri.getLastPathSegment();
                    SQLDB = MBD.getReadableDatabase();
                    c = SQLDB.query("contactos", projection, id, selectionArgs, null, null, null, sortOrder);
                    break;
                case CONTACTOS:
                    SQLDB = MBD.getReadableDatabase();
                    c = SQLDB.query("contactos", projection, null, selectionArgs, null, null, null, sortOrder );
                    break;
            }
        }catch (IllegalArgumentException e) {
            Log.e("ERROR", "Argumento no admitido" + e.toString());
        }
        return c;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }

    // Variables
    private MiBaseDatos MBD;
    private SQLiteDatabase SQLDB;
    private static final String NOMBRE_CP = "mi.contect.provider.contactos"; // Autoria de nuestro promotor

    private static final int CONTACTOS = 1;
    private static final int CONTACTOS_ID = 2;
    private static final UriMatcher uriMartcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMartcher.addURI(NOMBRE_CP, "contactos", CONTACTOS);
        uriMartcher.addURI(NOMBRE_CP, "contactos/#", CONTACTOS_ID);
    }
}
