package com.joffr.meusgastos.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.joffr.meusgastos.CartoesFragment
import com.joffr.meusgastos.CriarCartaoFragment

class BottomSheetAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return CartoesFragment()
        } else {
            return CriarCartaoFragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0){
            return "Cartões"
        }else{
            return "Criar novo"
        }
    }
}