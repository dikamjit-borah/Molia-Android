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
import com.hobarb.molia.models.schemas.SearchedTitle
import com.hobarb.molia.services.ApiHandler
import com.hobarb.molia.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SaveActivity : AppCompatActivity(), OnItemClickListener<SearchedTitle> {
    private lateinit var binding: ActivitySaveBinding
    private var searchedTitles: MutableList<SearchedTitle> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
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

    private fun rvSearchedTitlesSetup(titles: MutableList<SearchedTitle>) {
        binding.rvSearchedTitles.layoutManager = LinearLayoutManager(this)
        binding.rvSearchedTitles.adapter = SearchedTitlesAdapter(this, this, titles)
    }

    private fun handleSearchEvent(s: String) {
        try {
            ApiHandler().searchTitles(s) { success, message, data ->
                //hideLoader() // Hide loader after API call is completed
                if (success) {
                    if (data != null) {
                        searchedTitles = data as MutableList<SearchedTitle>
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

    fun setupEts(data: JsonObject) {

    }

    private fun clearSearchedTitles(){
        searchedTitles.clear()
        binding.rvSearchedTitles.adapter?.notifyDataSetChanged()
    }

    override fun onItemClick(item: SearchedTitle) {
        clearSearchedTitles()
        fetchTitleDetails(item)
    }

    private fun fetchTitleDetails(title: SearchedTitle){
        ApiHandler().fetchTitleDetails(title.imdbID) { success, message, data ->
            if (success) {
                if (data != null) {
                    Toast.makeText(baseContext, "hahahah" + data, Toast.LENGTH_SHORT).show()
                    setupEts(data)
                }
            } else {
                Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
            }
        }
    }

}