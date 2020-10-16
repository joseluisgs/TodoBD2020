package com.joseluisgs.todobd2020.ui.datos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joseluisgs.todobd2020.R
import com.joseluisgs.todobd2020.datos.Dato
import kotlinx.android.synthetic.main.list_dato_item.view.*

class DatosListAdapter(
    private val listaDatos: MutableList<Dato>,
    private val listener: (Dato) -> Unit
) :
    RecyclerView.Adapter<DatosListAdapter.DatoViewHolder>() {

//class NoticiasListAdapter(// Objeto con el modelo de datos (lista)
//	private val listaNoticias: MutableList<Noticia>, // Fragment Manager para trabajar con el
//	private val fm: FragmentManager
//) :
//	RecyclerView.Adapter<NoticiasListAdapter.NoticiaViewHolder>() {

    /**
     * Asociamos la vista
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatoViewHolder {
        return DatoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_dato_item, parent, false)
        )
    }

    /**
     * Procesamos los datos y las metemos en un Holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: DatoViewHolder, position: Int) {
        holder.txItemDato.text = listaDatos[position].description
        holder.ivItemDato.setImageResource(listaDatos[position].imgId)
//        Picasso.get()
//            .load(listaNoticias[position].imagen) //Instanciamos un objeto de la clase (creada más abajo) para redondear la imagen
//            .transform(CirculoTransformacion())
//            .resize(375, 200)
//            .into(holder.ivNoticia)

        // Programamos el clic de cada fila (itemView)
        holder.itemView
            .setOnClickListener {
                listener(listaDatos[position])
            }
    }

    /**
     * Elimina un item de la lista
     *
     * @param position
     */
    fun removeItem(position: Int) {
        listaDatos.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listaDatos.size)
    }

    /**
     * Recupera un Item de la lista
     *
     * @param item
     * @param position
     */
    fun restoreItem(item: Dato, position: Int) {
        listaDatos.add(position, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, listaDatos.size)
    }

    /**
     * Para añadir un elemento
     * @param item Dato
     */
    fun addItem(item: Dato) {
        listaDatos.add(item)
        notifyItemInserted(listaDatos.size)
    }

    /**
     * Devuelve el número de items de la lista
     *
     * @return
     */
    override fun getItemCount(): Int {
        return listaDatos.size
    }

    fun list(): MutableList<Dato> {
        return this.listaDatos
    }


    /**
     * Holder que encapsula los objetos a mostrar en la lista
     */
    class DatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementos graficos con los que nos asociamos
        var ivItemDato = itemView.ivItemDato
        var txItemDato = itemView.txItemDato
    }
}
