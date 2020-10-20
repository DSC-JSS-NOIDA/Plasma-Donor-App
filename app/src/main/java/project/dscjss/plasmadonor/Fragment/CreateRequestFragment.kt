package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.CreateRequestViewModel

class CreateRequestFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = CreateRequestFragment()
    }

    private lateinit var viewModel: CreateRequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_request_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateRequestViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
