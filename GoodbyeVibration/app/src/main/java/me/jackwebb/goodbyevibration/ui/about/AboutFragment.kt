package me.jackwebb.goodbyevibration.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.jackwebb.goodbyevibration.databinding.ScreenAboutBinding

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private lateinit var binding: ScreenAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScreenAboutBinding.inflate(inflater, container, false)
        binding.attributions.movementMethod = LinkMovementMethod.getInstance() // Enable links
        return binding.root
    }


    companion object {
        fun newInstance() = AboutFragment()
    }
}