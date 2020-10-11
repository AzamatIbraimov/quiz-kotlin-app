package kg.midterm.quizapp.Models

import android.os.Parcel
import android.os.Parcelable

class Quiz() : Parcelable {
    private var question: String = ""
    private var option_one: String = ""
    private var option_two: String = ""
    private var option_three: String = ""
    private var option_four: String = ""
    private var correct_answer: Int = 0

    constructor(question: String, option_one: String, option_two: String, option_three: String, option_four: String, correct_answer: Int) : this() {
        this.question = question
        this.option_one = option_one
        this.option_two = option_two
        this.option_three = option_three
        this.option_four = option_four
        this.correct_answer = correct_answer
    }

    fun getQuestion(): String {
        return question
    }

    fun setQuestion(question: String) {
        this.question = question
    }

    fun getOptionalOne(): String {
        return option_one
    }

    fun setOptionOne(option_one: String) {
        this.option_one = option_one
    }

    fun getOptionalTwo(): String {
        return option_two
    }

    fun setOptionTwo(option_two: String) {
        this.option_two = option_two
    }

    fun getOptionalThree(): String {
        return option_three
    }

    fun setOptionThree(option_three: String) {
        this.option_three = option_three
    }

    fun getOptionalFour(): String {
        return option_four
    }

    fun setOptionFour(option_four: String) {
        this.option_four = option_four
    }

    fun getCorrectAnswer(): Int {
        return correct_answer
    }

    fun setCorrectAnswer(correct_answer: Int) {
        this.correct_answer = correct_answer
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(question)
        parcel.writeString(option_one)
        parcel.writeString(option_two)
        parcel.writeString(option_three)
        parcel.writeString(option_four)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

}