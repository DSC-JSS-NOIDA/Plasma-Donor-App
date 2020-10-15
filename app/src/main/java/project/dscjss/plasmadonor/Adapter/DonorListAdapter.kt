package project.dscjss.plasmadonor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.dscjss.plasmadonor.Model.DonorModel
import project.dscjss.plasmadonor.R

class DonorListAdapter(val list: List<DonorModel>) :
    RecyclerView.Adapter<DonorListAdapter.ViewHolder>() {

    class ViewHolder(val v: View) :
        RecyclerView.ViewHolder(v) {
        val pName = v.findViewById<TextView>(R.id.tvPatientName)
        val pLocation = v.findViewById<TextView>(R.id.tvPatientLocation)
        val pMobile = v.findViewById<TextView>(R.id.tvPatientMobile)
        val pAge = v.findViewById<TextView>(R.id.tvPatientAge)
        val pBloodGroup = v.findViewById<TextView>(R.id.tvPatientBloodGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.donor_list_item, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pName.text = "Name      : ${list[position].name}"
        holder.pAge.text = "Age       : ${list[position].age}"
        holder.pLocation.text = "Location  : ${list[position].location}"
        holder.pMobile.text = "Phone No. : ${list[position].mobile}"
        holder.pBloodGroup.text = "Blood Group : ${list[position].bloodGroup}"
    }

    override fun getItemCount(): Int = list.size
}
