package com.joffr.meusgastos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.joffr.meusgastos.model.Compra
import kotlinx.android.synthetic.main.fragment_criar_compra.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.lang.reflect.TypeVariable

class MainFragment : Fragment() {

    var database: FirebaseDatabase? = null
    var myRef: DatabaseReference? = null

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

        //===== listener firebase ========
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue()
//                val compras: MutableList<Compra> = mutableListOf()
//                dataSnapshot.children.mapNotNullTo(compras) { it.getValue<Compra>(Compra::class.java) }
//                Log.i("TAG", compras[0].nome)
                val compras = mutableListOf<Compra>()
                for (data in dataSnapshot.children) {
                    compras.add(data.getValue(Compra::class.java)!!)
                }
                for (compra in compras) { Log.i("TAG", compra.nome) }

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

        view.topAppBar.setOnMenuItemClickListener {
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
