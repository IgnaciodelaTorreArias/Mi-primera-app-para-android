package delaTorre.Arias.Ignaci.quiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import delaTorre.Arias.Ignaci.quiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
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
    private fun checkanswer(answer: Boolean, view: View){
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
        Log.d(TAG, "onCreate(Bundle?) called")

        Log.d(TAG, "Gpt a QuizViewModel: $quizViewModel")

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
            checkanswer(true, view)
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkanswer(false, view)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}