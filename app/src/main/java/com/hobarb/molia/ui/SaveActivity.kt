package com.hobarb.molia.ui

import TitleDetails
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var titleDetails: TitleDetails = TitleDetails()
    private var searchJob: Job? = null
    private var shouldExecuteQueryTextChange = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        svTitleSetup()
        btnSubmitSetup()
    }

    private fun svTitleSetup() {
        binding.svTitle.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (shouldExecuteQueryTextChange) {
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
                }
                return false
            }
        })
    }

    private fun handleSearchEvent(s: String) {
        try {
            ApiHandler().searchTitles(s) { success, message, data ->
                if (success) {
                    if (data != null) {
                        searchedTitles = data as MutableList<SearchedTitle>
                        rvSearchedTitlesSetup(searchedTitles)
                    }
                } else {
                    Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (exception: java.lang.Exception) {
            Toast.makeText(baseContext, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun rvSearchedTitlesSetup(titles: MutableList<SearchedTitle>) {
        binding.rvSearchedTitles.layoutManager = LinearLayoutManager(this)
        binding.rvSearchedTitles.adapter = SearchedTitlesAdapter(this, this, titles)
    }

    override fun onItemClick(item: SearchedTitle) {
        clearSearchedTitles()
        fetchTitleDetails(item)
    }

    private fun clearSearchedTitles() {
        searchedTitles.clear()
        binding.rvSearchedTitles.adapter?.notifyDataSetChanged()
    }

    private fun fetchTitleDetails(title: SearchedTitle) {
        ApiHandler().fetchTitleDetails(title.imdbID) { success, message, data ->
            if (success) {
                if (data != null) {
                    handleTitleFetchedEvent(data)
                }
            } else {
                Toast.makeText(baseContext, message + data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun handleTitleFetchedEvent(titleDetails: TitleDetails) {
        this.titleDetails = titleDetails
        setupEts(titleDetails)

    }

    fun setupEts(title: TitleDetails) {
        shouldExecuteQueryTextChange = false
        binding.svTitle.setQuery(title.Title, false)
        binding.etDirector.setText(title.Director)
        binding.etActors.setText(title.Actors)
        binding.etYear.setText(title.Year)
        binding.etLanguage.setText(title.Language)
        binding.etImdbRating.setText(title.imdbRating)
        shouldExecuteQueryTextChange = true
    }

    private fun btnSubmitSetup() {
        binding.btnSubmit.setOnClickListener {
            try {
                val details = getTitleDetails()
                submitDetails(
                    details,
                    binding.etCollection.text.toString(),
                    binding.etSubCollection.text.toString()
                )
            } catch (ex: Exception) {
                Toast.makeText(baseContext, ex.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getTitleDetails(): TitleDetails {
        this.titleDetails.Title = binding.svTitle.query.toString()
        this.titleDetails.Director = binding.etDirector.text.toString()
        this.titleDetails.Actors = binding.etActors.text.toString()
        this.titleDetails.Year = binding.etYear.text.toString()
        this.titleDetails.Language = binding.etLanguage.text.toString()
        this.titleDetails.imdbRating = binding.etImdbRating.text.toString()
        return this.titleDetails
    }

    private fun submitDetails(
        titleDetails: TitleDetails,
        collection: String,
        subCollection: String? = null
    ) {
        var body: SaveTitleModel
        if (subCollection.isNullOrEmpty()) {
            body = SaveTitleModel(
                user_id = Constants.USER_ID,
                collection = collection,
                details = titleDetails
            )
        } else {
            body = SaveTitleModel(
                user_id = Constants.USER_ID,
                collection = collection,
                sub_collection = subCollection,
                details = titleDetails
            )

        }

        ApiHandler().saveTitle(
            body = body
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


}