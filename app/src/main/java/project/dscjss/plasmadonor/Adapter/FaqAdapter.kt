package project.dscjss.plasmadonor.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.faq_list.*
import project.dscjss.plasmadonor.Fragment.data.Faq
import project.dscjss.plasmadonor.R

class FaqAdapter(
    private val context: Context,
    options: FirestorePagingOptions<Faq>,
    private val onProgress: () -> Unit,
    private val onLoaded: () -> Unit,
) : FirestorePagingAdapter<Faq, FaqAdapter.FaqViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.faq_list, parent, false)
        return FaqViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: FaqViewHolder, p1: Int, faq: Faq) {
        viewHolder.bind(viewHolder, faq)
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

    inner class FaqViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(viewHolder: FaqViewHolder, faq: Faq) {
            tvQuestion.text = faq.question
            tvSolution.text = faq.solution

            viewHolder.itemView.setOnClickListener {
                val drawable = if (tvSolution.isVisible)
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_down_24)!!
                else
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_up_24)!!

                tvQuestion.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                tvSolution.isVisible = !tvSolution.isVisible
            }
        }
    }
}
