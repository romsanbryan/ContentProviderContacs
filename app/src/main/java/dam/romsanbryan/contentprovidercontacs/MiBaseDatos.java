package dam.romsanbryan.contentprovidercontacs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que crea una base de datos de los contactos de la agenda
 * @author romsanbryan on 6/02/18.
 *
 */

public class MiBaseDatos extends SQLiteOpenHelper{

    /**
     * Constructor
     * @param context
     */
    public MiBaseDatos(Context context){
        super(context, "mibasedatos.db", null, 1);
    }

    /**
     * Creamos la base de datos
     * @param db Nombre de la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLA_CONTACTOS);
    }

    /**
     * Actualizamos la base de datos
     * @param db Nombre de la base de datos
     * @param oldVersion Version antigua
     * @param newVersion Version nueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CONTACTOS);
        onCreate(db);
     }

     // Variables
     private static final String TABLA_CONTACTOS = "CREATE TABLE contactos"+"(_id INT PRIMARY KEY, nombre TEXT, telefono INT, email TEXT)";
}
