package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.facebook.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import project.dscjss.plasmadonor.Activity.MainActivity
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import java.util.*


class SignUpBegFragment : Fragment(), View.OnClickListener {
    lateinit var btnEmailSignUp: MaterialButton
    lateinit var tvSignIn: TextView
    lateinit var googleSignUp: MaterialCardView
    lateinit var facebookSignUp: MaterialCardView
    lateinit var fragmentChangeInterface: FragmentChangeInterface
    lateinit var callbackManager:CallbackManager
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentChangeInterface = context as FragmentChangeInterface
        // Initialize the facebook sdk
        FacebookSdk.sdkInitialize(requireContext());
        callbackManager = CallbackManager.Factory.create()
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_sign_up_beg, container, false)
        btnEmailSignUp = view.findViewById<MaterialButton>(R.id.btnEmailSignUp)
        tvSignIn = view.findViewById<TextView>(R.id.tvSignIn)
        googleSignUp = view.findViewById<MaterialCardView>(R.id.googleSignUp)
        facebookSignUp = view.findViewById<MaterialCardView>(R.id.facebookSignUp)
        btnEmailSignUp.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)
        googleSignUp.setOnClickListener(this)
        facebookSignUp.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEmailSignUp -> {
                fragmentChangeInterface.changeFragment(SignUpInfoFragment())
            }
            R.id.tvSignIn -> {
                fragmentChangeInterface.changeFragment(SignInBegFragment())
            }
            R.id.googleSignUp -> {
                Toast.makeText(requireContext(), "SignUp through google", Toast.LENGTH_SHORT).show()
            }
            R.id.facebookSignUp -> {
              //  Toast.makeText(requireContext(), "SignUp through facebook", Toast.LENGTH_SHORT).show()
                LoginManager.getInstance().logInWithReadPermissions(activity, mutableListOf("email","public_profile"))
                LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("sp",loginResult.accessToken.toString())
                        handleFacebookAccessToken(loginResult.accessToken)
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException) {

                    }
                })
            }
        }
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user!=null){
            startActivity(Intent(context,MainActivity::class.java))
        }else{
            Toast.makeText(requireContext(),"SignUp through facebook",Toast.LENGTH_SHORT).show()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
