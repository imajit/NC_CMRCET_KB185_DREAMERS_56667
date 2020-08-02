package com.example.sih2020.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import android.widget.TextView

import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.sih2020.R
import com.example.sih2020.classes.BottomSheetDialog
import com.example.sih2020.classes.BottomSheetRegister
import com.example.sih2020.utils.Constants
import com.example.sih2020.utils.loadData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView


class Homepage : Fragment(), View.OnClickListener {


    var navController: NavController? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        view.findViewById<MaterialCardView>(R.id.cardview_newSurvey).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.cardview_oldsurvey).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.seePreviousSurvey).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.RegisterUser).setOnClickListener(this)
        view.findViewById<TextView>(R.id.textGrid).setOnClickListener(this)


        /***
         * TODO implement callBack from the bottomsheet to get the parameters and authenticate the user to the next screen
          ***/







    }




    override fun onClick(v: View?) {

        when(v!!.id)
        {
            R.id.cardview_newSurvey->apply {
               fragmentManager?.let {
                   BottomSheetDialog().show(
                       it,
                       ""
                   )
               }
            }

            R.id.RegisterUser->apply {
                fragmentManager?.let {
                    BottomSheetRegister().show(
                        it,
                        ""
                    )
                }
            }

            R.id.cardview_oldsurvey-> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setCancelable(false)
                builder.setView(R.layout.progress_dialog)
                val dialog: AlertDialog = builder.create()
                dialog.setTitle("Loading . . .")
                dialog.show()
                Constants.pendingSurveys = loadData(requireContext())
                object : CountDownTimer(500, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        dialog.dismiss()
                        if(Constants.pendingSurveys.schoolId.isEmpty()){
                            Toast.makeText(context,"No Surveys Found",Toast.LENGTH_LONG).show()
                        }else {
                            navController!!.navigate(R.id.HomeToPendingSurvey)
                        }
                    }
                }.start()



            }

            R.id.seePreviousSurvey->navController!!.navigate(R.id.homeToQuestionList)

        R.id.textGrid->navController!!.navigate(R.id.Test)


        }
    }


}