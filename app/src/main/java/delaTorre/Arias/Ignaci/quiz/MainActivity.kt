package delaTorre.Arias.Ignaci.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import delaTorre.Arias.Ignaci.quiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel:QuizViewModel by viewModels()

    private fun updateQuestion(){
        val currentQuestion = quizViewModel.currentQuestion
        binding.questionTextView.setText(currentQuestion.textResId)
        var message:String = getString(R.string.Points, quizViewModel.points)
        if (quizViewModel.done()) {
            message = if (quizViewModel.userAnswer()) {
                getString(R.string.correct_snack) + "\n" + message
            } else {
                getString(R.string.incorrect_snack) + "\n" + message
            }
        }
        binding.counterPoints.setText(message)
    }
    private fun nextQuestion(){
        quizViewModel.next()
        updateQuestion()
    }
    private  fun beforeQuestion(){
        quizViewModel.previous()
        updateQuestion()
    }
    private fun checkAnswer(answer: Boolean, view: View){
        if (quizViewModel.done()){
            nextQuestion()
            return
        }
        val validation = quizViewModel.validate(answer)
        val message = if(validation) {
            R.string.correct_snack
        } else {
            R.string.incorrect_snack
        }
        val mySnack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        if(validation) {
            mySnack.setBackgroundTint(getColor(R.color.green))
        }else{
            mySnack.setBackgroundTint(getColor(R.color.red))
        }
        mySnack.show()
        nextQuestion()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        updateQuestion()

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }
        binding.beforeButton.setOnClickListener {
            beforeQuestion()
        }
        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true, view)
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false, view)
        }

    }
}