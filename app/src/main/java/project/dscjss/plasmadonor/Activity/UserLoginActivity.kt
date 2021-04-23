package project.dscjss.plasmadonor.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.Fragment.LoadingFragment
import project.dscjss.plasmadonor.Fragment.SignUpBegFragment
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface

class UserLoginActivity : AppCompatActivity(), FragmentChangeInterface {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        //supportActionBar!!.hide()
        // changed the loginFragment() to SignUpBegFragment
        val fragment = SignUpBegFragment()
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

    fun showLoading() {
        val fragment = LoadingFragment()
        supportFragmentManager.beginTransaction()
            .add(fragment, "loading").commit()
    }

    fun hideLoading() {
        val fragment = supportFragmentManager.findFragmentByTag("loading") ?: return

        supportFragmentManager.beginTransaction()
            .remove(fragment).commit()
    }
}
