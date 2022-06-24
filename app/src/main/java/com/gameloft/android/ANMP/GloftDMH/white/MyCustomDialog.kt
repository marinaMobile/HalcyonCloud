package com.gameloft.android.ANMP.GloftDMH.white

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gameloft.android.ANMP.GloftDMH.R
import kotlinx.android.synthetic.main.fragment_my_custom_dialog.*


class MyCustomDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_my_custom_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        fragment_cl.setOnClickListener{
            activity?.run {
                supportFragmentManager.beginTransaction().remove(this@MyCustomDialog)
                    .commitAllowingStateLoss()
            }
        }

    }
}
