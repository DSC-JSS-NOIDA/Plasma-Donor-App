package project.dscjss.plasmadonor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.dscjss.plasmadonor.models.PatientModel
import project.dscjss.plasmadonor.R

class PatientListAdapter(val list: List<PatientModel>) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>(){

    class ViewHolder(val v : View) :
        RecyclerView.ViewHolder(v){
        val pName = v.findViewById<TextView>(R.id.tvPatientName)
        val pLocation = v.findViewById<TextView>(R.id.tvPatientLocation)
        val pHosital = v.findViewById<TextView>(R.id.tvPatientHospital)
        val pMobile = v.findViewById<TextView>(R.id.tvPatientMobile)
        val pAge = v.findViewById<TextView>(R.id.tvPatientAge)
        val pBloodGroup = v.findViewById<TextView>(R.id.tvPatientBloodGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.patient_list_item,parent,false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pName.text     = "Name      : ${list[position].name}"
        holder.pAge.text      = "Age       : ${list[position].age}"
        holder.pLocation.text = "Location  : ${list[position].location}"
        holder.pMobile.text   = "Phone No. : ${list[position].mobile}"
        holder.pHosital.text  = "Hospital  : ${list[position].hospital}"
        holder.pBloodGroup.text = "Blood Group : ${list[position].bloodGroup}"
    }

    override fun getItemCount(): Int = list.size
}