package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R

class ToDoItemBinding private constructor(
    var rootView: ConstraintLayout,
    var titleView: TextView,
    var descView: TextView,
    var endDateView: TextView,
    var completedView: ImageView,
    var addSubToDoView: TextView,
    var subToDoTriggerStateTextView: TextView,
    var deleteView: ImageView,
    var editView: ImageView
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup, z: Boolean): ToDoItemBinding {
            val inflate: View = layoutInflater.inflate(R.layout.todo_item, viewGroup, false)
            if (z) {
                viewGroup.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): ToDoItemBinding {
            var i = R.id.title_txt
            val titleView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
            if(titleView != null){
                i = R.id.description_txt
                val descView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                if(descView != null){
                    i = R.id.todo_end_date_text_view
                    val endDateView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                    if(endDateView != null){
                        i = R.id.complete_indicator
                        val completedView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
                        if(completedView != null){
                            i = R.id.add_subtodo_text_view
                            val addSubToDoView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                            if(addSubToDoView != null){
                                i = R.id.subtodo_trigger_state_text_view
                                val repliesTriggerStateTextView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                                if(repliesTriggerStateTextView != null){
                                    i = R.id.delete_view
                                    val deleteView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
                                    if(deleteView != null){
                                        i = R.id.edit_view
                                        val editView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
                                        if(editView != null){
                                            return ToDoItemBinding(view as ConstraintLayout, titleView, descView, endDateView, completedView, addSubToDoView, repliesTriggerStateTextView, deleteView, editView)

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            throw NullPointerException("Missing required view with ID: " + view.resources.getResourceName(i))
        }
    }

    override fun getRoot(): ConstraintLayout {
        return this.rootView
    }
}