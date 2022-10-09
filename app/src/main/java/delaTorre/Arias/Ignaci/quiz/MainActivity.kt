package delaTorre.Arias.Ignaci.quiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
        binding.counterPoints.text = message
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
        val (message,color) = when {
            quizViewModel.isCheater -> message_color(R.string.judgement_toast, R.color.yellow)
            quizViewModel.validate(answer) -> message_color(R.string.correct_snack, R.color.green)
            else -> message_color(R.string.incorrect_snack, R.color.red)
        }
        val mySnack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        mySnack.setBackgroundTint(getColor(color))
        mySnack.show()
        nextQuestion()
    }
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
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
        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestion.answer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)

        }
    }
}