package com.github.af2905.timetrackersdk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.af2905.timetracker.TimeTracker
import com.github.af2905.timetrackersdk.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {

        }
    }
    private fun start() {
        runBlocking {
            TimeTracker.start("load_user")
            delay(2000)
            TimeTracker.stop("load_user")
        }

        TimeTracker.printAllDurations()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}