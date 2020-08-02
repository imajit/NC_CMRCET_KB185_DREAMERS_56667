package com.example.sih2020.fragments


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sih2020.R
import com.example.sih2020.adapters.QuestionnaireAdapter
import com.example.sih2020.classes.PendingClass
import com.example.sih2020.classes.Questionnaire
import com.example.sih2020.dbClasses.Records
import com.example.sih2020.utils.BaseFragment
import com.example.sih2020.utils.Constants
import com.example.sih2020.utils.onQuestionclicked
import com.example.sih2020.utils.sentimentOnList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class QuestionnaireFragment : BaseFragment(), onQuestionclicked {


    private var submitButton: FloatingActionButton? = null
    private  var recyclerView: RecyclerView? = null
    private  var adapter:QuestionnaireAdapter?= null
    private var linearLayoutManager: LinearLayoutManager?= null
    private  var card: CardView?=null
    var navController: NavController? = null
    var pendingClass: PendingClass? = null

    /**TODO
     * add a class which contains both records and school id
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionnaire, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        submitButton = view.findViewById<FloatingActionButton>(R.id.submitButton)
        view.findViewById<FloatingActionButton>(R.id.submitButton).setOnClickListener(View.OnClickListener {
            if(Constants.qList.size == Questionnaire.questionnaireQuestions.size){
                UpdateToRoom()
            }else{
                Toast.makeText(requireContext(),"Submit all questions",Toast.LENGTH_SHORT).show()
            }
        })

        adapter = QuestionnaireAdapter(this)
        recyclerView=view.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter

    }

    private fun UpdateToRoom() {
        /*
        *TODO : Save the details from Constants to room
        */

        val record: Records= Records()
        record.creationDate = Constants.creationDate
        record.gpsLocation = Constants.gpsSnap
        record.officerId = Constants.officerId
        record.overallReview = Constants.overallReview
        record.questions = Constants.qList
        Constants.mapList.add(mapOf(Pair(Constants.schoolId,record)))
        //sentimentOnList(record.questions,Constants.schoolId,record,Constants.photo,requireContext())
        pendingClass = PendingClass()
        pendingClass?.records = record
        pendingClass?.schoolId = Constants.schoolId

        saveData(pendingClass!!)
        /**TODO
         * Call the function saveData( schoolId:String, records: Records) to save the results in gson format
         * call the function loadData() to load the data
         *
         */











    }

    override fun Onclicked(question: String, view: View,position: Int) {
        when(view.id)
        {
            R.id.cardviewQuestion-> {
                val bundle = bundleOf("QuestionNumber" to position)
                if(Constants.questionsCount == position){
                    navController!!.navigate(R.id.QuestionListToAnswer,bundle)
                    //
                }else{
                    Toast.makeText(requireContext(),"Submit Question ${Constants.questionsCount+1} first ",Toast.LENGTH_LONG).show()
                }
            }

            R.id.submitButton-> {
                if(Constants.questionsCount == Constants.qList.size){
                    Toast.makeText(requireContext(), "Submit all list", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),"Submit All Questions", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
    private fun saveData( pending: PendingClass)
    {
        val sharedPreferences = requireContext().getSharedPreferences("SP_INFO", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var gson = Gson()
        //please update the class file with all the variable required in json format
        var jsonString = gson.toJson(pending)
        editor.putString("School Survey List", jsonString)
        editor.apply()
        findNavController().popBackStack(R.id.homepage,false)

    }

    private fun loadData(): PendingClass {

        val sharedPreferences = requireContext().getSharedPreferences("SP_INFO", MODE_PRIVATE)
        val gson = Gson()
        val jsonstring: String? = sharedPreferences.getString("School Survey List",null)
        val type = object : TypeToken<PendingClass?>() {}.type


        //pendingClass = gson.fromJson(jsonstring,type)
        var pc  = PendingClass()
        pc = gson.fromJson(jsonstring,type)
        Log.d("Log", "loadData: ${pc.schoolId} ${pc.records.officerId}")

        if(pc == null)
        {
            Toast.makeText(requireContext(), "Pending class is null", Toast.LENGTH_SHORT).show()
        }

        return pc




    }





}