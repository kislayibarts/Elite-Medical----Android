package com.elite.medical.admin.ui.sidemenu.approvals.details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elite.medical.EliteMedical
import com.elite.medical.R
import com.elite.medical.admin.ui.AdminDashboard
import com.elite.medical.admin.ui.sidemenu.approvals.ApprovalsNurse
import com.elite.medical.databinding.ActivityNurseApprovalDetailsBinding
import com.elite.medical.retrofit.apis.admin.sidemenu.approvals.ButtonAPIs
import com.elite.medical.retrofit.responsemodel.admin.sidemenu.approvals.nurseapproval.NurseDetailsFromNurseApprovalModel
import com.elite.medical.utils.endpoints.ConstantsAdmin
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NurseApprovalDetails : AppCompatActivity() {
    lateinit var dateButtonInModal: Button
    lateinit var timeButtonInModal: Button
    lateinit var datePickerDialog: DatePickerDialog
    private lateinit var binding: ActivityNurseApprovalDetailsBinding

    private lateinit var details: NurseDetailsFromNurseApprovalModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNurseApprovalDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        details =
            intent.getParcelableExtra<NurseDetailsFromNurseApprovalModel>(
                "details"
            )!!

        setNurseDetailsData(details)

        binding.btnViewLicence.setOnClickListener {
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(ConstantsAdmin.URL_FOR_IMAGE + details.nurseLicense)
            )
            startActivity(urlIntent)

        }

        if (details?.approvalStatus == "approved") {
            binding.btnGroup.visibility = View.GONE
        }
        if (details.approvalStatus == "cancelled") {
            binding.ApproveBtn.visibility = View.GONE
            binding.CancelBtn.visibility = View.GONE
            binding.ScheduleBtn.text = "Reschedule"
        }
        if (details?.approvalStatus == "unapproved" && details.scheduleStatus == "false") {
            binding.ApproveBtn.visibility = View.GONE
        }


        binding.ApproveBtn.setOnClickListener {
            var token = EliteMedical.AuthTokenAdmin
            token = "Bearer $token"
            val email = details?.email
            if (email != null) {
                ButtonAPIs.approveUserRequest(email, object : ButtonAPIs.Companion.ButtonsCallback {
                    override fun onSuccess(msg: String) {
                        Toast.makeText(this@NurseApprovalDetails, "$msg", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(
                                this@NurseApprovalDetails,
                                ApprovalsNurse::class.java
                            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                    }


                    override fun onResponseErr(msg: String, statusCode: String) {

                        Toast.makeText(this@NurseApprovalDetails, msg, Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(
                                this@NurseApprovalDetails,
                                AdminDashboard::class.java
                            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )

                    }
                })
            }

        }
        binding.ScheduleBtn.setOnClickListener {
            openModal()
        }

        binding.CancelBtn.setOnClickListener {

            var token = EliteMedical.AuthTokenAdmin
            token = "Bearer $token"
            val id = details.id
            ButtonAPIs.cancelNurseRequest(id, object : ButtonAPIs.Companion.ButtonsCallback {
                override fun onSuccess(msg: String) {
                    Toast.makeText(this@NurseApprovalDetails, msg, Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(
                            this@NurseApprovalDetails,
                            ApprovalsNurse::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                }

                override fun onResponseErr(msg: String, statusCode: String) {

                    Toast.makeText(this@NurseApprovalDetails, msg, Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(
                            this@NurseApprovalDetails,
                            AdminDashboard::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )

                }
            })
        }
    }

    private fun setNurseDetailsData(details: NurseDetailsFromNurseApprovalModel) {
            binding.tv1.text = details.name
            binding.tv2.text = details.mobile
            binding.tv3.text = details.email
            binding.tv4.text = details.dob
            binding.tv5.text = details.address
            binding.tv6.text = details.licenseType
            binding.tv7.text = details.licenseExpiry
            binding.tv8.text = details.experience
            binding.tv9.text = details.speciality
            binding.tv10.text = details.usImmgStatus
            binding.tv11.text = details.nclexStatus
            binding.tv12.text = details.cgfnsStatus
            binding.tv13.text = details.approvalStatus
    }

    @SuppressLint("MissingInflatedId")
    fun openModal() {
        initDatePicker()
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_schedule_layout, null)
        dateButtonInModal = dialogView.findViewById(R.id.datePickerButton)
        timeButtonInModal = dialogView.findViewById(R.id.timeButton)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("OK") { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {

                val scheduleDate = dateButtonInModal.text.toString()
                val scheduleTime = timeButtonInModal.text.toString()
                var token = EliteMedical.AuthTokenAdmin
                token = "Bearer $token"
                val id = details.id
                ButtonAPIs.scheduleNurseRequest(
                    id, scheduleDate, scheduleTime, object : ButtonAPIs.Companion.ButtonsCallback {
                        override fun onSuccess(msg: String) {
                            Toast.makeText(
                                this@NurseApprovalDetails,
                                "Nurse Scheduled Successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(
                                Intent(
                                    this@NurseApprovalDetails,
                                    ApprovalsNurse::class.java
                                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            )
                            finish()
                        }

                        override fun onResponseErr(msg: String, statusCode: String) {

                            Toast.makeText(
                                this@NurseApprovalDetails,
                                msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    })
            }
        }
        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = alertDialog.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_600))
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_600))
        }
        dialog.show()
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val formattedDate = makeDateString(day, month + 1, year)
            dateButtonInModal.text = formattedDate


        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    fun makeDateString(day: Int, month: Int, year: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar[year, month - 1] = day
        return sdf.format(calendar.time)
    }

    fun openDatePicker(view: View) {
        datePickerDialog.show()
    }

    fun popTimePicker(view: View) {
        val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                timeButtonInModal.text = java.lang.String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    selectedHour,
                    selectedMinute
                )
            }
        val style = AlertDialog.THEME_HOLO_LIGHT
        val timePickerDialog =
            TimePickerDialog(this, style, onTimeSetListener, 0, 0, true)
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }


}