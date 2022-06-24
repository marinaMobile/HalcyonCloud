package com.gameloft.android.ANMP.GloftDMH.white

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.gameloft.android.ANMP.GloftDMH.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_white_one.*

@AndroidEntryPoint
class WhiteOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_one)
        val fragment = MyCustomDialog()
        fragment.show(supportFragmentManager, "MyCustomFragment")

        magic.setOnClickListener {
            animationRotate()
        }
    }

    private fun animationRotate() {

        val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.enlarge)
        val shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.shrink)

        magic.startAnimation(shrinkAnimation)
        magic.startAnimation(shakeAnimation)


        val animShakeListener: Animation.AnimationListener =
            object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation) {
                    magic.startAnimation(shrinkAnimation)
                    text()
                }

                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationStart(animation: Animation) {
                    tv_answer.text = ""
                }
            }

        val animShrinkListener: Animation.AnimationListener =
            object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation) {
                    magic.startAnimation(shakeAnimation)
                    magic.clearAnimation()
                }

                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationStart(animation: Animation) {}
            }
        shakeAnimation.setAnimationListener(animShakeListener)
        shrinkAnimation.setAnimationListener(animShrinkListener)
    }

    private fun text() {

        val arrayList = listOf(
            "Нет",
            "Вполне вероятно",
            "Может быть",
            "Задайте вопрос позже",
            "Возможно",
            "Наверное",
            "Однозначно",
            "Звёзды говорят да",
            "Без сомнений",
            "Безусловно",
            "Абсолютно точно",
            "Ответ не ясен",
            "Не могу сказать",
            "Вряд ли",
            "Звёзды говорят нет",
            "Не надейтесь",
            "Мало шансов"
        )
        tv_answer.text = arrayList[(arrayList.indices).random()]
        val anim = AnimationUtils.loadAnimation(this, R.anim.tv_anim);
        tv_answer.startAnimation(anim);
    }
}