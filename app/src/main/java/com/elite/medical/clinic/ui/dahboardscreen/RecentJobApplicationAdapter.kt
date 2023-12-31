package com.elite.medical.clinic.ui.dahboardscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elite.medical.databinding.CustomListItemBinding
import com.elite.medical.retrofit.responsemodel.clinic.dashboard.ClinicDashboardModel

class RecentJobApplicationAdapter(val itemList: List<ClinicDashboardModel.NurseApplicant>) :
    RecyclerView.Adapter<RecentJobApplicationAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: CustomListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.layout
        var clinicName: TextView = binding.tv1
        var location: TextView = binding.tv2
        var ratings: TextView = binding.tv3
        var tv4: TextView = binding.tv4


        var label1: TextView = binding.label1
        var label2: TextView = binding.label2
        var label3: TextView = binding.label3
        var label4: TextView = binding.label4

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CustomListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv4.visibility = View.GONE
        holder.label4.visibility = View.GONE


        val nurses = itemList[position].nurses.map { it.name }

        holder.label1.text = "Job Title"
        holder.label2.text = "Job Created At"
        holder.label3.text = "Nurse Name"

        holder.clinicName.text = itemList[position].jobTitle
        holder.location.text = itemList[position].jobCreatedAt
        holder.ratings.text = nurses.joinToString("\n")



/*        var fullLength = false
        holder.layout.setOnClickListener {
            fullLength = !fullLength
            if (fullLength) {
                holder.ratings.maxLines = 40
                holder.ratings.text = nursesName.joinToString("\n")
            } else {
                holder.ratings.maxLines = 1
                holder.ratings.text = "Click to expand..."
            }
        }*/

    }
}