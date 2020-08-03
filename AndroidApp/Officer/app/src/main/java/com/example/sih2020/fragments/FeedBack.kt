package com.example.sih2020.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.sih2020.R
import com.example.sih2020.utils.BaseFragment
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_feed_back.*


class FeedBack :BaseFragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var subjects = arrayOf("Not Able To Register","App Not working as Expected ","Others")
        var adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            subjects
        )
        dropdownSubject.threshold = 0;
        dropdownSubject.setAdapter(adapter)
        dropdownSubject.setOnFocusChangeListener { view, b -> if (b) dropdownSubject.showDropDown() }

        ButtonSendMail.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.ButtonSendMail->{
                val mIntent = Intent(Intent.ACTION_SEND)
                mIntent.data = Uri.parse("mailto:")
                mIntent.type = "text/plain"
                mIntent.putExtra(Intent.EXTRA_EMAIL, "SIH2020MonitoringApp@gmail.com")
                mIntent.putExtra(Intent.EXTRA_SUBJECT, dropdownSubject.text.toString())

                try {
                    startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
                } catch (e: Exception) {
                }
            }

        }

    }
}