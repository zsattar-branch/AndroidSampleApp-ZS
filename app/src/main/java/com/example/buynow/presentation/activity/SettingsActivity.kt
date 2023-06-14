package com.example.buynow.presentation.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.buynow.R
import com.example.buynow.utils.Extensions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.branch.referral.Branch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
class SettingsActivity : AppCompatActivity() {
    lateinit var nameEt_SettingsPage:EditText
    lateinit var EmailEt_SettingsPage:EditText
    lateinit var saveSetting_SettingsBtn:Button
    lateinit var newButton:Button
    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        nameEt_SettingsPage = findViewById(R.id.nameEt_SettingsPage)
        EmailEt_SettingsPage = findViewById(R.id.EmailEt_SettingsPage)
        saveSetting_SettingsBtn = findViewById(R.id.saveSetting_SettingsBtn)
        newButton =findViewById(R.id.DisableTracking_SettingsPage)
        newButton.setOnClickListener {
            // Call disable tracking
            Branch.getInstance().disableTracking(true)
            Branch.getInstance().disableTracking(false)
        }
        val backIv_ProfileFrag:ImageView = findViewById(R.id.backIv_ProfileFrag)

        backIv_ProfileFrag.setOnClickListener {
            onBackPressed()
        }
        getUserData()
        saveSetting_SettingsBtn.setOnClickListener {
            textCheck()
        }
        textAutoCheck()
    }
    private fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = userCollectionRef
                .document(firebaseAuth.uid.toString()