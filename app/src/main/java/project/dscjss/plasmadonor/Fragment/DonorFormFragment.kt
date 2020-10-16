package project.dscjss.plasmadonor.Fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.TextView
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
    lateinit var spinnerGender: Spinner
    lateinit var spinnerBloodGrp: Spinner

    companion object {
        private const val TAG = "DonorForm"
        fun newInstance() = DonorFormFragment()
    }

    private lateinit var viewModel: DonorFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.donor_form_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        // TODO: Use the ViewModel
        setBloodGrpSpinner()
        setGenderSpinner()
        btSubmit.setOnClickListener {
            var check : Boolean = false
            if(etName.text.isBlank()){
                etName.error = "Name cannot be blank!"
                check = true
            }
            if(spinnerBloodGrp?.selectedItem.toString().equals(getString(R.string.blood_group), true)){
                (spinnerBloodGrp.selectedView as TextView).error = "Select Blood Group"
                check = true
            }
            if(etAge.text.isBlank()){
                etAge.error = "Age cannot be blank!"
                check = true
            }
            if(spinnerGender?.selectedItem.toString().equals(getString(R.string.gender), true)){
                (spinnerGender.selectedView as TextView).error = "Select Gender"
                check = true
            }
            if(etLocation.text.isBlank()){
                etLocation.error = "Location cannot be blank!"
                check = true
            }
            if(etMobile.text.isBlank()){
                etMobile.error = "Mobile no. cannot be blank!"
                check=true
            } else if (!isPhoneNumberValid(etMobile.text.toString())) {
                etMobile.error = "Mobile no. invalid!"
                check=true
            }
            if(etEmail.text.isBlank()){
                etEmail.error = "Email cannot be blank!"
                check = true
            }

            if(check)
                return@setOnClickListener

            insertData()
        }
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etName.error = null
            }
        })
        etAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etAge.error = null
            }
        })
        etLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etLocation.error = null
            }
        })
        etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etMobile.error = null
            }
        })
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etEmail.error = null
            }
        })
    }



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
        var donorDetails = HashMap<String, String>()
        donorDetails["Name"] = etName.text.toString()
        donorDetails["Age"] = etAge.text.toString()
        donorDetails["Gender"] = spinnerGender.selectedItem.toString()
        donorDetails["Location"] = etLocation.text.toString()
        donorDetails["Mobile"] = etMobile.text.toString()
        donorDetails["BloodGroup"] = spinnerBloodGrp.selectedItem.toString()
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
        spinnerGender.setSelection(0)
        etLocation.setText("")
        etMobile.setText("")
        spinnerBloodGrp.setSelection(0)
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }


}