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
// Es importante que los datos tenga un valor por defecto, lo ideal sería poner y detectar nulls, como dejo abajo
open class Dato(var id: Long = 0,
                var descripcion: String ="",
                var imgId: Int=0) : RealmObject() {
    constructor(descripcion: String, imgId: Int) : this((System.currentTimeMillis() / 1000L), descripcion, imgId)
}

//open class Student(
//        var name: String?= null,
//        var age: Int?= null
//) : RealmObject