package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.PatientInfoViewModel

class PatientInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PatientInfoFragment()
    }

    private lateinit var viewModel: PatientInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatientInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
