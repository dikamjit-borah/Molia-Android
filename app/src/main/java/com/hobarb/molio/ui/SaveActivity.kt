package com.hobarb.molio.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hobarb.molio.R
import com.hobarb.molio.adapters.TitlesAdapter
import com.hobarb.molio.databinding.ActivitySaveBinding
import com.hobarb.molio.models.SaveTitleModel
import com.hobarb.molio.models.TitleModel
import com.hobarb.molio.network.RetrofitClient
import com.hobarb.molio.services.ApiHandler
import com.hobarb.molio.utils.Constants
import com.hobarb.molio.utils.Helpers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        binding.submitBtn.setOnClickListener {
            try {
                //Helpers().showLoader()
                handleSubmitBtn()

            } catch (ex: Exception) {
                //Helpers().hideLoader()
            }
        }
    }

    private fun handleSubmitBtn() {
        ApiHandler().saveTitle(
            body = SaveTitleModel(
                "some_user",
                binding.collectionEt.text.toString(),
                null,
                TitleModel(binding.titleEt.text.toString())
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


}