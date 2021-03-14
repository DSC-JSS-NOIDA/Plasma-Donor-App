package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.patient_list_fragment.*
import project.dscjss.plasmadonor.Adapter.PatientListAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.dscjss.plasmadonor.Fragment.data.Patient
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.PatientListViewModel

class PatientListFragment : Fragment(R.layout.patient_list_fragment) {

    private lateinit var viewModel: PatientListViewModel
    private val firebase = Firebase.firestore
    private val query = firebase.collection("patients")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatientListViewModel::class.java)
        getData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Patients"
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getData() {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        val options = FirestorePagingOptions.Builder<Patient>()
            .setLifecycleOwner(this)
            .setQuery(query, config, Patient::class.java)
            .build()

        val adapter = PatientListAdapter(
            options,
            onProgress = { progress_circular.isVisible = true },
            onLoaded = { progress_circular.isVisible = false }
        )
        patientListRecyclerView.adapter = adapter
    }
}
