package kg.midterm.quizapp.DataAccess

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kg.midterm.quizapp.Models.Quiz

public class QuizReaderDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Table for Database
        this.db = db!!
        val SQL_CREATE_ENTRIES: String =
            "CREATE TABLE ${QuizReaderContract.QuizEntry.TABLE_NAME} (" +
                    "${QuizReaderContract.QuizEntry.QUIZ_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${QuizReaderContract.QuizEntry.COLUMN_QUESTION} TEXT," +
                    "${QuizReaderContract.QuizEntry.OPTION_ONE} TEXT,"+
                    "${QuizReaderContract.QuizEntry.OPTION_TWO} TEXT,"+
                    "${QuizReaderContract.QuizEntry.OPTION_THREE} TEXT,"+
                    "${QuizReaderContract.QuizEntry.OPTION_FOUR} TEXT,"+
                    "${QuizReaderContract.QuizEntry.CORRECT_ANSWER} INTEGER)"
        db.execSQL(SQL_CREATE_ENTRIES)

        // Insert Data in the Table
        createQuestions()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db!!.execSQL(QuizReaderContract.QuizEntry.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }

    private fun createQuestions(){
        val question_one = Quiz("What is my Name", "Z", "Zaa", "Zaiyr", "Zaha", 3)
        val question_two = Quiz("2 + 2", "1", "5", "4", "6", 3)

        // Put information into database
        putQuestion(question_one)
        putQuestion(question_two)
    }

    private fun putQuestion(quiz: Quiz){
        // Create a new map of values, where column names are the keys
        val values: ContentValues = ContentValues().apply {
            put(QuizReaderContract.QuizEntry.COLUMN_QUESTION, quiz.getQuestion())
            put(QuizReaderContract.QuizEntry.OPTION_ONE, quiz.getOptionalOne())
            put(QuizReaderContract.QuizEntry.OPTION_TWO, quiz.getOptionalTwo())
            put(QuizReaderContract.QuizEntry.OPTION_THREE, quiz.getOptionalThree())
            put(QuizReaderContract.QuizEntry.OPTION_FOUR, quiz.getOptionalFour())
            put(QuizReaderContract.QuizEntry.CORRECT_ANSWER, quiz.getCorrectAnswer())
        }

        // Insert the new row
        db.insert(QuizReaderContract.QuizEntry.TABLE_NAME, null, values)
    }

    public fun getAllQuestions(): ArrayList<Quiz> {
        val questionsArrayList = ArrayList<Quiz>()
        db = readableDatabase
        val cursor: Cursor = db.rawQuery("select * from ${QuizReaderContract.QuizEntry.TABLE_NAME}",null);

        if (cursor.moveToFirst()) {
            do {
                //Parcelable object
                val quiz = Quiz()
                quiz.setQuestion(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.COLUMN_QUESTION)))
                quiz.setOptionOne(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.OPTION_ONE)))
                quiz.setOptionTwo(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.OPTION_TWO)))
                quiz.setOptionThree(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.OPTION_THREE)))
                quiz.setOptionFour(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.OPTION_FOUR)))
                quiz.setCorrectAnswer(cursor.getInt(cursor.getColumnIndex(QuizReaderContract.QuizEntry.CORRECT_ANSWER)))

                questionsArrayList.add(quiz)
            } while (cursor.moveToNext())
        }
        cursor.close()
        println("QUESTION ARRAY LIST ----> $questionsArrayList")
        return questionsArrayList
    }
}