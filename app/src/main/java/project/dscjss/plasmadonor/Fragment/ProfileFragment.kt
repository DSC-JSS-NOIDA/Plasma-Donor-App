package project.dscjss.plasmadonor.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.profile_fragment.*
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import project.dscjss.plasmadonor.Fragment.editprofile.EditProfileFragment
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities.showShortToast
import project.dscjss.plasmadonor.ViewModel.ProfileViewModel
import project.dscjss.plasmadonor.databinding.ProfileFragmentBinding
import project.dscjss.plasmadonor.models.ProfileDetail

class ProfileFragment : Fragment() {

    companion object {
        private const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var viewModel: ProfileViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var detail: ProfileDetail? = null
    private lateinit var mContext: Context
    private lateinit var binding: ProfileFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        binding = ProfileFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(context, UserLoginActivity::class.java))
            requireActivity().finish()
        } else {
            firebaseFirestore.collection("users")
                .whereEqualTo("uid", firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.documents.size == 0) {
                        mContext.showShortToast(R.string.err_msg_no_user_detail)
                    } else {
                        parseAndSetUserInfo(doc.documents.first())
                    }
                }
                .addOnFailureListener {
                    mContext.showShortToast(R.string.err_user_detail_firebase__fetch_failure)
                    Log.e(TAG, it.message.toString())
                }
        }
    }
    private fun parseAndSetUserInfo(doc: DocumentSnapshot) {
        if (detail == null) { detail = ProfileDetail() }
        detail?.apply {
            firstName = doc["FirstName"].toString()
            lastName = doc ["LastName"].toString()
            age = doc["Age"].toString()
            bloodGroup = doc["BloodGroup"].toString()
            weight = doc["Weight"].toString()
            gender = doc["Gender"].toString()
            phone = doc["Phone"].toString()
            emailId = doc["Email"].toString()
            location = doc["Location"].toString()
        }.also {
            binding.user = detail
        }
    }
    private fun init() {
        fragmentChangeInterface = context as FragmentChangeInterface
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        buttonProfileEdit.setOnClickListener {
            fragmentChangeInterface.changeFragment(
                EditProfileFragment.newInstance(detail)
            )
        }
    }
}
