package delaTorre.Arias.Ignaci.quiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, var answer: Boolean)