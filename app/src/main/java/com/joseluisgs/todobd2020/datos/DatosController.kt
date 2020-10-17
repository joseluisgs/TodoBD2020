package com.joseluisgs.todobd2020.datos

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where


object DatosController {
    // Variables de
    private const val DATOS_BD = "DATOS_BD_REALM"
    private const val DATOS_BD_VERSION = 1L

    fun initRealm(context: Context?) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name(DATOS_BD)
            .schemaVersion(DATOS_BD_VERSION) // Versión de esquema estamos trabajando, si lo cambiamos, debemos incrementar
            .deleteRealmIfMigrationNeeded() // Podemos borrar los datos que ya haya si cambiamos el esquema
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


    /**
     * Selecciona los datos
     * @return MutableList<Dato>?
     */
    fun selectDatos(): MutableList<Dato>? {
        return Realm.getDefaultInstance().copyFromRealm(
                Realm.getDefaultInstance().where<Dato>().findAll()
        )
    }

    // Manejar un CRUD
    // https://parzibyte.me/blog/2019/02/04/tutorial-sqlite-android-crud-create-read-update-delete/
    /**
     * Inserta un lugar en el sistema de almacenamiento
     */
    fun insertDato(dato: Dato) {
        Realm.getDefaultInstance().executeTransaction {
            it.copyToRealm(dato); // Copia, inserta
        }
    }

    /**
     * Elimina un lugar del sistema de almacenamiento
     */
    fun deleteDato(dato: Dato) {
        Realm.getDefaultInstance().executeTransaction {
            it.where<Dato>().equalTo("descripcion", dato.descripcion).findFirst()?.deleteFromRealm()
        }
    }

    /**
     * Actualiza un lugar en el sistema de almacenamiento
     */
    fun updateDato(dato: Dato) {
        Realm.getDefaultInstance().executeTransaction {
            it.copyToRealmOrUpdate(dato)
        }
    }

    /**
     * Elimina todos los objetos
     */
    fun removeAll() {
        Realm.getDefaultInstance().executeTransaction {
            it.deleteAll();
        }
    }


}