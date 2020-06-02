package com.joffr.meusgastos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.joffr.meusgastos.model.Compra
import kotlinx.android.synthetic.main.fragment_criar_compra.*
import kotlinx.android.synthetic.main.fragment_criar_compra.view.*
import kotlinx.android.synthetic.main.fragment_criar_compra.view.fabCriarCompra

var database: FirebaseDatabase? = null
var myRef: DatabaseReference? = null

class CriarCompraFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        myRef = database!!.getReference("Compras")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_criar_compra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.bar_criar_compra.setNavigationOnClickListener { fragmentManager?.popBackStack() }
        view.fabCriarCompra.setOnClickListener {
            if (campodescompra.text.isNullOrEmpty() || campovalorcompra.text.isNullOrEmpty()) {
                Snackbar.make(fabCriarCompra, "Algum campo está vazio", Snackbar.LENGTH_SHORT)
                    .setAnchorView(fabCriarCompra).show()
            } else {
                val compra = Compra(
                    campodescompra?.text.toString(),
                    campovalorcompra.text.toString().toDouble()
                )
                myRef!!.child(myRef!!.push().key!!).setValue(compra)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show()
                        fragmentManager?.popBackStack()
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

}
