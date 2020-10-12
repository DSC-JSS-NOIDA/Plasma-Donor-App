package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import kotlinx.android.synthetic.main.edit_profile_fragment.view.*
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
        val v = inflater.inflate(R.layout.edit_profile_fragment, container, false)

        v.etGender.adapter = spinnerAdapter(resources.getStringArray(R.array.gender))
        v.etBloodGrp.adapter = spinnerAdapter(resources.getStringArray(R.array.blood_grp))

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        // TODO: Use the ViewModel

        init()
    }

    private fun init() {
        fragmentChangeInterface = context as FragmentChangeInterface
        utilities = Utilities

        btSave.setOnClickListener {
            utilities.showLongToast(requireContext(), "Profile Updated")
            //TODO fragments ui needs to added and all data managed from here

            fragmentChangeInterface.changeFragment(ProfileFragment())
        }
    }

    private fun spinnerAdapter(spinnerType: Array<String>): ArrayAdapter<String> {
        return object : ArrayAdapter<String>(
            requireContext(), R.layout.spinner_text_layout,
            spinnerType
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {

                val dropdownView = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    dropdownView.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorHint))
                } else {
                    dropdownView.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                }
                return dropdownView
            }
        }
    }
}