package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.profile_fragment.*
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        private const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var viewModel: ProfileViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        init()

        if (firebaseAuth.currentUser == null)  {
            startActivity(Intent (context, UserLoginActivity::class.java))
            requireActivity().finish()
        }
        else {

            firebaseFirestore.collection("users")
                .whereEqualTo("uid", firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {doc->
                    val it = doc.documents.first()

                    tvProfileName.text = "${it["FirstName"]} ${it["LastName"]}"
                    tvProfileAge.text = it["Age"].toString()
                    tvBloodGroup.text = it["BloodGroup"].toString()
                    tvProfileWeight.text = it["Weight"].toString()
                    tvProfileGender.text = it["Gender"].toString()
                    tvProfileMobile.text = it["Phone"].toString()
                    tvProfileEmail.text = it["Email"].toString()
                    tvProfileLocation.text = it["Location"].toString()

                }
                .addOnFailureListener {

                    Utilities.showShortToast(requireContext() , "Something went wrong fetching user details")

                    Log.e(TAG, it.message.toString())
                }
        }

    }

    private fun init(){

        fragmentChangeInterface = context as FragmentChangeInterface
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        buttonProfileEdit.setOnClickListener{
            fragmentChangeInterface.changeFragment(EditProfileFragment())
        }

    }
}
