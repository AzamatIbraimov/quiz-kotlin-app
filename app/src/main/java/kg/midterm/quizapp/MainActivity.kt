package kg.midterm.quizapp

import android.R.id.button2
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kg.midterm.quizapp.DataAccess.QuizReaderDBHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var bestScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showBestScoreOfUser()

        buttonStartQuiz.setOnClickListener { view ->
            val intent = Intent(view.context, QuizActivity::class.java)
            view.context.startActivity(intent)
        }

    }

    private fun showBestScoreOfUser(){
        textViewBestScore.text = "Your best score is: $bestScore"
    }

}


