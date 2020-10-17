package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.patient_list_fragment.*
import project.dscjss.plasmadonor.Adapter.PatientListAdapter
import project.dscjss.plasmadonor.models.PatientModel
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
        return view
    }

    private fun getData(){
        progress_circular.visibility = View.VISIBLE
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
                progress_circular.visibility = View.GONE
                setRecyclerview()
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

        getData()
    }

}