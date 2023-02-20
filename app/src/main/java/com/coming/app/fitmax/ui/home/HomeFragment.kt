package com.coming.app.fitmax.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coming.app.fitmax.R
import com.coming.app.fitmax.data.DataHome
import com.coming.app.fitmax.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arrMonthName = ArrayList<DataHome>()
        for (i in 0 until 12) {
            arrMonthName.add(DataHome("Complete 3 squats exercises", "with 100 reps", 113))
        }
//
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewHome.adapter = MainAdapter(arrMonthName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMove: AppCompatImageButton = view.findViewById(R.id.icon_right_home)
        btnMove.setOnClickListener {
            findNavController().navigate(R.id.move_net_tensorflow_fragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}