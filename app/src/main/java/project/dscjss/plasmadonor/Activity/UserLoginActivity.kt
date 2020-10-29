package project.dscjss.plasmadonor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.Fragment.LoginFragment
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.R

class UserLoginActivity : AppCompatActivity(), FragmentChangeInterface {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.loginFrame, fragment).commit()
    }

    override fun changeFragment(fragment: Fragment) {

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.loginFrame, fragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.loginFrame, fragment).commit()
        }
    }
}
