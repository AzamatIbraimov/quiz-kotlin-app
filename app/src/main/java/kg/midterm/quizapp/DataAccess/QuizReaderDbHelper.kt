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
                    "${QuizReaderContract.QuizEntry.COLUMN_QUESTION} TEXT NOT NULL UNIQUE," +
                    "${QuizReaderContract.QuizEntry.OPTION_ONE} TEXT NOT NULL,"+
                    "${QuizReaderContract.QuizEntry.OPTION_TWO} TEXT NOT NULL,"+
                    "${QuizReaderContract.QuizEntry.OPTION_THREE} TEXT NOT NULL,"+
                    "${QuizReaderContract.QuizEntry.OPTION_FOUR} TEXT NOT NULL,"+
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
        const val DATABASE_NAME = "QuizReader.db"
    }

    private fun createQuestions(){
        val questionOne = Quiz("Who is the developer of Kotlin?", "Google", "IntelliJ IDEA", "Eclipse", "JetBrains", 4)
        val questionTwo = Quiz("Which of following is used to handle null exceptions in Kotlin?", "Range", "Sealed Class", "Elvis Operator", "Lambda function", 3)
        val questionThree = Quiz("Which file extension is used to save Kotlin files.", ".java", ".kot", ".kt or .kts", ".andriod", 3)
        val questionFour = Quiz("All classes in Kotlin classes are by default?", "public", "final", "sealed", "abstract", 2)
        val questionFive = Quiz("What is correct way to create an arraylist in Kotlin?", "val list = arrayListOf(1, 2, 3)", "enum class Color {RED, GREEN, BLUE}", "val map = hashMapOf(1 to \"one\", 2 to \"two\", 3 to \"three\")", "val set = hashSetOf(1, 2, 3)", 1)
        val questionSix = Quiz("Which of the followings constructors are available in Kotlin?", "Primary constructor", "Secondary constructor", "None of the above", "Both 1 & 2", 4)
        val questionSeven = Quiz("Which of the following extension methods are used in Kotlin?", "Read texts () & Headlines ()", "Buffer reader ()", "Read each line ()", "All of the above", 4)
        val questionEight = Quiz("Which of the following is not the basic data types in Kotlin?", "Lists", "Numbers", "Strings", "Arrays", 1)
        val questionNine = Quiz("Kotlin was developed under the .......... license.", "Apache 1.0", "Apache 2.0", "Apache 1.1", "None of the above", 2)
        val questionTen = Quiz("You can create an emulator to simulate the configuration of a particular type of Android device using a tool like ___.", "Theme Editor", "AVD Manager", "Android SDK Manager", "None of the above", 2)

        // Put information into database
        putQuestion(questionOne)
        putQuestion(questionTwo)
        putQuestion(questionThree)
        putQuestion(questionFour)
        putQuestion(questionFive)
        putQuestion(questionSix)
        putQuestion(questionSeven)
        putQuestion(questionEight)
        putQuestion(questionNine)
        putQuestion(questionTen)
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
        return questionsArrayList
    }
}