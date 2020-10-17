package project.dscjss.plasmadonor.Activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import project.dscjss.plasmadonor.AboutFragment
import project.dscjss.plasmadonor.Fragment.*
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.OnBackPressInterface

class MainActivity : AppCompatActivity(), FragmentChangeInterface, NavigationView.OnNavigationItemSelectedListener,OnBackPressInterface {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.mainFrame, ProfileFragment())
//            .commit()
    }


    private fun init() {

        firebaseAuth = FirebaseAuth.getInstance()
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun changeFragment(fragment: Fragment) {

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFrame, fragment).commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit()
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> initiate(FeedsFragment())
            R.id.viewDonors -> initiate(DonorListFragment())
            R.id.viewPatients -> initiate(PatientListFragment())
            R.id.addDonor -> initiate(DonorFormFragment())
            R.id.addPatient -> initiate(PatientFormFragment())
            R.id.profile -> initiate(ProfileFragment())
            R.id.faq -> initiate(FaqFragment())
            R.id.aboutUs -> initiate(AboutFragment())
            R.id.logout -> {
                firebaseAuth.signOut()
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initiate(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)

        }
        else if (supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
                .onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun navigateBack() {
       onBackPressed()
    }
}