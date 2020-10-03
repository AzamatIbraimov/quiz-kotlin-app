package kg.midterm.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.midterm.quizapp.DataAccess.QuizReaderDBHelper
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val dbHelper = QuizReaderDBHelper(this)
        textView1.text = dbHelper.getAllQuestions().toString()
    }
}