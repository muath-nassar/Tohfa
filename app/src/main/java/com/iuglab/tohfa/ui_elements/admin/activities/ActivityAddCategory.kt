package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.toolbar_admin.*

class ActivityAddCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        setSupportActionBar(toolbar)
        toolbar.title = "UPDATE CATEGORY" //customize title
    }
}
