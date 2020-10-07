package com.gprifti.app.ui

import android.app.AlertDialog.Builder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.gprifti.app.R
import com.gprifti.app.databinding.ListItemActiveTaskBindingImpl
import com.gprifti.app.databinding.ListItemArchivedTaskBindingImpl
import com.gprifti.app.models.Task
import kotlinx.android.synthetic.main.list_item_active_task.view.*

class SearchTaskAdapter(var task: List<Task>, context: Context) : RecyclerView.Adapter<SearchTaskAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        abstract fun bind(item: T)
    }

    private var adapterDataList: List<Task> = task
    private var _context = context

    /** we have two types of layouts, active and archived one */
    companion object {
        private const val TYPE_ACTIVE = 1
        private const val TYPE_ARCHIVED = 18
    }

    override fun getItemCount(): Int = adapterDataList.size

    /** depending of the type, we inflate different view */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ACTIVE -> {
                val dataBinding = ListItemActiveTaskBindingImpl.inflate(inflater, parent, false)
                ViewHolderOne(dataBinding)
            }
            TYPE_ARCHIVED -> {
                val dataBinding = ListItemArchivedTaskBindingImpl.inflate(inflater, parent, false)
                ViewHolderTwo(dataBinding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    /** helps to bind the holders, we have two holders: the holder one and two */
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        if (element.status == TYPE_ACTIVE) {
            (holder as ViewHolderOne).bind(element)
        } else if (element.status == TYPE_ARCHIVED) {
            (holder as ViewHolderTwo).bind(element)
        }
    }

    /** based on the status tells decide the type */
    override fun getItemViewType(position: Int): Int {
        return if (task[position].status == TYPE_ACTIVE) TYPE_ACTIVE else TYPE_ARCHIVED
    }

    /** view holder one, shows the project name and the task name */
    inner class ViewHolderOne(dataBinding: ViewDataBinding) : BaseViewHolder<Task>(dataBinding) {
        private val dataBindingTask = dataBinding
        private val txtProjectName = itemView.txtProjectName
        private val txtTaskName = itemView.txtTaskName
        private val cardView = itemView.cardView

        override fun bind(item: Task) {
            dataBindingTask.setVariable(BR.itemData, item)
            dataBindingTask.executePendingBindings()

            txtProjectName.text = item.projectName.toString()
            txtTaskName.text = item.taskName.toString()
            // clicking on the card view trigger the dialog and shows the task name
            cardView.setOnClickListener{
                showTaskName(item.taskName.toString())
            }
        }
    }

    /** view holder two, shows only the project name */
    inner class ViewHolderTwo(dataBinding: ViewDataBinding) : BaseViewHolder<Task>(dataBinding) {
        private val dataBindingTask = dataBinding
        private val txtProjectName = itemView.txtProjectName
        private val cardView = itemView.cardView

        override fun bind(item: Task) {
            dataBindingTask.setVariable(BR.itemData, item)
            dataBindingTask.executePendingBindings()

            txtProjectName.text = item.projectName.toString()

            // clicking on the card view trigger the dialog and shows the task name
            cardView.setOnClickListener{
                showTaskName(item.taskName.toString())
            }
        }
    }

    /** the dialog method which shows the dialog once a row is clicked */
    private fun showTaskName(msg: String) {
        val taskName: String = _context.getString(R.string.label_task_name)
        val builder = Builder(_context)
        builder.setMessage("$taskName $msg")
        builder.setCancelable(true)
        val alert = builder.create()
        alert.window?.attributes?.windowAnimations = R.style.DialogAnimation
        alert.show()
    }
}