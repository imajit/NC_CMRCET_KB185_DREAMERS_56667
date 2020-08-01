package com.example.sih2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.sih2020.R
import com.example.sih2020.utils.RecyclerViewClickListner
import com.google.android.material.textview.MaterialTextView

class PendingSurveyApater(private val listner: RecyclerViewClickListner):RecyclerView.Adapter<PendingSurveyApater.ViewHolder>(){

    private var checkBox:CheckBox? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingSurveyApater.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pendingsurvey,parent,false)
        checkBox = v.findViewById(R.id.checkbox_survey)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: PendingSurveyApater.ViewHolder, position: Int) {
        holder.itemView.findViewById<MaterialTextView>(R.id.pendingSurveyID).text = "Survey ${position+1}"
        checkBox!!.setOnClickListener{
            listner.onRecyclerViewItemClick(it,position)
        }
    }


    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }


}