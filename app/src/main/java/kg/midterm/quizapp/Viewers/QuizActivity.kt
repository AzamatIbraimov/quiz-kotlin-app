package kg.midterm.quizapp.Viewers

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import kg.midterm.quizapp.DataAccess.QuizReaderDBHelper
import kg.midterm.quizapp.Models.Quiz
import kg.midterm.quizapp.R
import kg.midterm.quizapp.utils.Constants
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.ArrayList

class QuizActivity : AppCompatActivity() {

    private var questionsArrayList: ArrayList<Quiz> = ArrayList()
    private var questionCounter: Int = 0
    private var totalNumberOfQuestions: Int = 0
    private lateinit var currentQuestion: Quiz
    private var isAnswered: Boolean = false
    private var score: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState == null) {
            // get the array list of questions
            val quizDbHelper = QuizReaderDBHelper(this)
            questionsArrayList = quizDbHelper.getAllQuestions()

            // get the total number of questions in array list
            totalNumberOfQuestions = questionsArrayList.size
            // show the total number of questions in progress bar
            progressBar.max = totalNumberOfQuestions

            // questions in the quiz will be in randomly order
            questionsArrayList.shuffle()
            showQuestion()

        } else {
            currentQuestion = questionsArrayList[questionCounter - 1]
        }

        button_next.setOnClickListener {
            answer_textView.text = ""
            if (!isAnswered){
                if(option_one.isChecked || option_two.isChecked || option_three.isChecked || option_four.isChecked){
                    checkAnswer()
                } else {
                    Toast.makeText(this, "Please choose the answer", Toast.LENGTH_LONG).show()
                }
            } else {
                showQuestion()
            }
        }
    }

    private fun showQuestion() {
        option_one.setTextColor(Color.parseColor("#000000"))
        option_two.setTextColor(Color.parseColor("#000000"))
        option_three.setTextColor(Color.parseColor("#000000"))
        option_four.setTextColor(Color.parseColor("#000000"))
        radio_group.clearCheck()

        if(questionCounter < totalNumberOfQuestions) {
            currentQuestion = questionsArrayList[questionCounter]
            question_textView.text = currentQuestion.getQuestion()
            option_one.text = currentQuestion.getOptionalOne()
            option_two.text = currentQuestion.getOptionalTwo()
            option_three.text = currentQuestion.getOptionalThree()
            option_four.text = currentQuestion.getOptionalFour()

            questionCounter++
            progressBar.progress = questionCounter
            question_number_textView.text = "$questionCounter / $totalNumberOfQuestions"

            isAnswered = false
            button_next.text = "Answer"
        } else {
            returnToMainMenu()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkAnswer(){
        isAnswered = true

        val selectedAnswer: RadioButton = findViewById(radio_group.checkedRadioButtonId)

        // index starts with 0 ( +1 added )
        val answerNumber: Int = radio_group.indexOfChild(selectedAnswer) + 1

        // add +1 score if chosen option is correct
        if(answerNumber == currentQuestion.getCorrectAnswer()){
            score++
            score_textView.text = "Score: ${score.toString()}"
        } else {
            showIncorrectAnswer(answerNumber)
        }
        showCorrectAnswer()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showCorrectAnswer() {
        when(currentQuestion.getCorrectAnswer()){
            1 -> {
                option_one.setTextColor(getColor(R.color.correct_answer_color))
                answer_textView.text = "The correct answer is '${currentQuestion.getOptionalOne()}'"
            }
            2 -> {
                option_two.setTextColor(getColor(R.color.correct_answer_color))
                answer_textView.text = "The correct answer is '${currentQuestion.getOptionalTwo()}'"
            }
            3 -> {
                option_three.setTextColor(getColor(R.color.correct_answer_color))
                answer_textView.text = "The correct answer is '${currentQuestion.getOptionalThree()}'"
            }
            4 -> {
                option_four.setTextColor(getColor(R.color.correct_answer_color))
                answer_textView.text = "The correct answer is '${currentQuestion.getOptionalFour()}'"
            }
        }

        if (questionCounter < totalNumberOfQuestions) {
            button_next.text = "Next"
        } else {
            button_next.text = "Finish the Quiz"
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showIncorrectAnswer(answerNumber: Int) {
        when(answerNumber){
            1 -> {
                option_one.setTextColor(getColor(R.color.incorrect_answer_color))
            }
            2 -> {
                option_two.setTextColor(getColor(R.color.incorrect_answer_color))
            }
            3 -> {
                option_three.setTextColor(getColor(R.color.incorrect_answer_color))
            }
            4 -> {
                option_four.setTextColor(getColor(R.color.incorrect_answer_color))
            }
        }
    }

    private fun returnToMainMenu() {
        val intent: Intent = Intent(this, MainActivity::class.java).apply {
            putExtra(Constants.EXTRA_SCORE, score)
            when (score) {
                0, 1, 2, 3, 4 -> putExtra(Constants.EXTRA_SCORE_COMMENTS, "Bad result. Try hard!")
                5, 6 -> putExtra(Constants.EXTRA_SCORE_COMMENTS, "Not bad. But you can do better :)")
                7, 8 -> putExtra(Constants.EXTRA_SCORE_COMMENTS, "Good result!")
                9, 10 -> putExtra(Constants.EXTRA_SCORE_COMMENTS, "Awesome result! You're incredible!")
            }
            putExtra(Constants.EXTRA_QUIZ_FINISHED, "FINISHED")
        }

        startActivity(intent)
    }
}