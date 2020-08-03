package com.example.sih2020.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.sih2020.R
import com.example.sih2020.utils.BaseFragment
import com.google.android.material.card.MaterialCardView

class QuestionnaireCategoryFragment : BaseFragment(), View.OnClickListener {


    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionnaire_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryInfra).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryAcademic).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryExtraCurricular).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryIndividualAttention).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryLifeSkills).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryFemale).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryDifferentlyAbled).setOnClickListener(this)
        view.findViewById<MaterialCardView>(R.id.MaterialCardViewQuestionCategoryValueEducation).setOnClickListener(this)




    }

    override fun onClick(view: View?) {
    }
    }


