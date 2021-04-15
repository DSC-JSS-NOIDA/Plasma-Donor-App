package project.dscjss.plasmadonor.Activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import project.dscjss.plasmadonor.AboutFragment
import project.dscjss.plasmadonor.Fragment.FeedsFragment
import project.dscjss.plasmadonor.Fragment.DonorListFragment
import project.dscjss.plasmadonor.Fragment.PatientListFragment
import project.dscjss.plasmadonor.Fragment.DonorFormFragment
import project.dscjss.plasmadonor.Fragment.PatientFormFragment
import project.dscjss.plasmadonor.Fragment.ProfileFragment
import project.dscjss.plasmadonor.Fragment.FaqFragment
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.OnBackPressInterface

class MainActivity :
    AppCompatActivity(),
    FragmentChangeInterface,
    NavigationView.OnNavigationItemSelectedListener,
    OnBackPressInterface {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, FeedsFragment())
            .commit()

        val fabSpeedDial = findViewById<FabSpeedDial>(R.id.fab_speed_dial)
        fabSpeedDial.setMenuListener(object: SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                //TODO: Start some activity
                onNavigationItemSelected(menuItem)
                return true
            }
        })

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

        initiate(FeedsFragment())
    }

    override fun changeFragment(fragment: Fragment) {

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFrame, fragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        fab_speed_dial.visibility = GONE
        when (item.itemId) {
            R.id.add_donor -> initiate(DonorFormFragment())
            R.id.add_patient -> initiate(PatientFormFragment())
            R.id.home ->{
                fab_speed_dial.visibility = VISIBLE
                initiate(FeedsFragment())
            }
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

    private fun initiate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
            .onOptionsItemSelected(item)
        ) return true
        return super.onOptionsItemSelected(item)
    }

    override fun navigateBack() {
        onBackPressed()
    }
}
