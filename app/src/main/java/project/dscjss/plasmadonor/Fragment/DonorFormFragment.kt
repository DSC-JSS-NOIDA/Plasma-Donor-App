package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.donor_form_fragment.*
import kotlinx.android.synthetic.main.patient_form_fragment.*
import kotlinx.android.synthetic.main.patient_form_fragment.btSubmit
import kotlinx.android.synthetic.main.patient_form_fragment.cbBpProblem
import kotlinx.android.synthetic.main.patient_form_fragment.cbDiabetes
import kotlinx.android.synthetic.main.patient_form_fragment.cbLiver
import kotlinx.android.synthetic.main.patient_form_fragment.etAge
import kotlinx.android.synthetic.main.donor_form_fragment.spGender
import kotlinx.android.synthetic.main.patient_form_fragment.etEmail
import kotlinx.android.synthetic.main.donor_form_fragment.spBloodGroup
import kotlinx.android.synthetic.main.patient_form_fragment.etLocation
import kotlinx.android.synthetic.main.patient_form_fragment.etMobile
import kotlinx.android.synthetic.main.patient_form_fragment.etName
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
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
        var donorDetails = HashMap<String, String>()
        donorDetails["Name"] = etName.text.toString()
        donorDetails["Age"] = etAge.text.toString()
        donorDetails["Gender"] = spGender.selectedItem.toString()
        donorDetails["Location"] = etLocation.text.toString()
        donorDetails["Mobile"] = etMobile.text.toString()
        donorDetails["BloodGroup"] = spBloodGroup.selectedItem.toString()
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
        etLocation.setText("")
        etMobile.setText("")
        etEmail.setText("")
        spGender.setSelection(0)
        spBloodGroup.setSelection(0)

        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }


}