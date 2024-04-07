package com.hobarb.molio.ui

import TitleDetails
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.JsonElement
import com.hobarb.molio.R
import com.hobarb.molio.databinding.ActivitySaveBinding
import com.hobarb.molio.models.dtos.SaveTitleModel
import com.hobarb.molio.models.schemas.SearchTitle
import com.hobarb.molio.services.ApiHandler
import com.hobarb.molio.utils.Constants
import kotlinx.coroutines.runBlocking

class SaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_save)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        svTitlesSetup()

        binding.btnSubmit.setOnClickListener {
            try {
                //Helpers().showLoader()
                //handleSubmitBtn()
                callSearchApi("something")

            } catch (ex: Exception) {
                //Helpers().hideLoader()
                print("ssmsm")
            }
        }
    }

    private fun handleSubmitBtn() {
        ApiHandler().saveTitle(
            body = SaveTitleModel(
                user_id = Constants.USER_ID,
                collection = binding.etCollection.text.toString(),
                details = TitleDetails(Title = binding.etTitle.text.toString())
            )
        ) { success, message, data ->
            //hideLoader() // Hide loader after API call is completed
            if (success) {
                Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                // Proceed to next step
            } else {
                Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                // Handle failure case
            }
        }
    }

    private fun svTitlesSetup() {
        binding.svTitles.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val searchResults =  (query)
                    if (searchResults != null) {
                       // setup_rvSearchResults(searchResults)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    val searchResults = callSearchApi(newText)
                    if (searchResults != null) {
                        //setup_rvSearchResults(searchResults)
                    }
                }
                return false
            }
        })
    }

    private fun callSearchApi(s: String) {
            try {
                ApiHandler().searchTitles(s) { success, message, data ->
                    //hideLoader() // Hide loader after API call is completed
                    if (success) {
                        Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                        // Proceed to next step
                    } else {
                        Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                        // Handle failure case
                    }
                }

            }catch (exception: java.lang.Exception){
                exception.printStackTrace()
            }

    }


}