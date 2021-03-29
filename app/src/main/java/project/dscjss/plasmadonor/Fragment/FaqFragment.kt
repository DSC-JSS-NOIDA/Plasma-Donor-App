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
import kotlinx.android.synthetic.main.faq_fragment.*
import project.dscjss.plasmadonor.Adapter.FaqAdapter
import project.dscjss.plasmadonor.Fragment.data.Faq
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.FaqViewModel

class FaqFragment : Fragment(R.layout.faq_fragment) {

    private lateinit var viewModel: FaqViewModel
    private val firestore = Firebase.firestore
    private val query = firestore.collection("faq")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FaqViewModel::class.java)
        getData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "FAQs"
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getData() {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        val options = FirestorePagingOptions.Builder<Faq>()
            .setLifecycleOwner(this)
            .setQuery(query, config, Faq::class.java)
            .build()

        val adapter = FaqAdapter(
            requireContext(),
            options,
            onProgress = { progress_circular.isVisible = true },
            onLoaded = { progress_circular.isVisible = false }
        )
        faqRecycle.adapter = adapter
    }
}
