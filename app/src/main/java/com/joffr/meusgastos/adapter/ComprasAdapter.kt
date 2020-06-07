package com.joffr.meusgastos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.joffr.meusgastos.R
import com.joffr.meusgastos.model.Compra
import kotlinx.android.synthetic.main.compra_details.view.*

class ComprasAdapter(
    private val compras: List<Compra>,
    private val context: Context?
) : Adapter<ComprasAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.compra_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return compras.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val compra = compras[position]
        holder.bindView(compra)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(compra: Compra) {
            val titulo = itemView.titulocompra
            val preco = itemView.titulovalorcompra

            titulo.text = compra.nome
            preco.text = compra.preco.toString()
        }
    }
}