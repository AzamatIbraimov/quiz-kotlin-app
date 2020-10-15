package kg.midterm.quizapp.Viewers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kg.midterm.quizapp.R
import kg.midterm.quizapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showScoreOfUser()

        // check if the user finished the quiz then show the button "SEE THE QUESTIONS"
        val scoreComment: String? = intent.getStringExtra(Constants.EXTRA_QUIZ_FINISHED)
        if (scoreComment != "FINISHED") {
            buttonOpenQuestions.visibility = View.INVISIBLE;
        }

        buttonStartQuiz.setOnClickListener { view ->
            val intent = Intent(view.context, QuizActivity::class.java)
            view.context.startActivity(intent)
        }

        buttonOpenQuestions.setOnClickListener { view ->
            val intent = Intent(view.context, QuestionsActivity::class.java)
            view.context.startActivity(intent)
        }
    }

    // get the result of user from quiz
    private fun showScoreOfUser(){
        val score: Int = intent.getIntExtra(Constants.EXTRA_SCORE, 0)
        val scoreComment: String? = intent.getStringExtra(Constants.EXTRA_SCORE_COMMENTS)
        textViewBestScore.text = "Your score is: $score"
        textViewScoreComment.text = scoreComment
    }
}




