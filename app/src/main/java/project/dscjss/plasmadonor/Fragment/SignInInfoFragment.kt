package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.Activity.MainActivity
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities

class SignInInfoFragment : Fragment(), View.OnClickListener {
    lateinit var tietSignInEmail: TextInputEditText
    lateinit var tietSignInPassword: TextInputEditText
    lateinit var tvForgotPassword: TextView
    lateinit var btnSignIn: MaterialButton
    lateinit var utilities: Utilities
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_page, container, false)
        tietSignInEmail = view.findViewById(R.id.tietSignInEmail)
        tietSignInPassword = view.findViewById(R.id.tietSignInPassword)
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword)
        btnSignIn = view.findViewById(R.id.btnSignIn)
        init()

        return view
    }

    private fun init() {
        btnSignIn.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()
        utilities = Utilities
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSignIn -> {
                if (!checkFields())
                    return

                (requireActivity() as UserLoginActivity).showLoading()
                firebaseAuth.signInWithEmailAndPassword(
                    tietSignInEmail.text.toString(),
                    tietSignInPassword.text.toString()
                )
                    .addOnSuccessListener {
                        utilities.showShortToast(requireContext(), "Login Success: ${it.user!!.displayName}")
                        (requireActivity() as UserLoginActivity).hideLoading()
                        startActivity(Intent(context, MainActivity::class.java))
                        requireActivity().finish()
                    }
                    .addOnFailureListener {
                        utilities.showShortToast(requireContext(), it.message + "")
                        (requireActivity() as UserLoginActivity).hideLoading()
                    }
            }
            R.id.tvForgotPassword -> {
                if (tietSignInEmail.text.isNullOrEmpty()) {
                    tietSignInEmail.error = "Enter Email to receive to Reset Password form"
                    tietSignInEmail.requestFocus()
                    return
                } else {
                    tietSignInEmail.error = null
                    tietSignInEmail.clearFocus()
                }

                (requireActivity() as UserLoginActivity).showLoading()
                firebaseAuth.sendPasswordResetEmail(tietSignInEmail.text.toString())
                    .addOnSuccessListener {
                        utilities.showShortToast(requireContext(), "Please check your Email")
                        (requireActivity() as UserLoginActivity).hideLoading()
                    }
                    .addOnFailureListener {
                        utilities.showShortToast(requireContext(), it.message + "")
                        (requireActivity() as UserLoginActivity).hideLoading()
                    }
            }
        }
    }

    private fun checkFields(): Boolean {
        if (tietSignInEmail.text.isNullOrEmpty()) {
            tietSignInEmail.error = "Email can't be empty"
            tietSignInEmail.requestFocus()
            return false
        } else {
            tietSignInEmail.error = null
            tietSignInEmail.clearFocus()
        }

        if (tietSignInPassword.text.isNullOrEmpty()) {
            tietSignInPassword.error = "Email can't be empty"
            tietSignInPassword.requestFocus()
            return false
        } else {
            tietSignInPassword.error = null
            tietSignInPassword.clearFocus()
        }

        return true
    }
}
