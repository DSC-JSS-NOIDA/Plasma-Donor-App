package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donor_list_fragment.*
import project.dscjss.plasmadonor.Adapter.DonorListAdapter
import project.dscjss.plasmadonor.Fragment.data.Donor
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.DonorListViewModel

class DonorListFragment : Fragment(R.layout.donor_list_fragment) {

    private lateinit var viewModel: DonorListViewModel
    private val firebase = Firebase.firestore
    private val query = firebase.collection("donors")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DonorListViewModel::class.java)
        getData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Donors"
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getData() {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        val options = FirestorePagingOptions.Builder<Donor>()
            .setLifecycleOwner(this)
            .setQuery(query, config, Donor::class.java)
            .build()

        val adapter = DonorListAdapter(
            options,
            onProgress = { progress_circular.isVisible = true },
            onLoaded = { progress_circular.isVisible = false }
        )
        donorListRecyclerView.adapter = adapter
    }
}
