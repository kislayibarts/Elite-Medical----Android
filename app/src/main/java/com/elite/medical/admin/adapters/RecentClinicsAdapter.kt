package com.elite.medical.admin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.elite.medical.admin.ui.sidemenu.approvals.details.ClinicApprovalDetails
import com.elite.medical.databinding.RvItemGenericBinding
import com.elite.medical.retrofit.responsemodel.admin.dashboard.AdminDashboardModel

class RecentClinicsAdapter(
    private val items: List<AdminDashboardModel.Clinic>,
    val context: Context,
    private val separator: String
) :
    RecyclerView.Adapter<RecentClinicsAdapter.ModelViewHolder>() {

    inner class ModelViewHolder(binding: RvItemGenericBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val layout = binding.layout

        val row1 = binding.row1
        val row2 = binding.row2
        val row3 = binding.row3
        val row4 = binding.row4
        val row5 = binding.row5
        val row6 = binding.row6
        val row7 = binding.row7
        val row8 = binding.row8
        val row9 = binding.row9
        val row10 = binding.row10

        var tv1: TextView = binding.tv1
        var tv2: TextView = binding.tv2
        var tv3: TextView = binding.tv3
        var tv4: TextView = binding.tv4
        var tv5: TextView = binding.tv5
        var tv6: TextView = binding.tv6
        var tv7: TextView = binding.tv7
        var tv8: TextView = binding.tv8
        var tv9: TextView = binding.tv9
        var tv10: TextView = binding.tv10

        var label1: TextView = binding.label1
        var label2: TextView = binding.label2
        var label3: TextView = binding.label3
        var label4: TextView = binding.label4
        var label5: TextView = binding.label5
        var label6: TextView = binding.label6
        var label7: TextView = binding.label7
        var label8: TextView = binding.label8
        var label9: TextView = binding.label9
        var label10: TextView = binding.label10

        val btnGoDeep = binding.btnGoDeep

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder(
            RvItemGenericBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {

        val item = items[position]

        if (separator == "ClinicApprovals") {
            holder.row1.isVisible = true
            holder.row2.isVisible = true
            holder.row3.isVisible = true
            holder.row4.isVisible = true
            holder.row5.isVisible = true
            holder.row6.isVisible = false
            holder.row7.isVisible = false
            holder.row8.isVisible = false
            holder.row9.isVisible = false
            holder.row10.isVisible = false

            holder.label1.text = "Name"
            holder.label2.text = "Email"
            holder.label3.text = "Address"
            holder.label4.text = "Clinic Type"
            holder.label5.text = "Approval Status"

            holder.tv1.text = item.name
            holder.tv2.text = item.email
            holder.tv3.text = item.address
            holder.tv4.text = item.clinicType
            holder.tv5.text = item.approvalStatus

            holder.btnGoDeep.isVisible = true
            holder.layout.setOnClickListener {
                val intent = Intent(context, ClinicApprovalDetails::class.java)
                intent.putExtra("details", items[position])
                context.startActivity(intent)
            }
        }
        else if (separator == "Recent Clinics"){
            holder.row1.isVisible = true
            holder.row2.isVisible = true
            holder.row3.isVisible = true
            holder.row4.isVisible = false
            holder.row5.isVisible = false
            holder.row6.isVisible = false
            holder.row7.isVisible = false
            holder.row8.isVisible = false
            holder.row9.isVisible = false
            holder.row10.isVisible = false

            holder.label1.text = "Name"
            holder.label2.text = "Email"
            holder.label3.text = "Locations"

            holder.tv1.text = item.name
            holder.tv2.text = item.email
            holder.tv3.text = item.city
            holder.btnGoDeep.isVisible = false
        }
    }
}