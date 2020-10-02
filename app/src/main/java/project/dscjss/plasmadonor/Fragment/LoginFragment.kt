package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.CubeGrid
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login_fragment.*
import project.dscjss.plasmadonor.Activity.MainActivity
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.LoginViewModel

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var utilities: Utilities
    lateinit var fragmentChangeInterface: FragmentChangeInterface
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    private val RC_SIGN_IN = 101
    private val TAG = "Login Fragment"
    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var alertDialog: AlertDialog

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        // TODO: Use the ViewModel

        tvSignUpLogin.setOnClickListener(this)
        tvForgot.setOnClickListener(this)
        tvLoginButton.setOnClickListener(this)
        tvLoginGmail.setOnClickListener(this)

        val progressBar: SpinKitView = SpinKitView(requireContext())
        val cube = Circle()
        progressBar.setIndeterminateDrawable(cube)
        progressBar.setColor(R.color.colorPrimary)

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(progressBar)

        alertDialog = builder.setCancelable(false).create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    private fun signInGmail() {

        if (firebaseAuth.currentUser != null)
            firebaseAuth.signOut()

        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser

                    val docRef = firebaseFirestore.collection("users")
                        .whereEqualTo("uid", user!!.uid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            alertDialog.dismiss()
                            if (document.size() == 0) {
                                Log.d(TAG, "No such User")

                                val userHash: HashMap<String, String> = HashMap()
                                userHash["uid"] = firebaseAuth.currentUser!!.uid
                                userHash["FirstName"] =
                                    user.displayName.toString().substringBefore(' ', "")
                                userHash["LastName"] =
                                    user.displayName.toString().substringAfter(' ', "")
                                userHash["Email"] = user.email.toString()
                                userHash["Phone"] = "Not Provided"
                                userHash["BloodGroup"] = "Not Provided"
                                userHash["Age"] = "Not Provided"
                                userHash["Weight"] = "Not Provided"
                                userHash["Gender"] = "Not Provided"
                                userHash["Location"] = "Not Provided"

                                FirebaseFirestore.getInstance().collection("users")
                                    .add(userHash)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            utilities.showShortToast(requireContext(), "Data Inserted")

                                            startActivity(Intent(context, MainActivity::class.java))
                                            requireActivity().finish()

                                        }
                                    }

                            } else {
                                alertDialog.dismiss()
                                startActivity(Intent(context, MainActivity::class.java))
                                requireActivity().finish()
                            }

                        }
                        .addOnFailureListener { exception ->
                            alertDialog.dismiss()
                            Log.e(TAG, "get failed with ", exception)
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    utilities.showShortToast(requireContext(), "Login Failed: ${task.exception}")
                }

            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: ${account.displayName}")
                utilities.showShortToast(requireContext(), "Login Success: ${account.displayName}")

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed ${e.message}", e)
                utilities.showShortToast(requireContext(), "Login Failed: ${e.message}")
            }
        }
    }

    private fun checkFields(): Boolean {

        if (etEmailLogin.text.toString().isNullOrEmpty()) {
            etEmailLogin.error = "Email can't be empty"
            etEmailLogin.requestFocus()
            return false
        } else {
            etEmailLogin.error = null
            etEmailLogin.clearFocus()
        }

        if (etPasswordLogin.text.toString().isNullOrEmpty()) {
            etPasswordLogin.error = "Email can't be empty"
            etPasswordLogin.requestFocus()
            return false
        } else {
            etPasswordLogin.error = null
            etPasswordLogin.clearFocus()
        }

        return true

    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        utilities = Utilities
        fragmentChangeInterface = context as FragmentChangeInterface
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvSignUpLogin -> {
                fragmentChangeInterface.changeFragment(SignupFragment())
            }

            R.id.tvForgot -> {
                if (etEmailLogin.text.isNullOrEmpty()) {
                    etEmailLogin.error = "Enter Email to receive to Reset Password form"
                    etEmailLogin.requestFocus()
                    return
                } else {
                    etEmailLogin.error = null
                    etEmailLogin.clearFocus()
                }

                firebaseAuth.sendPasswordResetEmail(etEmailLogin.text.toString())
                    .addOnSuccessListener {
                        utilities.showShortToast(requireContext(), "Please check your Email")
                    }
                    .addOnFailureListener {
                        utilities.showShortToast(requireContext(), it.message + "")
                    }
            }

            R.id.tvLoginButton -> {
                alertDialog.show()
                if (!checkFields())
                    return

                firebaseAuth.signInWithEmailAndPassword(
                    etEmailLogin.text.toString(),
                    etPasswordLogin.text.toString()
                )
                    .addOnSuccessListener {
                        alertDialog.dismiss()
                        utilities.showShortToast(requireContext(), "Login Success: ${it.user!!.displayName}")
                        startActivity(Intent(context, MainActivity::class.java))
                        requireActivity().finish()
                    }
                    .addOnFailureListener {
                        alertDialog.dismiss()
                        utilities.showShortToast(requireContext(), it.message + "")
                    }
            }

            R.id.tvLoginGmail -> {

                alertDialog.show()

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

                signInGmail()

            }

        }
    }

}