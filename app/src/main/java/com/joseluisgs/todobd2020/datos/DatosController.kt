package com.joseluisgs.todobd2020.datos

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


object DatosController {
    // Variables de
    private const val DATOS_BD = "DATOS_BD_LITE"
    private const val DATOS_BD_VERSION = 1

    // Inicia la lista de datos con datos ficticios
    fun initDatos(): MutableList<Dato> {
        var datos = mutableListOf<Dato>() // Lista
        datos.add(Dato("Email", android.R.drawable.ic_dialog_email))
        datos.add(Dato("Info", android.R.drawable.ic_dialog_info))
        datos.add(Dato("OOOOHHHHH", android.R.drawable.ic_delete))
        datos.add(Dato("Dialer", android.R.drawable.ic_dialog_dialer))
        datos.add(Dato("Alert", android.R.drawable.ic_dialog_alert))
        datos.add(Dato("Map", android.R.drawable.ic_dialog_map))
        return datos;
    }

    @SuppressLint("Recycle")

    fun getDatos(context: Context, filtro: String?): MutableList<Dato>? {
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
                val aux = Dato(c.getString(0), c.getInt(1))
                lista.add(aux)
            } while (c.moveToNext())
        }
        bd.close()
        bdDatos.close()
        return lista
    }


}