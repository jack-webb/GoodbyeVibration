package me.jackwebb.goodbyevibration.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.jackwebb.goodbyevibration.R
import me.jackwebb.goodbyevibration.databinding.MainFragmentBinding
import me.jackwebb.goodbyevibration.observeNotNull

@AndroidEntryPoint
class AppsFragment : Fragment() {

    private val viewModel by viewModels<AppsViewModel>()

    private lateinit var binding: MainFragmentBinding
    private val appAdapter = AppAdapter {
        when (it) {
            is ClickAction.OnAppChecked -> viewModel.onAppChecked(it.packageName, it.checked)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvApps.adapter = appAdapter

        observeNotNull(viewModel.apps) { appAdapter.items = it }

        observeNotNull(viewModel.showSystemApps) { appAdapter.showSystemApps = it }

        observeNotNull(viewModel.showGoogleApps) { appAdapter.showGoogleApps = it }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showSystem -> {
                item.isChecked = !item.isChecked
                viewModel.showSystemApps(item.isChecked)
            }
            R.id.showGoogle -> {
                item.isChecked = !item.isChecked
                viewModel.showGoogleApps(item.isChecked)
            }
            R.id.resetAll -> {
                viewModel.resetAll()
            }
            R.id.about -> {
                // Todo Nav to about
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        menu.findItem(R.id.showGoogle).isChecked = true // Default

        super.onCreateOptionsMenu(menu, inflater)
    }

    sealed class ClickAction {
        class OnAppChecked(val packageName: String, val checked: Boolean) : ClickAction()
    }

    companion object {
        fun newInstance() = AppsFragment()
    }
}