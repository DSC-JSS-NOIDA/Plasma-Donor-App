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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import project.dscjss.plasmadonor.AboutFragment
import project.dscjss.plasmadonor.Fragment.*
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.interfaces.OnBackPressInterface
import project.dscjss.plasmadonor.models.ProfileDetail

class MainActivity :
    AppCompatActivity(),
    FragmentChangeInterface,
    NavigationView.OnNavigationItemSelectedListener,
    OnBackPressInterface {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var detail: ProfileDetail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        ProfileDetailFetch()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, FeedsFragment())
            .commit()
        bottom_navigation.setOnNavigationItemSelectedListener {
            lateinit var fragment: Fragment
            when (it.itemId) {
                R.id.btHome -> {
                    fragment = FeedsFragment()
                    initiate(fragment)
                }
                R.id.btSearch -> {
                    fragment = SearchFragment()
                    initiate(fragment)
                }
                R.id.btMessage -> {
                    fragment = MessageFragment()
                    initiate(fragment)
                }
                R.id.btProfile -> {
                    fragment = ProfileFragment()
                    initiate(fragment)
                }
            }
            true
        }
    }

    private fun ProfileDetailFetch() {
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()
        } else {
            firebaseFirestore.collection("users")
                .whereEqualTo("uid", firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.documents.size != 0) {
                        parseAndSetUserInfo(doc.documents.first())
                    }
                }
                .addOnFailureListener {}
        }
    }

    private fun parseAndSetUserInfo(doc: DocumentSnapshot) {
        tvName.text = "${doc["FirstName"]} ${doc["LastName"]}"
        UserGender.text = doc["Gender"].toString()
        userEmail.text = doc["Email"].toString()
    }

    private fun init() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        setSupportActionBar(topAppBar)
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
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
        when (item.itemId) {
            R.id.add_donor -> {
                topAppBar.title = "Add Donor"
                initiate(DonorFormFragment())
            }
            R.id.add_patient -> {
                topAppBar.title = "Add Patient"
                initiate(PatientFormFragment())
            }
            R.id.home -> {
                topAppBar.title = "Home"
                initiate(FeedsFragment())
            }
            R.id.viewDonors -> {
                topAppBar.title = "View Donors"
                initiate(DonorListFragment())
            }
            R.id.viewPatients -> {
                topAppBar.title = "View Patients"
                initiate(PatientListFragment())
            }
            R.id.addDonor -> {
                topAppBar.title = "Add Donor"
                initiate(DonorFormFragment())
            }
            R.id.addPatient -> {
                topAppBar.title = "Add Patient"
                initiate(PatientFormFragment())
            }
            R.id.profile -> {
                topAppBar.title = "Profile"
                initiate(ProfileFragment())
            }
            R.id.faq -> {
                topAppBar.title = "FAQ"
                initiate(FaqFragment())
            }
            R.id.aboutUs -> {
                topAppBar.title = "About Us"
                initiate(AboutFragment())
            }
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
        if (ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open,
                R.string.close
            ).onOptionsItemSelected(item)
        ) return true
        return super.onOptionsItemSelected(item)
    }

    override fun navigateBack() {
        onBackPressed()
    }
}
