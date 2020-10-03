package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.donor_form_fragment.*
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.Util.isPhoneNumberValid
import project.dscjss.plasmadonor.ViewModel.DonorFormViewModel

class DonorFormFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    companion object {
        private const val TAG = "DonorForm"
        fun newInstance() = DonorFormFragment()
    }

    private lateinit var viewModel: DonorFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.donor_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setupBloodGroupSpinner()
        setupGenderSpinner()
        // TODO: Use the ViewModel

        btSubmit.setOnClickListener {
            if(etName.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Name cannot be blank!")
                return@setOnClickListener
            }
            if(spBloodGrp?.selectedItem.toString().equals(getString(R.string.blood_group), true)){
                Utilities.showShortToast(requireContext(),"Blood Group cannot be blank!")
                return@setOnClickListener
            }
            if(etAge.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Age cannot be blank!")
                return@setOnClickListener
            }
            if(spGender?.selectedItem.toString().equals(getString(R.string.gender), true)){
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
            } else if (!isPhoneNumberValid(etMobile.text.toString())) {
                Utilities.showShortToast(requireContext(),"Mobile no. invalid!")
                return@setOnClickListener
            }
            if(etEmail.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Email cannot be blank!")
                return@setOnClickListener
            }

            insertData()
        }
    }

    private fun setupGenderSpinner() {
        ArrayAdapter.createFromResource(requireContext(), R.array.gender_array, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spGender?.adapter = arrayAdapter
            }
    }

    private fun setupBloodGroupSpinner() {
        ArrayAdapter.createFromResource(requireContext(), R.array.blood_group_array, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spBloodGrp?.adapter = arrayAdapter
            }
    }


    private fun insertData() {
        var donorDetails = HashMap<String, String>()
        donorDetails["Name"] = etName.text.toString()
        donorDetails["Age"] = etAge.text.toString()
        donorDetails["Gender"] = spGender.selectedItem.toString()
        donorDetails["Location"] = etLocation.text.toString()
        donorDetails["Mobile"] = etMobile.text.toString()
        donorDetails["BloodGroup"] = spBloodGrp.selectedItem.toString()
        donorDetails["Diabetes"] = cbDiabetes.isChecked.toString()
        donorDetails["BpProblem"] = cbBpProblem.isChecked.toString()
        donorDetails["LiverProblem"] = cbLiver.isChecked.toString()

        firebaseFirestore.collection("donors")
            .add(donorDetails)
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
        viewModel = ViewModelProvider(this).get(DonorFormViewModel::class.java)
    }

    private fun clearFields() {

        etName.setText("")
        etAge.setText("")
        spGender?.setSelection(0)
        etLocation.setText("")
        etMobile.setText("")
        spBloodGrp?.setSelection(0)
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }


}