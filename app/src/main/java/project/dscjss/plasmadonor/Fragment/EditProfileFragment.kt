package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.EditProfileViewModel

class EditProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = EditProfileFragment()
    }
    lateinit var fragmentChangeInterface: FragmentChangeInterface
    lateinit var utilities: Utilities
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        // TODO: Use the ViewModel

        init()

    }

    private fun init(){
        fragmentChangeInterface = context as FragmentChangeInterface
        utilities = Utilities

        btSave.setOnClickListener{

            utilities.showLongToast(requireContext(),"Profile Updated")

            //todo fragments ui needs to added and all data managed from here

            fragmentChangeInterface.changeFragment(ProfileFragment())
        }
    }

}
