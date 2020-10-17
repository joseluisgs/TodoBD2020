package com.joseluisgs.todobd2020.datos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory

import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.Nullable


/**
 * Clase para Construir el Controlador de la Base de Datos de SQLite
 * @constructor
 */
class DatosBD(@Nullable context: Context?, @Nullable name: String?, @Nullable factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    //Sentencia SQL para crear la tabla de de almacenamiento
    private val DATOS_DB = "DatosSQLite"
    private val CREATE_DATOS_DB = "CREATE TABLE $DATOS_DB(description TEXT, imgId INTEGER)"
    private val DELETE_DATOS_DB = "DROP TABLE IF EXISTS $DATOS_DB"

    /**
     * Crea las bases de datos indicadas
     *
     * @param db Referencia BD SQLite
     */
    override fun onCreate(db: SQLiteDatabase) {
        //Cuando se llame al onCreate se realiza la sentencia
        db.execSQL(CREATE_DATOS_DB)
    }

    /**
     * Actualizas la tablas
     *
     * @param db         Referencia BD SQLite
     * @param oldVersion antigua versión
     * @param newVersion nueva versión
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
        db.execSQL(DELETE_DATOS_DB)
        db.execSQL(CREATE_DATOS_DB)
    }
}
