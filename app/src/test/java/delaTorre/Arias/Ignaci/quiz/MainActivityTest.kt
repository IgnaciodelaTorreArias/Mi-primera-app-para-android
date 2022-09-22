package delaTorre.Arias.Ignaci.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith

@RunWith(JUnit4::class)
class MainActivityTest{
    private lateinit var scenario: ActivityScenario<MainActivity>
    @Test
    fun proveeElTectoDePreguntaEsperado(){
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_welcome, quizViewModel.currentQuestion.textResId)
    }
    @Before
    fun setUP(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }
    @After
    fun tearDown(){
        scenario.close()
    }
    @Test
    fun verificarSiSeMuestraPrimerPreguntaOnLaunch(){
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_welcome)))
    }
}