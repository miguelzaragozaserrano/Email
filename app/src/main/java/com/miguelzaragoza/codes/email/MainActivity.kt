package com.miguelzaragoza.codes.email

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.miguelzaragoza.cursobasico.email.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var email = findViewById<Button>(R.id.email)
        email.setOnClickListener {
            Mailer.sendMail(
                "example@email.com", "This is an example",
                "We are trying to send a mail without intent. Change the example@email.com with the email address where you want to receive the mail."
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(this, "Sent! Check the email", Toast.LENGTH_LONG).show()
                }
        }
    }
}