package com.joseluisgs.todobd2020.datos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration


object DatosController {
    // Variables de
    private const val DATOS_BD = "DATOS_BD_REALM"
    private const val DATOS_BD_VERSION = 1L

    fun initRealm(context: Context?) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name(DATOS_BD)
            .schemaVersion(DATOS_BD_VERSION) // Versión de esquema estamos trabajando, si lo cambiamos, debemos incrementar
            //.deleteRealmIfMigrationNeeded() // Podemos borrar los datos que ya haya si cambiamos el esquema
            .build()
        Realm.setDefaultConfiguration(config)
        Log.d("Datos", "Iniciando Realm")
    }

    // Inicia la lista de datos con datos ficticios
    fun initDatos(): MutableList<Dato> {
        val datos = mutableListOf<Dato>() // Lista
        datos.add(Dato("Email", android.R.drawable.ic_dialog_email))
        datos.add(Dato("Info", android.R.drawable.ic_dialog_info))
        datos.add(Dato("OOOOHHHHH", android.R.drawable.ic_delete))
        datos.add(Dato("Dialer", android.R.drawable.ic_dialog_dialer))
        datos.add(Dato("Alert", android.R.drawable.ic_dialog_alert))
        datos.add(Dato("Map", android.R.drawable.ic_dialog_map))
        return datos;
    }



    fun selectDatos(filtro: String?, context: Context?): MutableList<Dato>? {
        // Abrimos la BD en Modo Lectura
        val lista = mutableListOf<Dato>()
        val bdDatos = DatosBD(context, DATOS_BD, null, DATOS_BD_VERSION)
        val bd: SQLiteDatabase = bdDatos.readableDatabase

        // Podemos hacer la consulta directamente o parametrizada
        //Cursor c = bd.rawQuery("SELECT * FROM Lugares " + filtro, null);
        // http://www.sgoliver.net/blog/bases-de-datos-en-android-iii-consultarrecuperar-registros/

        /* Ejemplo de cada campo de la consulta
            String table = "table2";
            String[] columns = {"column1", "column3"};
            String selection = "column3 =?";
            String[] selectionArgs = {"apple"};
            String groupBy = null;
            String having = null;
            String orderBy = "column3 DESC";
            String limit = "10";

            Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
     */
        val c: Cursor = bd.query(DatosBD.DATOS_TABLE, null, null, null, null, null, filtro, null)
        if (c.moveToFirst()) {
            do {
                val aux = Dato(c.getString(1), c.getInt(2))
                lista.add(aux)
            } while (c.moveToNext())
        }
        bd.close()
        bdDatos.close()
        return lista
    }

    // Manejar un CRUD
    // https://parzibyte.me/blog/2019/02/04/tutorial-sqlite-android-crud-create-read-update-delete/
    /**
     * Inserta un lugar en el sistema de almacenamiento
     */
    fun insertDato(dato: Dato, context: Context?): Boolean {
        // se insertan sin problemas porque lugares es clave primaria, si ya están no hace nada
        // Abrimos la BD en modo escritura
        val bdDatos = DatosBD(context, DATOS_BD, null, DATOS_BD_VERSION)
        val bd: SQLiteDatabase = bdDatos.writableDatabase
        var sal = false
        try {
            //Cargamos los parámetros
            val valores = ContentValues()
            valores.put("DESCRIPCION", dato.descripcion)
            valores.put("IMG_ID", dato.imgId)
            // insertamos en su tabla, en long tenemos el id más alto creado
            val res = bd.insert(DatosBD.DATOS_TABLE, null, valores)
            sal = true
        } catch (ex: SQLException) {
            Log.d("Datos", "Error al insertar un nuevo Dato " + ex.message)
        } finally {
            bd.close()
            bdDatos.close()
            return sal
        }
    }

    /**
     * Elimina un lugar del sistema de almacenamiento
     */
    fun deleteDato(dato: Dato, context: Context?): Boolean {
        // Abrimos la BD en modo escritura
        val bdDatos = DatosBD(context, DATOS_BD, null, DATOS_BD_VERSION)
        val bd: SQLiteDatabase = bdDatos.writableDatabase
        var sal = false
        try {

            // Creamos el where
            val where = "DESCRIPCION = ?"
            //Cargamos los parámetros es un vector, en este caso es solo uno, pero podrían ser mas
            val args = arrayOf(dato.descripcion)
            // En el fondo hemos hecho where descripción = dato.descripcion, podíamos habrr usado el id
            // Eliminamos. En res tenemos el numero de filas eliminadas por si queremos tenerlo en cuenta
            val res = bd.delete(DatosBD.DATOS_TABLE, where, args)
            sal = true
        } catch (ex: SQLException) {
            Log.d("Datos", "Error al eliminar este Dato " + ex.message)
        } finally {
            bd.close()
            bdDatos.close()
            return sal
        }
    }

    /**
     * Actualiza un lugar en el sistema de almacenamiento
     */
    fun updateDato(datoNew: Dato, datoOld: Dato, context: Context?): Boolean {
        // Abrimos la BD en modo escritura
        val bdDatos = DatosBD(context, DATOS_BD, null, DATOS_BD_VERSION)
        val bd: SQLiteDatabase = bdDatos.writableDatabase
        var sal = false
        try {
            // Cargamos los valores
            val valores = ContentValues()
            valores.put("DESCRIPCION", datoNew.descripcion)
            valores.put("IMG_ID", datoNew.imgId)
            // Creamos el where
            val where = "DESCRIPCION = ?"
            //Cargamos los parámetros es un vector, en este caso es solo uno, pero podrían ser mas
            val args = arrayOf(datoOld.descripcion)
            // En el fondo hemos hecho where descripción = dato.descripcion, podíamos haber usado el id
            // Eliminamos. En res tenemos el numero de filas eliminadas por si queremos tenerlo en cuenta
            val res = bd.update(DatosBD.DATOS_TABLE, valores, where, args)
            sal = true
        } catch (ex: SQLException) {
            Log.d("Datos", "Error al actualizar este lugar " + ex.message)
        } finally {
            bd.close()
            bdDatos.close()
            return sal
        }
    }

    fun removeAll(context: Context?): Boolean {
        // Abrimos la BD en modo escritura
        val bdDatos = DatosBD(context, DATOS_BD, null, DATOS_BD_VERSION)
        val bd: SQLiteDatabase = bdDatos.writableDatabase
        var sal = false
        try {
            bd.execSQL("DELETE FROM ${DatosBD.DATOS_TABLE}")
            sal = true
        } catch (ex: SQLException) {
            Log.d("Datos", "Error al borrar todos los datos " + ex.message)
        } finally {
            bd.close()
            bdDatos.close()
            return sal
        }
    }


}