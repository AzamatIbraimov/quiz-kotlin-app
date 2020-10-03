package kg.midterm.quizapp.DataAccess;

import android.provider.BaseColumns;

public class QuizReaderContract private constructor(){
    // // Table contents are grouped together in an anonymous object.
    class QuizEntry private constructor() :BaseColumns {
        companion object {
            val QUIZ_ID = BaseColumns._ID
            const val TABLE_NAME = "Quiz"
            const val COLUMN_QUESTION = "Question"
            const val OPTION_ONE = "option_one"
            const val OPTION_TWO = "option_two"
            const val OPTION_THREE = "option_three"
            const val OPTION_FOUR = "option_four"
            const val CORRECT_ANSWER = "correct_answer"
        }
    }
}

// Sqlite databases uses in this app, comes with an _id column that autoincrements and can function as a primary key.
