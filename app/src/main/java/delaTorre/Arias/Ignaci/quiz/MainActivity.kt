package delaTorre.Arias.Ignaci.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.ListViewCompat
import delaTorre.Arias.Ignaci.quiz.databinding.ActivityMainBinding

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

    private var currentIndex = 0
    private var points = 0

    private fun updateQuestion(){
        currentIndex = (currentIndex+1) % questionBank.size
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkanswer(answer: Boolean){
        val message = if(questionBank[currentIndex].answer == answer) {
            R.string.correct_toast
            points++
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.puntaje.setText(points)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            updateQuestion()
        }
        binding.trueButton.setOnClickListener { view: View ->
            checkanswer(true)
            updateQuestion()
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkanswer(false)
            updateQuestion()
        }

    }
}