package me.jackwebb.goodbyevibration.ui.apps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.jackwebb.goodbyevibration.databinding.ItemAppBinding

class AppAdapter(
    val onClickAction: (AppsFragment.ClickAction) -> Unit
) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    var items: List<AppInfo> = emptyList()
        set(value) {
            val diffCallback = AppInfoDiffUtilCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }
        get() {
            return field
                .filterNot { app ->
                    !showSystemApps && Constants.SYSTEM_APP_PACKAGE_PREFIXES.any { prefix ->
                        app.packageName.startsWith(prefix)
                    }
                }
                .filterNot {
                    !showGoogleApps && it.packageName.startsWith(Constants.GOOGLE_APP_PACKAGE_PREFIX)
                }
        }

    var showSystemApps: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var showGoogleApps: Boolean = true
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAppBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(app: AppInfo) {
            binding.app = app
            binding.checkbox.isChecked = app.checked

            binding.root.setOnClickListener { binding.checkbox.toggle() }

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onClickAction.invoke(
                    AppsFragment.ClickAction.OnAppChecked(
                        app.packageName,
                        isChecked
                    )
                )
            }
        }
    }

    class AppInfoDiffUtilCallback(
        private val oldList: List<AppInfo>,
        private val newList: List<AppInfo>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].packageName == newList[newItemPosition].packageName

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]

            return old.icon == new.icon
                    && old.name == new.name
                    && old.checked == new.checked
        }
    }
}