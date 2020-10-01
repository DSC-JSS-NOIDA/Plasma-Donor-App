package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.donor_list_fragment.*
import kotlinx.android.synthetic.main.donor_list_item.*
import kotlinx.android.synthetic.main.patient_list_fragment.*
import kotlinx.android.synthetic.main.patient_list_fragment.patientListRecyclerView
import project.dscjss.plasmadonor.Adapter.DonorListAdapter
import project.dscjss.plasmadonor.Adapter.PatientListAdapter
import project.dscjss.plasmadonor.Model.DonorModel
import project.dscjss.plasmadonor.Model.PatientModel
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.DonorListViewModel

class DonorListFragment : Fragment() {

    companion object {
        fun newInstance() = DonorListFragment()
    }

    private lateinit var viewModel: DonorListViewModel
    private var donorList = mutableListOf<DonorModel>()
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.donor_list_fragment, container, false)
        getData()
        return view
    }

    private fun getData(){
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("donors")
            .get().addOnSuccessListener {doc->
                val it = doc.documents
                donorList.clear()
                for(i in it){
                    val name = i["Name"].toString()
                    val age = i["Age"].toString()
                    val bloodGroup = i["BloodGroup"].toString()
                    val gender = i["Gender"].toString()
                    val mobile = i["Mobile"].toString()
                    val email = i["Email"].toString()
                    val diabetes = i["Diabetes"].toString()
                    val location = i["Location"].toString()
                    val liverProblem = i["LiverProblem"].toString()
                    val bpProblem = i["BpProblem"].toString()

                    donorList.add(
                        DonorModel(name,age,gender,location,bloodGroup,
                            mobile,email,diabetes,liverProblem,bpProblem)
                    )
                }
                setRecyclerview()
            }.addOnCompleteListener {
                spinKit.visibility = View.GONE
            }.addOnFailureListener {
                spinKit.visibility = View.GONE
            }
    }

    private fun setRecyclerview(){
        donorListRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        donorListRecyclerView.adapter = DonorListAdapter(donorList)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DonorListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}