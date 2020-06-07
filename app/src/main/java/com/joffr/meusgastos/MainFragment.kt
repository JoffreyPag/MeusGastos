package com.joffr.meusgastos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.joffr.meusgastos.adapter.ComprasAdapter
import com.joffr.meusgastos.model.Compra
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    var database: FirebaseDatabase? = null
    var myRef: DatabaseReference? = null
    val compras = mutableListOf<Compra>()

    var bottomBehaviour: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //================ firebase =============
        // Write a message to the database
        database = FirebaseDatabase.getInstance()
        myRef = database!!.getReference("Compras")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bottomBehaviour = BottomSheetBehavior.from(view.bottom_sheet_compra)
//        view.bar_criar_compra.setNavigationOnClickListener {
//            bottomBehaviour!!.state = BottomSheetBehavior.STATE_HIDDEN
//        }

        view.fab.setOnClickListener { chamaCriarcompra() }
        val viewManager = LinearLayoutManager(context)
        view.recycler_contas.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = ComprasAdapter(compras, context)
        }

        //===== listener firebase ========
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue()
                compras.clear()
                for (data in dataSnapshot.children) {
                    compras.add(data.getValue(Compra::class.java)!!)
                }
                (view.recycler_contas.adapter as ComprasAdapter).notifyDataSetChanged()
                view.loandingMainContentBar.visibility = View.GONE
                view.fab.show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        myRef!!.addValueEventListener(postListener)
        //====== fim do listener firebase ==============

        view.bottomappbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.card_item -> chamaCartoes(view)
            }
            true
        }
    }

    fun chamaCartoes(view: View) {
        Snackbar.make(view.fab, "clicou cards", Snackbar.LENGTH_SHORT)
            .setAnchorView(view.fab).show()

        val fragmentTransaction = fragmentManager?.beginTransaction()
        val fragment = CartoesFragment()
        fragmentTransaction?.replace(R.id.frame_for_fragments, fragment)
            ?.addToBackStack(null)
            ?.commit()

    }

    fun chamaCriarcompra() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val fragment = CriarCompraFragment()
        fragmentTransaction?.replace(R.id.frame_for_fragments, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
