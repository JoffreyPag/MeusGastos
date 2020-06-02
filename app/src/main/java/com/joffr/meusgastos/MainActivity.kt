package com.joffr.meusgastos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {


    var fragmentSheet: Fragment? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
        val fragment = MainFragment()
        fragmentTransaction.add(R.id.frame_for_fragments, fragment)
        fragmentTransaction.commit()
//        bottomBehaviour = BottomSheetBehavior.from(bottom_sheet_compra)
//        bottomBehaviour!!.state = BottomSheetBehavior.STATE_HIDDEN
//        topAppBar_sheet.setNavigationOnClickListener {
//            bottomBehaviour!!.state = BottomSheetBehavior.STATE_HIDDEN
//        }
        //sheetPager.adapter = BottomSheetAdapter(supportFragmentManager)
    }

}
