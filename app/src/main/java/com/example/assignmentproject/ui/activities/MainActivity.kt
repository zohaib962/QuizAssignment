package com.example.assignmentproject.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmentproject.R
import com.example.assignmentproject.databinding.ActivityMainBinding
import com.example.assignmentproject.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        setObservers()

        viewModel.fetchScore()
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.data.observe(
            this,
        ) {
            if (it) {
                startActivity(Intent(this, QuestionAnswerActivity::class.java))
            }
        }

        viewModel.score.observe(this) {
            binding.highScoreTv.text = getString(R.string.high_score_is) + it
        }
    }
}
