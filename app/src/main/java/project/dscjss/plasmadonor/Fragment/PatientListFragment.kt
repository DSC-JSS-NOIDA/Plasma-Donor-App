package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donor_list_fragment.*
import kotlinx.android.synthetic.main.patient_list_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*
import project.dscjss.plasmadonor.Adapter.PatientListAdapter
import project.dscjss.plasmadonor.Model.PatientModel
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.PatientListViewModel

class PatientListFragment : Fragment() {

    companion object {
        fun newInstance() = PatientListFragment()
    }

    private lateinit var viewModel: PatientListViewModel
    private var patientList = mutableListOf<PatientModel>()
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.patient_list_fragment, container, false)
        getData()
        return view
    }

    private fun getData(){
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("patients")
            .get().addOnSuccessListener {doc->
                val it = doc.documents
                patientList.clear()
                for(i in it){
                    val name = i["Name"].toString()
                    val age = i["Age"].toString()
                    val bloodGroup = i["BloodGroup"].toString()
                    val hospital = i["Hospital"].toString()
                    val gender = i["Gender"].toString()
                    val mobile = i["Mobile"].toString()
                    val email = i["Email"].toString()
                    val diabetes = i["Diabetes"].toString()
                    val location = i["Location"].toString()
                    val liverProblem = i["LiverProblem"].toString()
                    val bpProblem = i["BpProblem"].toString()

                    patientList.add(
                        PatientModel(name,age,gender,location,hospital,
                            bloodGroup,mobile,email,diabetes,liverProblem,bpProblem)
                    )
                }
                setRecyclerview()
            }.addOnCompleteListener {
                spinKitP.visibility = View.GONE
            }.addOnCompleteListener {
                spinKitP.visibility = View.GONE
            }
    }

    private fun setRecyclerview(){
        patientListRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        patientListRecyclerView.adapter = PatientListAdapter(patientList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PatientListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}