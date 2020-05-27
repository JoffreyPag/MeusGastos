package com.joffr.meusgastos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cartoes.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainActivity : AppCompatActivity() {

    var bottomBehaviour:BottomSheetBehavior<View>? = null
    var fragmentSheet : Fragment? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = MainFragment()
        fragmentTransaction.add(R.id.frame_for_fragments, fragment)
        fragmentTransaction.commit()

        bottomBehaviour = BottomSheetBehavior.from(bottom_sheet_cartoes)
        bottomBehaviour!!.state = BottomSheetBehavior.STATE_HIDDEN

        topAppBar_sheet.setNavigationOnClickListener {
            bottomBehaviour!!.state = BottomSheetBehavior.STATE_HIDDEN
        }

        fab.setOnClickListener {
            Snackbar.make(fab, "Will Be implemented", Snackbar.LENGTH_SHORT).setAnchorView(fab).show()
        }

        bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.card_item -> bottomBehaviour!!.state = BottomSheetBehavior.STATE_EXPANDED
            }
            true
        }

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Compras")
        var num = 1
        //val compra = Compra("compra123", (num/10.0))
        //Log.i("key", myRef.key)
        // myRef.child(myRef.push().key!!).setValue(compra)
        // num++

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue()
                loandingMainContentBar.visibility = View.GONE
                fab.show()
                if (post == null) createbanner("Não há cartão cadastrado", "Criar", "Depois")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        myRef.addValueEventListener(postListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun createbanner(texto:String?, opc1:String?, opc2:String? ){
        banner.contentText = texto
        banner.leftButtonText = opc2
        banner.rightButtonText = opc1
        banner.setLeftButtonAction { banner.dismiss() }
        banner.setRightButtonAction { Snackbar.make(fab, "Unable to do at moment", Snackbar.LENGTH_SHORT).setAnchorView(fab).show() }
        banner.show()
    }
}
