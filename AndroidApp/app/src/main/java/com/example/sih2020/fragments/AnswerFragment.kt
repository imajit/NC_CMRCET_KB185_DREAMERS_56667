package com.example.sih2020.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.sih2020.R
import com.example.sih2020.classes.Questionnaire
import com.example.sih2020.dbClasses.model.QuestionDatabase
import com.example.sih2020.dbClasses.model.QuestionEntity
import com.example.sih2020.dbClasses.qa
import com.example.sih2020.utils.BaseFragment
import com.example.sih2020.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_answer.*
import kotlinx.coroutines.launch


class AnswerFragment : BaseFragment(){


    lateinit var inputEditText: TextInputEditText
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
        return inflater.inflate(R.layout.fragment_answer, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputEditText = view.findViewById(R.id.edittext_answer)
        floatingActionButton = view.findViewById(R.id.FloatingButton)
        questiontext = view.findViewById(R.id.textViewQuestionDetail)
        questiontext.text = "Question: " + Questionnaire.questionnaireQuestions[requireArguments().getInt("QuestionNumber")] + "\nCategory : " +  Questionnaire.questionnaireCategory[requireArguments().getInt("QuestionNumber")]


        inputEditText.addTextChangedListener( object : TextWatcher
        {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                floatingActionButton.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                floatingActionButton.isEnabled = !inputEditText.text.isNullOrEmpty()


                floatingActionButton.setOnClickListener {

                    launch {
                        val questionEntity = QuestionEntity(questiontext.toString(),inputEditText.toString())
                        context?.let {
                            var qObj = qa()
                            qObj.analysis = 0.0
                            qObj.answer = inputEditText.text.toString()
                            qObj.question = Questionnaire.questionnaireQuestions[requireArguments().getInt("QuestionNumber")]
                            qObj.category = Questionnaire.questionnaireCategory[requireArguments().getInt("QuestionNumber")]
                            Constants.qList.add(qObj)
                            Constants.questionsCount++
                            QuestionDatabase(it).getquestionDao().addanswer(questionEntity)
                            Toast.makeText(it, "Saved", Toast.LENGTH_SHORT).show()
                        }
                    }


                }

            }

        })
    }











}