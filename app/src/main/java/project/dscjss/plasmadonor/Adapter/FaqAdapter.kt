package project.dscjss.plasmadonor.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.dscjss.plasmadonor.models.FaqModel
import project.dscjss.plasmadonor.R

class FaqAdapter(val list: List<FaqModel>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val ques = v.findViewById<TextView>(R.id.tvQuestion)
        val solution = v.findViewById<TextView>(R.id.tvSolution)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.faq_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FaqAdapter.ViewHolder, position: Int) {
        holder.ques.text = list[position].question
        holder.solution.text = list[position].solution

        holder.ques.setOnClickListener {
            if (holder.solution.visibility == View.GONE) {
                holder.solution.visibility = View.VISIBLE
            } else if (holder.solution.visibility == View.VISIBLE) {
                holder.solution.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
