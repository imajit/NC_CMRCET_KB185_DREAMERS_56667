package com.example.sih2020.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sih2020.R
import com.example.sih2020.classes.Questionnaire
import com.example.sih2020.classes.Questionnaire.*
import com.example.sih2020.utils.Constants
import com.example.sih2020.utils.RecyclerViewClickListner
import com.example.sih2020.utils.onQuestionclicked
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.cardview_questionlist.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class QuestionnaireAdapter(private val listner: onQuestionclicked):RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder>() {


    private var cardView: CardView? = null
    private var linearLayout: LinearLayout? = null
    private var textView: TextInputLayout? = null
    private val questions = Questionnaire.questionnaireQuestions


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionnaireAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_questionlist, parent, false)
        cardView = v.findViewById(R.id.cardviewQuestion)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardviewQuestion.textViewqID.text = "Question " + (position + 1).toString()
        holder.itemView.cardviewQuestion.textViewQDetail.text = questions[position]
        holder.itemView.cardviewQuestion.setOnClickListener {
                listner.Onclicked(questions[position],it,position)
        }

        setColor(holder)



    }

    private fun setColor(holder: QuestionnaireAdapter.ViewHolder) {
        if(holder.adapterPosition < Constants.qList.size){
            holder.itemView.cardviewQuestion.findViewById<LinearLayout>(R.id.questionCard).setBackgroundColor(Color.parseColor("#80010FFF"))
        }else{
            holder.itemView.cardviewQuestion.findViewById<LinearLayout>(R.id.questionCard).setBackgroundColor(Color.parseColor("#B4E7FD"))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}