package com.example.sih2020.classes

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.sih2020.R
import com.example.sih2020.dbClasses.gpsCoordinates
import com.example.sih2020.fragments.QuestionnaireFragment
import com.example.sih2020.utils.Constants
import com.example.sih2020.utils.LocationFinder
import com.example.sih2020.utils.Permissions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetDialog() : BottomSheetDialogFragment() {

    private lateinit var photo: Bitmap
    var imageFound :Boolean = false
    private val CAMERA_REQUEST: Int = 1888
    var database = FirebaseDatabase.getInstance().reference
    lateinit var createDate: String
    lateinit var sId: String
    lateinit var oId: String
    var gpsSnapshot: gpsCoordinates = gpsCoordinates()
    lateinit var oRev: String
    lateinit var loc: LocationFinder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.bottomsheet, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {
        view.findViewById<MaterialButton>(R.id.ButtonContinue).setOnClickListener {
            //Toast.makeText(context, "Check how to proceed to questionnaire", Toast.LENGTH_SHORT).show()
            loc = LocationFinder(requireContext())
            if (Permissions.checkPermissions(this.requireActivity()) && loc.canGetLocation() ) {
                //Toast.makeText(context, "Check how to proceed to questionnaire", Toast.LENGTH_SHORT).show()
                verifyInput(
                    view.findViewById(R.id.schoolId),
                    view.findViewById(R.id.officerId),
                    view.findViewById(R.id.overallReview)

                )
            }
            else{
                if(!loc.canGetLocation()){
                    loc.showSettingsAlert()
                }
            }
        }

        view.findViewById<MaterialButton>(R.id.ButtonImage).setOnClickListener{
            if(Permissions.checkPermissions(this.requireActivity()) ){
                takeImage()
            }
        }

    }

    private fun verifyInput(schoolId: TextInputEditText?, officerId: TextInputEditText?, overallReview: TextInputEditText?) {
        var valid = true
        if (schoolId!!.text.isNullOrEmpty()) {
            schoolId.error = "Cannot be blank"
            valid = false
        }
        if (officerId!!.text.isNullOrEmpty()) {
            officerId.error = "Cannot be blank"
            valid = false
        }
        if (overallReview!!.text.isNullOrEmpty()) {
            overallReview.error = "Cannot be blank"
            valid = false
        }
        if(valid){

            if(!imageFound) {
                takeImage()
            }
            else{
                sId = schoolId.text.toString()
                oId = Settings.Secure.getString(this.requireContext().contentResolver, Settings.Secure.ANDROID_ID)
                oRev = overallReview.text.toString()
                gpsSnapshot.lat = loc.getLatitude()
                gpsSnapshot.long = loc.getLongitude()
                createDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
                verifyFingerprint()
            }
        }

    }

    private fun takeImage() {
        if (checkSelfPermission(this.requireContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    //result for takePhoto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            photo = data?.extras?.get("data") as Bitmap
            imageFound = true
        }

    }

    private fun verifyFingerprint(){
        var biometricPrompt = androidx.biometric.BiometricPrompt(this.requireActivity(),
            ContextCompat.getMainExecutor(this.requireActivity()),
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Auth error",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: androidx.biometric.BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireContext(),
                        "Authentication succeeded!\nStarting Transaction", Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        TAG,
                        "onAuthenticationSucceeded: " +
                                createDate + " " +sId+ " " + oId + " " + gpsSnapshot.lat
                    )
                    saveDataAndOpenQuestionnaire()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        var promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login-Monitoring App")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()


        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        biometricPrompt.authenticate(promptInfo)
    }

    private fun saveDataAndOpenQuestionnaire() {
        Constants.schoolId = sId
        Constants.officerId = oId
        Constants.gpsSnap = gpsSnapshot
        Constants.overallReview = oRev
        Constants.creationDate = createDate
        Constants.photo = photo
        this@BottomSheetDialog.dismiss()
        findNavController().navigate(R.id.homeToQuestionList)
    }


}