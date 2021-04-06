package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import project.dscjss.plasmadonor.R

class SignIn_Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.login_fragment_new_design, container, false)
        view.findViewById<MaterialButton>(R.id.btnEmailSignIn).setOnClickListener {
            Toast.makeText(requireContext(),"email login btn clicked",Toast.LENGTH_SHORT).show()
        }
        return view
    }

}
