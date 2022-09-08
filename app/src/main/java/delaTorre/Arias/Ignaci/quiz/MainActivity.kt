package delaTorre.Arias.Ignaci.quiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.Placeholder
import androidx.core.widget.ListViewCompat
import delaTorre.Arias.Ignaci.quiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf<Question>(
        Question(R.string.question_welcome, true),
        Question(R.string.question_biggest, true),
        Question(R.string.question_pizza, false),
        Question(R.string.question_google, false),
        Question(R.string.question_colores, false)
    )
    private var answeredQuestions = mutableListOf<Question>()
    private var currentIndex: Int = 0
    private var points: Int = 0

    private fun updateQuestion(){
        currentIndex = (currentIndex+1) % questionBank.size
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        val answerIndex = answeredQuestions.indexOf(questionBank[currentIndex])
        if (answerIndex != -1){
            val message = if (answeredQuestions[answerIndex].answer) {
                getString(R.string.correct_snack) + "\n" + getString(R.string.Points, points)
            } else {
                getString(R.string.incorrect_snack) + "\n" + getString(R.string.Points, points)
            }
            binding.counterPoints.setText(message)
        }
    }
    private fun checkanswer(answer: Boolean, view: View){
        if (answeredQuestions.contains(questionBank[currentIndex])){
            updateQuestion()
            return
        }
        val message = if(questionBank[currentIndex].answer == answer) {
            R.string.correct_snack
        } else {
            R.string.incorrect_snack
        }
        val mySnack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        answeredQuestions.add(questionBank[currentIndex])
        if(questionBank[currentIndex].answer == answer) {
            points += 1
            mySnack.setBackgroundTint(getColor(R.color.green))
            answeredQuestions.last().answer = true
        }else{
            mySnack.setBackgroundTint(getColor(R.color.red))
            answeredQuestions.last().answer = false
        }
        mySnack.show()
        binding.counterPoints.setText(getString(R.string.Points, points))

        updateQuestion()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.questionTextView.setText(getString(R.string.question_welcome))

        binding.nextButton.setOnClickListener {
            updateQuestion()
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