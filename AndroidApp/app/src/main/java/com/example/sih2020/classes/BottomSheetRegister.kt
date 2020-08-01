package com.example.sih2020.classes

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.sih2020.R
import com.example.sih2020.dbClasses.Users
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class BottomSheetRegister : BottomSheetDialogFragment(),View.OnClickListener {

    private val CAMERA_REQUEST: Int = 1888
    lateinit var name: TextInputEditText
    lateinit var phone: TextInputEditText
    lateinit var buttonRegister: MaterialButton
    lateinit var buttonImage: MaterialButton
    lateinit var photo : Bitmap
    var imageFound :Boolean = false
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val mRef = firebaseDatabase.reference.child("users")
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val sRef = firebaseStorage.reference.child("userImage")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.bottomsheetregister,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.edittext_userName)
        phone = view.findViewById(R.id.edittext_phoneNumber)
        buttonRegister = view.findViewById(R.id.ButtonRegister)
        buttonImage = view.findViewById(R.id.ButtonImage)
        buttonImage.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.ButtonImage->apply {
                takeImage()
            }

            R.id.ButtonRegister->apply {
                if(imageFound){
                    biometricAuthenticate()

                }else{
                    takeImage()
                }
            }
        }
    }



    private fun biometricAuthenticate() {
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
                    CollectDetails()
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

        var promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login-Monitoring App")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()


        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        biometricPrompt.authenticate(promptInfo)
    }

    private fun CollectDetails() {
        val user = Users()
        var validInput = true
        if(phone.text.isNullOrEmpty() || phone.text.isNullOrBlank()){
            phone.error = "Enter Valid Phone Number"
            validInput = false
        }
        if(name.text.isNullOrEmpty() || name.text.isNullOrBlank()){
            name.error = "Enter Valid Name"
            validInput = false
        }
        if(validInput){
            user.phone = phone.text.toString()
            user.name = name.text.toString()
            user.deviceId = Settings.Secure.getString(this.requireContext().contentResolver,Settings.Secure.ANDROID_ID)
            Log.d("Device Id" , "CollectDetails: ${user.deviceId} ")
            authenticateUser(user)
        }



    }

    private fun authenticateUser(user: Users) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        val dialog: AlertDialog = builder.create()
        dialog.setTitle("Checking with db")
        dialog.show()
        val dbListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.children.any {
                    it.key == user.deviceId
                }){
                    Log.d(TAG, "onDataChange: Duplicate found")
                    dialog.dismiss()
                    Toast.makeText(requireContext(),"Already registered on this device",Toast.LENGTH_LONG).show()
                    this@BottomSheetRegister.dismiss()
                }else{
                    Log.d(TAG, "onDataChange: Duplicate Not Found")
                    dialog.dismiss()
                    uploadImageAndRegister(photo,user)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
                dialog.dismiss()
            }
        }
        mRef.addListenerForSingleValueEvent(dbListener)
    }

    private fun uploadImageAndRegister(photo: Bitmap, user : Users) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        val dialog: AlertDialog = builder.create()
        dialog.setTitle("Uploading Image")
        dialog.show()

        val baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = sRef.child(user.deviceId).putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d(TAG, "uploadImage: Error")
            dialog.dismiss()
            Toast.makeText(requireContext(),"Error uploading Image",Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Log.d(TAG, "uploadImage: Success + ${it.metadata}")
            mRef.child(user.deviceId).setValue(user)
            dialog.dismiss()
            Toast.makeText(requireContext(),"Registration Successful",Toast.LENGTH_LONG).show()
            this@BottomSheetRegister.dismiss()
        }
    }

    private fun takeImage() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED) {
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
            photo= data?.extras?.get("data") as Bitmap
            imageFound = true
        }

    }



}