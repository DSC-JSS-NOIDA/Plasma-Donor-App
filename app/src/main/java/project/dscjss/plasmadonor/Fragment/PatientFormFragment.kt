package project.dscjss.plasmadonor.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.patient_form_fragment.*
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.Util.isPhoneNumberValid
import project.dscjss.plasmadonor.ViewModel.PatientFormViewModel

class PatientFormFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var spinnerGender: Spinner
    lateinit var spinnerBloodGrp: Spinner

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
        setBloodGrpSpinner()
        setGenderSpinner()
        // TODO: Use the ViewModel

        btSubmit.setOnClickListener {

            if(etName.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Name cannot be blank!")
                return@setOnClickListener
            }
            if(spinnerBloodGrp?.selectedItem.toString().equals(getString(R.string.blood_group), true)){
                (spinnerBloodGrp.selectedView as TextView).error = "Select Blood Group"
                check = true
            }
            if(etAge.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Age cannot be blank!")
                return@setOnClickListener
            }
            if(spinnerGender?.selectedItem.toString().equals(getString(R.string.gender), true)){
                (spinnerGender.selectedView as TextView).error = "Select Gender"
                check = true
            }
            if(etLocation.text.isBlank()){
                Utilities.showShortToast(requireContext(),"Location cannot be blank!")
                return@setOnClickListener
            }
            if(etMobile.text.isBlank() || isPhoneNumberValid(etMobile.text.toString())){
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

    private fun spinnerAdapter(spinnerType : Array<String>): ArrayAdapter<String> {
        var adapter = object : ArrayAdapter<String>(
            requireContext(), R.layout.spinner_text_layout,
            spinnerType
        ) {

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {

                val dropdownView = super.getDropDownView(position, convertView, parent) as TextView


                if (position == 0) {
                    dropdownView.setTextColor(resources.getColor(R.color.colorHint))

                } else {
                    dropdownView.setTextColor(resources.getColor(R.color.colorPrimary))
                }

                return dropdownView
            }

        }
        return adapter
    }

    private fun setGenderSpinner() {

        spinnerGender = view?.findViewById(R.id.sp_gender) as Spinner

        spinnerGender.adapter = spinnerAdapter(resources.getStringArray(R.array.gender))

        spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(view: AdapterView<*>?) {
            }


            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                p3: Long
            ) {

                var selectedText = adapterView?.getChildAt(0) as TextView


                if (adapterView.getItemAtPosition(position).toString() == "Gender") {
                    selectedText.setTextColor(resources.getColor(R.color.colorHint))
                } else {
                    selectedText.setTextColor(Color.BLACK)
                }


            }
        }

    }


    private fun setBloodGrpSpinner() {

        spinnerBloodGrp = view?.findViewById(R.id.sp_bloodGrp) as Spinner

        spinnerBloodGrp.adapter = spinnerAdapter(resources.getStringArray(R.array.blood_grp))

        spinnerBloodGrp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(view: AdapterView<*>?) {
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                p3: Long
            ) {

                var selectedText = adapterView?.getChildAt(0) as TextView


                if (adapterView.getItemAtPosition(position).toString() == "Blood Group") {
                    selectedText.setTextColor(resources.getColor(R.color.colorHint))
                } else {
                    selectedText.setTextColor(Color.BLACK)
                }


            }
        }

    }



    private fun insertData() {
        var PatientDetails = HashMap<String, String>()
        PatientDetails["Name"] = etName.text.toString()
        PatientDetails["Age"] = etAge.text.toString()
        PatientDetails["Gender"] = spinnerGender.selectedItem.toString()
        PatientDetails["Location"] = etLocation.text.toString()
        PatientDetails["Hospital"] = etHospital.text.toString()
        PatientDetails["Mobile"] = etMobile.text.toString()
        PatientDetails["BloodGroup"] = spinnerBloodGrp.selectedItem.toString()
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
        spinnerGender.setSelection(0)
        etHospital.setText("")
        etLocation.setText("")
        etMobile.setText("")
        spinnerBloodGrp.setSelection(0)
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }

}