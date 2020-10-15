package kg.midterm.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kg.midterm.quizapp.DataAccess.QuizReaderDBHelper
import kg.midterm.quizapp.Models.Quiz
import kotlinx.android.synthetic.main.questions_content.*

class QuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val quizDbHelper = QuizReaderDBHelper(this)
        val questionsArrayList: ArrayList<Quiz> = quizDbHelper.getAllQuestions()

        questions_recycler_view.hasFixedSize()
        questions_recycler_view.layoutManager = LinearLayoutManager(this)
        questions_recycler_view.adapter = QuestionsAdapter(questionsArrayList, this)
    }
}