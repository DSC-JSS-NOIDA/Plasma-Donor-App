package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.faq_fragment.*
import project.dscjss.plasmadonor.Adapter.FaqAdapter
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.FaqViewModel

class FaqFragment : Fragment() {

    companion object {
        fun newInstance() = FaqFragment()
    }

    private  lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var viewModel: FaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.faq_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FaqViewModel::class.java)
        firebaseFirestore = FirebaseFirestore.getInstance()
        // TODO: Use the ViewModel

        val data = viewModel.getData(firebaseFirestore)
        val faqAdapter = FaqAdapter(data)
        faqRecycle.adapter = faqAdapter
    }
}
