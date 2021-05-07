package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.FeedsViewModel

class FeedsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fabBtn: FabSpeedDial

    companion object {
        fun newInstance() = FeedsFragment()
    }

    private lateinit var viewModel: FeedsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feeds_fragment, container, false)
        fabBtn = view.findViewById(R.id.fab_speed_dial)
        fabBtn.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // TODO: Start some activity
                onNavigationItemSelected(menuItem)
                return true
            }
        })
        return view
    }

    private fun onNavigationItemSelected(menuItem: MenuItem) {
        fabBtn.visibility = View.GONE
        when (menuItem.itemId) {
            R.id.add_donor -> {
                initiate(DonorFormFragment())
            }
            R.id.add_patient -> {
                initiate(PatientFormFragment())
            }
        }
    }

    private fun initiate(fragment: Fragment) {
        activity?.supportFragmentManager!!.beginTransaction().replace(R.id.mainFrame, fragment).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedsViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
