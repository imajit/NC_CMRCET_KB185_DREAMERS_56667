package com.example.sih2020.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sih2020.R
import com.example.sih2020.classes.Questionnaire
import com.example.sih2020.dbClasses.model.QuestionDatabase
import com.example.sih2020.dbClasses.model.QuestionEntity
import com.example.sih2020.dbClasses.qa
import com.example.sih2020.utils.BaseFragment
import com.example.sih2020.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_m_c_q_answer.*
import kotlinx.coroutines.launch


class MCQAnswerFragment : BaseFragment(){

    private var radioGroup: RadioGroup? = null
    private  var radioButton: RadioButton? = null
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var questiontext : MaterialTextView
    private var answerradio: String? = null
    private var analysisRadio: Double? = null

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

        when(requireArguments().get("tag")){
            "yn"->{
                radioButtonAnswer1.text = "Strongly Agree"
                radioButtonAnswer2.text = "Agree"
                radioButtonAnswer3.text = "disagree"
                radioButtonAnswer4.text = "Strongly disagree"
            }
            "week"->{
                radioButtonAnswer1.text = "2 weeks"
                radioButtonAnswer2.text = "4 weeks"
                radioButtonAnswer3.text = "6 weeks"
                radioButtonAnswer4.text = "8 weeks+"
            }
            "per"->{
                radioButtonAnswer1.text = "> 90% "
                radioButtonAnswer2.text = "75% - 90%"
                radioButtonAnswer3.text = "60% - 75%"
                radioButtonAnswer4.text = "< 60%"
            }
        }

        floatingActionButton = view.findViewById(R.id.FloatingButtonMCQ)
        questiontext = view.findViewById(R.id.textViewQuestionDetail)
        questiontext.text = "Question ${requireArguments().getInt("QuestionNumber")}: " + Questionnaire.questionnaireQuestions[requireArguments().getInt("QuestionNumber")] + "\nCategory : " +  Questionnaire.questionnaireCategory[requireArguments().getInt("QuestionNumber")]

        radioGroup = view.findViewById(R.id.radio_group_1) as RadioGroup

        radioGroup!!.setOnCheckedChangeListener { group, checkedId ->
            floatingActionButton.isEnabled = true
            floatingActionButton.setOnClickListener {
                if(checkedId == R.id.radioButtonAnswer1)
                {
                    answerradio = radioButtonAnswer1.text.toString()
                    analysisRadio = 10.0

                }
                if (checkedId == R.id.radioButtonAnswer2)
                {
                    answerradio = radioButtonAnswer2.text.toString()
                    analysisRadio = 7.5
                }
                if (checkedId == R.id.radioButtonAnswer3)
                {
                    answerradio = radioButtonAnswer3.text.toString()
                    analysisRadio = 2.5
                }
                if (checkedId == R.id.radioButtonAnswer4)
                {
                    answerradio = radioButtonAnswer4.text.toString()
                    analysisRadio = 0.5
                }

                launch {
                    val questionEntity = QuestionEntity(questiontext.toString(),answerradio.toString())
                    context?.let {
                        var qObj = qa()
                        qObj.analysis = analysisRadio!!
                        qObj.answer = answerradio.toString()
                        qObj.question = Questionnaire.questionnaireQuestions[requireArguments().getInt("QuestionNumber")]
                        qObj.category = Questionnaire.questionnaireCategory[requireArguments().getInt("QuestionNumber")]
                        Constants.qList.add(qObj)
                        Constants.questionsCount++
                        QuestionDatabase(it).getquestionDao().addanswer(questionEntity)
                        Toast.makeText(it, "Saved", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()


                    }
                }

            }

                }







        /**
         *
         * TODO
         * Fetch Value from the radio group
         * and update it
         */



    }

}