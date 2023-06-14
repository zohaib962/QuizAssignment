package com.example.assignmentproject.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmentproject.R
import com.example.assignmentproject.databinding.ActivityQuestionAnswerBinding
import com.example.assignmentproject.models.Question
import com.example.assignmentproject.viewModels.QuestionAnswerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionAnswerActivity : AppCompatActivity() {

    private val viewModel: QuestionAnswerViewModel by viewModels()
    lateinit var binding: ActivityQuestionAnswerBinding
    private var currentQuestionIndex = 0
    private var score = 0
    private var timeLeft = 0L
    private lateinit var handler: Handler
    private lateinit var quizQuestions: List<Question>
    private var totalCorrectAnswersCount = 0
    private var correctAnswersCount = 0

    val timeLimitInMillis = 10000L
    var timer = object : CountDownTimer(timeLimitInMillis, 100) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeft = millisUntilFinished
            binding.progressbar.progress = (timeLimitInMillis - millisUntilFinished).toInt()
        }

        override fun onFinish() {
            currentQuestionIndex++
            displayQuestion()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchDataFromDb()
        setObservers()
    }

    private fun setObservers() {
        viewModel.data.observe(this) {
            quizQuestions = it
            handler = Handler(Looper.getMainLooper())

            startQuiz()
        }

        viewModel.dataInserted.observe(this) {
            restartApp()
        }
    }

    private fun startQuiz() {
        // Reset variables
        currentQuestionIndex = 0
        score = 0

        // Display the first question
        displayQuestion()
    }

    private fun displayQuestion() {
        // Check if all questions have been answered
        if (currentQuestionIndex >= quizQuestions.size) {
            // Quiz finished
            showScore()
            return
        }

        enableAnswerSelection()

        binding.questionNumberTv.text = getString(R.string.question_number, currentQuestionIndex + 1, quizQuestions.size)
        binding.scoreTv.text = getString(R.string.current_score, score)

        val currentQuestion = quizQuestions[currentQuestionIndex]

        binding.questionText.text = currentQuestion.question
        binding.answersContainer.removeAllViews()

        if (currentQuestion.type.contains("single", true)) {
            displaySingleChoiceQuestion(currentQuestion)
        } else if (currentQuestion.type.contains("multiple", true)) {
            displayMultipleChoiceQuestion(currentQuestion)
        }

        startCountdownTimer()
    }

    private fun displaySingleChoiceQuestion(question: Question) {
        val answerOptions = mutableListOf<String>()
        val answerTags = mutableListOf<String>()

        // Add available answer options and tags to the lists
        if (question.answers.a.isNotEmpty()) {
            answerOptions.add(question.answers.a)
            answerTags.add("A")
        }
        if (question.answers.b.isNotEmpty()) {
            answerOptions.add(question.answers.b)
            answerTags.add("B")
        }
        if (question.answers.c.isNotEmpty()) {
            answerOptions.add(question.answers.c)
            answerTags.add("C")
        }
        if (question.answers.d.isNotEmpty()) {
            answerOptions.add(question.answers.d)
            answerTags.add("D")
        }
        if (question.answers.e.isNotEmpty()) {
            answerOptions.add(question.answers.e)
            answerTags.add("E")
        }

        for (i in answerOptions.indices) {
            val answerButton = Button(this)
            answerButton.text = answerOptions[i]
            answerButton.tag = answerTags[i]
            answerButton.setOnClickListener { onSingleChoiceAnswerSelected(answerButton) }
            binding.answersContainer.addView(answerButton)
        }
    }

    private fun onSingleChoiceAnswerSelected(answerButton: Button) {
        disableAnswerSelection()

        val selectedAnswer = answerButton.tag.toString()

        val currentQuestion = quizQuestions[currentQuestionIndex]

        val isCorrect = selectedAnswer == currentQuestion.correctAnswer

        if (isCorrect) {
            answerButton.setBackgroundColor(Color.GREEN)
            score += currentQuestion.score
        } else {
            // Wrong answer
            answerButton.setBackgroundColor(Color.RED)

            // Find the correct answer button and highlight it
            for (i in 0 until binding.answersContainer.childCount) {
                val answerButton = binding.answersContainer.getChildAt(i) as Button
                if (answerButton.text.toString() == currentQuestion.correctAnswer) {
                    answerButton.setBackgroundColor(Color.GREEN)
                    break
                }
            }
        }

        // Move to the next question after a delay
        handler.postDelayed(
            {
                currentQuestionIndex++
                displayQuestion()
            },
            2000,
        )
    }

    private fun displayMultipleChoiceQuestion(question: Question) {
        val answerOptions = mutableListOf<String>()
        val answerTags = mutableListOf<String>()

        // Add available answer options and tags to the lists
        if (question.answers.a.isNotEmpty()) {
            answerOptions.add(question.answers.a)
            answerTags.add("A")
        }
        if (question.answers.b.isNotEmpty()) {
            answerOptions.add(question.answers.b)
            answerTags.add("B")
        }
        if (question.answers.c.isNotEmpty()) {
            answerOptions.add(question.answers.c)
            answerTags.add("C")
        }
        if (question.answers.d.isNotEmpty()) {
            answerOptions.add(question.answers.d)
            answerTags.add("D")
        }
        if (question.answers.e.isNotEmpty()) {
            answerOptions.add(question.answers.e)
            answerTags.add("E")
        }
        for (i in answerOptions.indices) {
            val answerButton = Button(this)
            answerButton.text = answerOptions[i]
            answerButton.tag = answerTags[i]
            answerButton.setOnClickListener { onMultipleChoiceAnswerSelected(answerButton) }
            binding.answersContainer.addView(answerButton)
        }

        totalCorrectAnswersCount = question.correctAnswer.replace(",", "").replace("\"", "").length
        correctAnswersCount = 0
    }

    private fun onMultipleChoiceAnswerSelected(answerButton: Button) {
        val currentQuestion = quizQuestions[currentQuestionIndex]

        val isCorrect = currentQuestion.correctAnswer.contains(answerButton.tag.toString())

        if (isCorrect) {
            correctAnswersCount++
            // Correct answer
            answerButton.setBackgroundColor(Color.GREEN)
        } else {
            // Wrong answer
            answerButton.setBackgroundColor(Color.RED)

            // Find the correct answer buttons and highlight them
            for (i in 0 until binding.answersContainer.childCount) {
                val correctAnswerButton = binding.answersContainer.getChildAt(i) as Button
                if (currentQuestion.correctAnswer.contains(correctAnswerButton.tag.toString())) {
                    correctAnswerButton.setBackgroundColor(Color.GREEN)
                }
            }

            handler.postDelayed(
                {
                    currentQuestionIndex++
                    displayQuestion()
                },
                2000,
            )
        }

        // Move to the next question after a delay
        if (correctAnswersCount == totalCorrectAnswersCount) {
            score += currentQuestion.score
            handler.postDelayed(
                {
                    currentQuestionIndex++
                    displayQuestion()
                },
                2000,
            )
        }
    }

    private fun startCountdownTimer() {
        binding.progressbar.max = timeLimitInMillis.toInt()
        timer.cancel()
        timer.start()
    }

    private fun stopCountdownTimer() {
        timer.cancel()
    }

    private fun enableAnswerSelection() {
        for (i in 0 until binding.answersContainer.childCount) {
            val answerButton = binding.answersContainer.getChildAt(i) as Button
            answerButton.isEnabled = true
        }
    }

    private fun disableAnswerSelection() {
        for (i in 0 until binding.answersContainer.childCount) {
            val answerButton = binding.answersContainer.getChildAt(i) as Button
            answerButton.isEnabled = false
        }
    }

    private fun showScore() {
        stopCountdownTimer()

        binding.scoreTv.text = getString(R.string.final_score, score)
        viewModel.insertScoreInDb(score)
    }

    private fun restartApp() {
        val ctx = applicationContext
        val pm = ctx.packageManager
        val intent = pm.getLaunchIntentForPackage(ctx.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
        ctx.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}
