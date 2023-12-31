package com.elite.medical.clinic.fragments.jobs.applicants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.elite.medical.R
import com.elite.medical.clinic.ui.sidemenu.jobs.viewmodels.JobApplicantsViewModel
import com.elite.medical.databinding.CustomListItemBinding
import com.elite.medical.retrofit.responsemodel.clinic.sidemenu.jobs.applicants.ClinicJobApplicantsModel
import com.elite.medical.retrofit.responsemodel.nurse.jobs.appliedjobs.AppliedJobsModel

class NurseListAdapter(
    private val items: List<ClinicJobApplicantsModel.NurseApplicant.Nurse>,
    private val isJobClosed: Boolean
) :
    RecyclerView.Adapter<NurseListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val layout = binding.layout

        var tv1: TextView = binding.tv1
        var tv2: TextView = binding.tv2
        var tv3: TextView = binding.tv3
        var tv4: TextView = binding.tv4

        var label1: TextView = binding.label1
        var label2: TextView = binding.label2
        var label3: TextView = binding.label3
        var label4: TextView = binding.label4

        val btnGoDeep = binding.btnGoDeep

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CustomListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.label1.text = "Nurse Name"
        holder.label2.text = "License Type"
        holder.label3.text = "License Expiry"
        holder.label4.text = "Experience (In Years)"

        holder.btnGoDeep.visibility = View.VISIBLE

        holder.tv1.text = items[position].name
        holder.tv2.text = items[position].licenseType
        holder.tv3.text = items[position].licenseExpiry
        holder.tv4.text = items[position].experience


        if (isJobClosed) holder.btnGoDeep.isVisible = false
        else holder.layout.setOnClickListener { onItemClicked?.invoke(items.elementAt(position)) }


    }

    var onItemClicked: ((ClinicJobApplicantsModel.NurseApplicant.Nurse) -> Unit)? = null


}