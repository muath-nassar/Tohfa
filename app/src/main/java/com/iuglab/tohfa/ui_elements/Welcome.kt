package com.iuglab.tohfa.ui_elements

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.Font
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_welcome.*
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_welcome)

        btnNext.animate().translationY(0f).duration = 500
        btnNext.animate().alpha(1f).duration = 600

        val titles = ArrayList<String>()
        titles.add("")
        titles.add("Flexible")
        titles.add("")
        titles.add("")
        titles.add("Speed")
        titles.add("")

        val desc =  ArrayList<String>()
        desc.add("")
        desc.add("")
        desc.add("Support registration with various \n social media sites")
        desc.add("")
        desc.add("")
        desc.add("We provide very fast delivery staff \n for your requests")

        var index = 0
        imgFlipper.setInAnimation(this,android.R.anim.fade_in)
        imgFlipper.setOutAnimation(this,android.R.anim.fade_out)

        switcher1.setFactory {
            val tv = TextView(this@Welcome)
            tv.textSize = 30f
            tv.typeface = Typeface.createFromAsset(assets, "titlesfont.ttf")
            tv.text = "Security"
            tv.setTextColor(Color.rgb(156,39,176))
            tv.gravity = Gravity.CENTER_HORIZONTAL
            return@setFactory tv
        }

        switcher2.setFactory {
            val tv2 = TextView(this@Welcome)
            tv2.textSize = 20f
            tv2.typeface = Typeface.createFromAsset(assets, "descriptionfonts.ttf")
            tv2.setTextColor(Color.rgb(33,33,33))
            tv2.text = "Your Card & Data Will be Encrypted"
            tv2.gravity = Gravity.CENTER
            return@setFactory tv2
        }

        btnNext.setOnClickListener {
            if (index <= titles.size -1){
                if (index <= titles.size -1){
                    index += 1
                    switcher1.setText(titles[index++])
                    imgFlipper.showNext()

                }
                if (index <= desc.size -1){
                    switcher2.setText(desc[index++])
                }
            }else {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }




    }
}
