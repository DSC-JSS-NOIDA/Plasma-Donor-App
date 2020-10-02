package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.patient_form_fragment.*
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.PatientFormViewModel

class PatientFormFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    companion object {
        private const val TAG = "PatientForm"
        fun newInstance() = PatientFormFragment()
    }

    private lateinit var viewModel: PatientFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        // TODO: Use the ViewModel

        btSubmit.setOnClickListener {

            if(etName.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Name cannot be blank!")
                return@setOnClickListener
            }
            if(spBloodGroup.selectedItemPosition==0){
                Utilities.showShortToast(requireContext(),"Blood Group cannot be blank!")
                return@setOnClickListener
            }
            if(etAge.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Age cannot be blank!")
                return@setOnClickListener
            }
            if(spGender.selectedItemPosition==0){
                Utilities.showShortToast(requireContext(),"Gender cannot be blank!")
                return@setOnClickListener
            }
            if(etLocation.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Location cannot be blank!")
                return@setOnClickListener
            }
            if(etMobile.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Mobile cannot be blank!")
                return@setOnClickListener
            }
            if(etEmail.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Email cannot be blank!")
                return@setOnClickListener
            }

            insertData()

        }

    }

    private fun insertData() {
        var PatientDetails = HashMap<String, String>()
        PatientDetails["Name"] = etName.text.toString()
        PatientDetails["Age"] = etAge.text.toString()
        PatientDetails["Gender"] = spGender.selectedItem.toString()
        PatientDetails["Location"] = etLocation.text.toString()
        PatientDetails["Hospital"] = etHospital.text.toString()
        PatientDetails["Mobile"] = etMobile.text.toString()
        PatientDetails["BloodGroup"] = spBloodGroup.selectedItem.toString()
        PatientDetails["Diabetes"] = cbDiabetes.isChecked.toString()
        PatientDetails["BpProblem"] = cbBpProblem.isChecked.toString()
        PatientDetails["LiverProblem"] = cbLiver.isChecked.toString()

        firebaseFirestore.collection("patients")
            .add(PatientDetails)
            .addOnSuccessListener {
                Utilities.showShortToast(requireContext(), "Data Inserted successfully")
                clearFields()
            }
            .addOnFailureListener {
                Utilities.showShortToast(requireContext(), "Something went wrong")
            }
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this).get(PatientFormViewModel::class.java)
    }

    private fun clearFields() {

        etName.setText("")
        etAge.setText("")
        spGender.setSelection(0)
        etHospital.setText("")
        etLocation.setText("")
        etMobile.setText("")
        spBloodGroup.setSelection(0)
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }

}