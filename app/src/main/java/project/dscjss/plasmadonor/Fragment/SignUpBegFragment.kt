package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface

class SignUpBegFragment : Fragment(), View.OnClickListener {
    lateinit var btnEmailSignUp: MaterialButton
    lateinit var tvSignIn: TextView
    lateinit var googleSignUp: MaterialCardView
    lateinit var facebookSignUp: MaterialCardView
    lateinit var fragmentChangeInterface: FragmentChangeInterface
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentChangeInterface = context as FragmentChangeInterface
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
                Toast.makeText(requireContext(), "SignUp through facebook", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
