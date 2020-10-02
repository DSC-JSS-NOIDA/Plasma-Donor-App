package project.dscjss.plasmadonor.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import project.dscjss.plasmadonor.R

class SplashActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
                if (firebaseAuth.currentUser != null) {
                    splashIcon.visibility = View.GONE
                    startActivity(Intent(applicationContext , MainActivity::class.java))
                }
                else {
                    splashIcon.visibility = View.GONE
                    startActivity(Intent(applicationContext, UserLoginActivity::class.java))
                }
                finish()
        }, 3000)

    }
}
