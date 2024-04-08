package com.hobarb.molia.ui

import TitleDetails
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.hobarb.molia.R
import com.hobarb.molia.adapters.SearchedTitlesAdapter
import com.hobarb.molia.databinding.ActivitySaveBinding
import com.hobarb.molia.interfaces.OnItemClickListener
import com.hobarb.molia.models.dtos.SaveTitleModel
import com.hobarb.molia.services.ApiHandler
import com.hobarb.molia.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SaveActivity : AppCompatActivity(), OnItemClickListener<JsonObject> {
    private lateinit var binding: ActivitySaveBinding
    private lateinit var searchedTitles: JsonArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_save)
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }*/
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        btnSubmitSetup()
        svTitlesSetup()
    }

    private fun btnSubmitSetup() {
        binding.btnSubmit.setOnClickListener {
            try {
                //Helpers().showLoader()
                //handleSubmitBtn()
                //callSearchApi("something")

            } catch (ex: Exception) {
                //Helpers().hideLoader()
                print("ssmsm")
            }
        }
    }

    private fun handleSubmitBtn(titleDetails: TitleDetails) {
        ApiHandler().saveTitle(
            body = SaveTitleModel(
                user_id = Constants.USER_ID,
                collection = binding.etCollection.text.toString(),
                details = titleDetails
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

    private var searchJob: Job? = null

    private fun svTitlesSetup() {
        binding.svTitle.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    handleSearchEvent(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 0) {
                    newText.let { text ->
                        searchJob?.cancel() // Cancel the previous search job if any
                        searchJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(300) // Debounce time in milliseconds
                            handleSearchEvent(text)
                        }
                    }

                } else {
                    searchedTitles.removeAll { true }
                    binding.rvSearchedTitles.adapter?.notifyDataSetChanged()
                }

                return false
            }


        })
    }

    private fun rvSearchedTitlesSetup(titles: JsonArray?) {
        binding.rvSearchedTitles.layoutManager = LinearLayoutManager(this)
        binding.rvSearchedTitles.adapter = titles?.let { SearchedTitlesAdapter(this, this, it) }
    }

    private fun handleSearchEvent(s: String) {
        try {
            ApiHandler().searchTitles(s) { success, message, data ->
                //hideLoader() // Hide loader after API call is completed
                if (success) {
                    if (data != null) {
                        searchedTitles = data
                        rvSearchedTitlesSetup(searchedTitles)
                    }
                    //Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                    // Proceed to next step
                } else {
                    Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
                    // Handle failure case
                }
            }

        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }

    }

    override fun onItemClick(item: JsonObject) {
        val titleDetails: TitleDetails = Gson().fromJson(item, TitleDetails::class.java)
        handleSubmitBtn(titleDetails)
    }


}