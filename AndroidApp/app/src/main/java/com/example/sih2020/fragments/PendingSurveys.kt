package com.example.sih2020.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sih2020.R
import com.example.sih2020.adapters.PendingSurveyApater
import com.example.sih2020.adapters.QuestionnaireAdapter
import com.example.sih2020.utils.BaseFragment
import com.example.sih2020.utils.Constants
import com.example.sih2020.utils.RecyclerViewClickListner
import com.example.sih2020.utils.sentimentOnList
import kotlinx.android.synthetic.main.fragment_pending_surveys.*
import java.text.FieldPosition


class PendingSurveys : BaseFragment(), RecyclerViewClickListner {


    private  var recyclerView: RecyclerView? = null
    private  var adapter: PendingSurveyApater?= null
    private var linearLayoutManager: LinearLayoutManager?= null
    private var counter: Int ? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_surveys, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PendingSurveyApater(this)
        recyclerView=view.findViewById(R.id.recyclerView_pendingSurvey)
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        if(FloatingButtonSubmitPendingSurvey.isEnabled){
            FloatingButtonSubmitPendingSurvey.setOnClickListener(View.OnClickListener {
                getRecordsAndUpload()
            })
        }
    }

    private fun getRecordsAndUpload() {
        sentimentOnList(Constants.pendingSurveys.records.questions,Constants.pendingSurveys.schoolId,Constants.pendingSurveys.records,Constants.photo,requireContext())
    }


    override fun onRecyclerViewItemClick(view: View,position: Int) {
        when(view.id)
        {
            R.id.cardviewQuestion->{
                Toast.makeText(requireContext(), "item is clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.checkbox_survey-> {
                var cb = view.findViewById<CheckBox>(R.id.checkbox_survey)
                if (cb.isChecked) {
                    counter= counter!! +1;
                    FloatingButtonSubmitPendingSurvey.isEnabled = true
                    FloatingButtonSubmitPendingSurvey.show()


                    Log.d("check", "onRecyclerViewItemClick: $position")
                }else{
                    counter= counter!! - 1
                    if(counter == 0){
                        FloatingButtonSubmitPendingSurvey.isEnabled = false
                        FloatingButtonSubmitPendingSurvey.hide()
                    }
                    Log.d("check", "onRecyclerViewItemClick: $position")
                }
            }
        }
    }



}