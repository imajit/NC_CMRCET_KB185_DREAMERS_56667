package com.example.sih2020.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.sih2020.R
import com.example.sih2020.utils.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView


class MCQAnswerFragment : BaseFragment(){

    private var radioGroup: RadioGroup? = null
    private var radioButton: RadioButton? = null
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var questiontext : MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_c_q_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        floatingActionButton = view.findViewById(R.id.FloatingButtonMCQ)
        questiontext = view.findViewById(R.id.textViewQuestionDetail)

        radioGroup = view.findViewById(R.id.radio_group_1)

        /**
         *
         * TODO
         * Fetch Value from the radio group
         * and update it
         */

    }

}