package com.miguelzaragoza.codes.email

import android.annotation.SuppressLint
import io.reactivex.Completable
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object Mailer {
    @SuppressLint("CheckResult")
    fun sendMail(email: String, subject: String, message: String): Completable {
        return Completable.create { emitter ->
            // Configure SMTP server
            val props: Properties = Properties().also {
                it["mail.smtp.host"] = "smtp.gmail.com"
                it["mail.smtp.socketFactory.port"] = "465"
                it["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                it["mail.smtp.auth"] = "true"
                it["mail.smtp.port"] = "465"
            }
            // Creating a session
            val session = Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
                }
            })
            try {
                MimeMessage(session).let { mime ->
                    mime.setFrom(InternetAddress(Config.EMAIL))
                    // Adding receiver
                    mime.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                    // Adding subject
                    mime.subject = subject
                    // Adding message
                    mime.setText(message)
                    // Send mail
                    Transport.send(mime)
                }
            } catch (e: MessagingException) {
                emitter.onError(e)
            }
            // Ending subscription
            emitter.onComplete()
        }
    }
}