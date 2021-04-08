package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.signup_fragment.*
import project.dscjss.plasmadonor.Activity.MainActivity
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities

class SignUpInfoFragment : Fragment(),View.OnClickListener {
    lateinit var utilities: Utilities
    lateinit var tietFirstName:TextInputEditText
    lateinit var tietLastName:TextInputEditText
    lateinit var tietEmail:TextInputEditText
    lateinit var tietPassword:TextInputEditText
    lateinit var tilLastName:TextInputLayout
    lateinit var tilFirstName:TextInputLayout
    lateinit var btnSignUp:MaterialButton
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        utilities = Utilities
        val view= inflater.inflate(R.layout.sign_up_page, container, false)
        tietFirstName=view.findViewById(R.id.tietFirstName)
        tietLastName=view.findViewById(R.id.tietLastName)
        tietEmail=view.findViewById(R.id.tietEmail)
        tietPassword=view.findViewById(R.id.tietPassword)
        tilLastName=view.findViewById(R.id.tilLastName1)
        tilFirstName=view.findViewById(R.id.tilFirstName1)
        btnSignUp=view.findViewById(R.id.btnSignUp)
        tietFirstName.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnSignUp->{
                if (!checkFields()) {
                    return
                }

                (requireActivity() as UserLoginActivity).showLoading()
                firebaseAuth.createUserWithEmailAndPassword(tietEmail.text.toString(), tietPassword.text.toString())
                    .addOnCompleteListener {
                        it.addOnSuccessListener {
                            // Sign Up Success Dialog
                            utilities.showShortToast(requireContext(), "User Created")
                            addDetails()
                        }
                        it.addOnFailureListener { e ->
                            utilities.showShortToast(requireContext(), e.message.toString())
                            (requireActivity() as UserLoginActivity).hideLoading()
                        }
                    }
            }
        }
    }

    private fun addDetails() {

        val userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName("${tietFirstName.text} ${tietLastName.text}")
            .build()

        firebaseAuth.currentUser!!.updateProfile(userProfileChangeRequest)

        val user: HashMap<String, String> = HashMap()
        user["uid"] = firebaseAuth.currentUser!!.uid
        user["FirstName"] = tietFirstName.text.toString()
        user["LastName"] = tietLastName.text.toString()
        user["Email"] = tietEmail.text.toString()
        user["Phone"] = "Not Provided"
        user["BloodGroup"] = "Not Provided"
        user["Age"] = "Not Provided"
        user["Weight"] = "Not Provided"
        user["Gender"] = "Not Provided"
        user["Location"] = "Not Provided"

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show()
                    (requireActivity() as UserLoginActivity).hideLoading()
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                } else {
                    addDetails()
                }
            }
    }

    private fun checkFields(): Boolean {
        var isValid = true
        if (tietEmail.text.isNullOrEmpty()) {
            tietEmail.error = "Email can't be empty"
            tietEmail.requestFocus()
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(tietEmail.text.toString()).matches()) {
            tietEmail.error = "Invalid email format"
            tietEmail.requestFocus()
            isValid = false
        } else {
            tietEmail.error = null
            tietEmail.clearFocus()
        }

        if (tietFirstName.text.isNullOrEmpty()) {
            tietFirstName.error = "First Name can't be empty"
            tietFirstName.requestFocus()
            isValid = false
        } else {
            tietFirstName.error = null
            tietFirstName.clearFocus()
        }

        if (tietLastName.text.isNullOrEmpty()) {
            tietLastName.error = "Last Name can't be empty"
            tietLastName.requestFocus()
            isValid = false
        } else {
            tietLastName.clearFocus()
            tietLastName.error = null
        }
        if (tietPassword.text.isNullOrEmpty()) {
            tietPassword.error = "Password can't be empty"
            tietPassword.requestFocus()
            isValid = false
        } else if (tietPassword.length() < 8) {
            tietPassword.error = "Password must be 8 digits long"
            tietPassword.requestFocus()
            isValid = false
        } else {
            tietPassword.error = null
           tietPassword.clearFocus()
        }

        return isValid

    }

}
