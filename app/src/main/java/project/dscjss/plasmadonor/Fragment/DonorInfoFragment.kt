package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.DonorInfoViewModel

class DonorInfoFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = DonorInfoFragment()
    }

    private lateinit var viewModel: DonorInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.donor_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DonorInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
