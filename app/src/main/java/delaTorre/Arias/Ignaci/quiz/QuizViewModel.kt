package delaTorre.Arias.Ignaci.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    private val questionBank = listOf<Question>(
        Question(R.string.question_welcome, true),
        Question(R.string.question_biggest, true),
        Question(R.string.question_pizza, false),
        Question(R.string.question_google, false),
        Question(R.string.question_colores, false)
    )
    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)
    private var answeredQuestions = mutableListOf<Question>()
    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
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