package com.elite.medical.nurse.fragments.clinics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elite.medical.databinding.FragmentEnrolledClinicsBinding
import com.elite.medical.nurse.adapters.clinics.EnrolledClinicsAdapter
import com.elite.medical.nurse.viewmodels.clinics.ClinicsViewModel
import com.elite.medical.utils.HelperMethods

class EnrolledClinics : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentEnrolledClinicsBinding
    private lateinit var backBtn: ImageButton
    private lateinit var loader: ProgressBar
    private lateinit var viewModel: ClinicsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEnrolledClinicsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ClinicsViewModel::class.java]
        viewModel.getEnrolledClinicsList()
        initBindings()

        binding.btnBack.setOnClickListener { activity?.onBackPressed() }

        viewModel.clinicList.observe(viewLifecycleOwner) { clinics ->
            if (clinics.isNullOrEmpty()) {
                binding.textabovervlistofassocnurse.isVisible = false
                HelperMethods.showDialog(
                    "No Clinics Enrolled",
                    "Go Back",
                    requireContext(),
                    activity,
                    activity?.onBackPressedDispatcher?.onBackPressed()
                )
                activity?.onBackPressedDispatcher?.onBackPressed()

            } else {
                val adapter = EnrolledClinicsAdapter(clinics!!, viewModel, requireContext())
                binding.rvEnrolledClinics.layoutManager = LinearLayoutManager(requireContext())
                binding.rvEnrolledClinics.adapter = adapter
            }
            binding.loader.isVisible = false

        }

        return binding.root
    }


    private fun initBindings() {
        backBtn = binding.btnBack
        loader = binding.loader

        backBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (view?.id) {
            binding.btnBack.id -> {
                activity?.onBackPressed()
            }
        }
    }


}