package project.dscjss.plasmadonor.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.patient_list_item.*
import project.dscjss.plasmadonor.Fragment.data.Patient
import project.dscjss.plasmadonor.R

class PatientListAdapter(
    options: FirestorePagingOptions<Patient>,
    private val onProgress: () -> Unit,
    private val onLoaded: () -> Unit,
) : FirestorePagingAdapter<Patient, PatientListAdapter.PatientViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.patient_list_item, parent, false)
        return PatientViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: PatientViewHolder, p1: Int, patient: Patient) {
        viewHolder.bind(patient)
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

    class PatientViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(patient: Patient) {
            val context = containerView.context
            tvPatientName.text = context.getString(R.string.list_name, patient.name)
            tvPatientAge.text = context.getString(R.string.list_age, patient.age)
            tvPatientLocation.text = context.getString(R.string.list_location, patient.location)
            tvPatientMobile.text = context.getString(R.string.list_phone, patient.mobile)
            tvPatientHospital.text = context.getString(R.string.list_hospital, patient.hospital)
            tvPatientBloodGroup.text = context.getString(R.string.list_blood, patient.bloodGroup)
        }
    }
}
