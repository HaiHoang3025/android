package com.coming.app.fitmax.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coming.app.fitmax.databinding.FragmentSplashBinding

import com.coming.app.fitmax.ui.tabs.BottomTabs

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.bottom_tab_fragment)
//            activity?.let {
//                val intent = Intent(it, BottomTabs::class.java)
//                it.startActivity(intent)
//            }
        }, 2000)
    }

}