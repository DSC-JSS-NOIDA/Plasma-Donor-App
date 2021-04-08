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

class SignInBegFragment : Fragment(),View.OnClickListener {
    lateinit var btnEmailSignIn:MaterialButton
    lateinit var tvSignUp:TextView
    lateinit var googleSignIn:MaterialCardView
    lateinit var facebookSignIn:MaterialCardView
    lateinit var fragmentChangeInterface: FragmentChangeInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentChangeInterface = context as FragmentChangeInterface
        val view=inflater.inflate(R.layout.fragment_sign_in_beg, container, false)
         btnEmailSignIn=view.findViewById<MaterialButton>(R.id.btnEmailSignIn)
         tvSignUp=view.findViewById<TextView>(R.id.tvSignUp)
         googleSignIn=view.findViewById<MaterialCardView>(R.id.googleSignIn)
         facebookSignIn=view.findViewById<MaterialCardView>(R.id.facebookSignIn)
         btnEmailSignIn.setOnClickListener(this)
         tvSignUp.setOnClickListener(this)
         googleSignIn.setOnClickListener(this)
         facebookSignIn.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnEmailSignIn->{
                fragmentChangeInterface.changeFragment(SignInInfoFragment())
            }
            R.id.tvSignUp->{
                fragmentChangeInterface.changeFragment(SignUpBegFragment())
            }
            R.id.googleSignIn->{
                Toast.makeText(requireContext(),"Sign In via google",Toast.LENGTH_SHORT).show()
            }
            R.id.facebookSignIn->{
                Toast.makeText(requireContext(),"Sign In via facebook",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
