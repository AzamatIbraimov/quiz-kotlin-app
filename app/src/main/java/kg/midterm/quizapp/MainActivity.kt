package kg.midterm.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kg.midterm.quizapp.DataAccess.QuizReaderDBHelper
import kg.midterm.quizapp.Models.Quiz
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.questions_content.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showScoreOfUser()

        buttonStartQuiz.setOnClickListener { view ->
            val intent = Intent(view.context, QuizActivity::class.java)
            view.context.startActivity(intent)
        }

        buttonOpenQuestions.setOnClickListener { view ->
            val intent = Intent(view.context, QuestionsActivity::class.java)
            view.context.startActivity(intent)
        }
    }

    private fun showScoreOfUser(){
        val score: Int = intent.getIntExtra("EXTRA_SCORE", 0)
        val scoreComment: String? = intent.getStringExtra("EXTRA_SCORE_COMMENTS")
        textViewBestScore.text = "Your score is: $score"
        textViewScoreComment.text = scoreComment
    }
}




