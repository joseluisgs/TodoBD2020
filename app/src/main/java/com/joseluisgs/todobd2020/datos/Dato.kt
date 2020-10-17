package com.joseluisgs.todobd2020.datos

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Clase Pojo para el modelo
 * @property descripcion String
 * @property imgId Int
 * @constructor
 */

// Podemos usar la notacion
@RealmClass
open class Dato(@PrimaryKey var id: String, var descripcion: String, var imgId: Int) : RealmObject()
