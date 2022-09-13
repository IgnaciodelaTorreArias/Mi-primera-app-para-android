package delaTorre.Arias.Ignaci.quiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel(){
    private val questionBank = listOf<Question>(
        Question(R.string.question_welcome, true),
        Question(R.string.question_biggest, true),
        Question(R.string.question_pizza, false),
        Question(R.string.question_google, false),
        Question(R.string.question_colores, false)
    )
    private var answeredQuestions = mutableListOf<Question>()
    private var currentIndex: Int = 0
    private var correct:Int = 0
    fun userAnswer(): Boolean {
        val responded = answeredQuestions.indexOf(questionBank[currentIndex])
        if (responded != -1){
            return answeredQuestions[responded].answer
        }
        return false
    }
    val points:Int
        get() = correct
    val currentQuestion:Question
        get() = questionBank[currentIndex]
    fun next() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun previous(){
        currentIndex = ((currentIndex + questionBank.size) - 1) %questionBank.size
    }
    fun done():Boolean{
        return answeredQuestions.contains(questionBank[currentIndex])
    }
    fun validate(answer:Boolean):Boolean{
        answeredQuestions.add(questionBank[currentIndex])
        if (questionBank[currentIndex].answer == answer){
            answeredQuestions.last().answer = true
            correct += 1
            return true
        }
        answeredQuestions.last().answer = false
        return false
    }
}