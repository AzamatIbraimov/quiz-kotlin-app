package kg.midterm.quizapp.Viewers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import kg.midterm.quizapp.Models.Quiz
import kg.midterm.quizapp.R

class QuestionsAdapter (listArray: ArrayList<Quiz>, context: Context): RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {
    private var listArrayR = listArray
    private var contextR = context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val questionName_textView = view.findViewById<TextView>(R.id.questionName_textView)

        // fill in layout
        fun bind(listItem: Quiz, context: Context) {
            val correctAnswer: Int = listItem.getCorrectAnswer()
            val questionOptionsArray: List<String> = listOf<String>(listItem.getOptionalOne(), listItem.getOptionalTwo(), listItem.getOptionalThree(), listItem.getOptionalFour())

            questionName_textView.text = listItem.getQuestion()
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    "The correct answer is : ${questionOptionsArray[correctAnswer - 1]}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    // draw view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.question_item, parent, false))
    }

    // run bind() function
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem: Quiz = listArrayR[position]
        holder.bind(listItem, contextR)
    }

    // get the size of elements
    override fun getItemCount(): Int {
        return listArrayR.size
    }
}