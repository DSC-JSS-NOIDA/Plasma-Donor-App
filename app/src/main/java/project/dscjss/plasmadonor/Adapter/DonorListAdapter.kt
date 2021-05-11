package project.dscjss.plasmadonor.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.donor_list_item.*
import project.dscjss.plasmadonor.Fragment.data.Donor
import project.dscjss.plasmadonor.R

class DonorListAdapter(
    options: FirestorePagingOptions<Donor>,
    private val onProgress: () -> Unit,
    private val onLoaded: () -> Unit,
) : FirestorePagingAdapter<Donor, DonorListAdapter.DonorViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.donor_list_item, parent, false)
        return DonorViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: DonorViewHolder, p1: Int, donor: Donor) {
        viewHolder.bind(donor)
    }

    override fun onError(e: Exception) {
        super.onError(e)
        onLoaded.invoke()
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> onProgress.invoke()
            LoadingState.LOADING_MORE -> onProgress.invoke()
            LoadingState.LOADED -> onLoaded.invoke()
            LoadingState.ERROR -> onLoaded.invoke()
            LoadingState.FINISHED -> onLoaded.invoke()
        }
    }

    class DonorViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(donor: Donor) {
            val context = containerView.context
            txtName.text = donor.name
            txtLocation.text = donor.location
            txtGender.text = donor.gender
            txtBloodGroup.text = donor.bloodGroup
        }
    }
}
