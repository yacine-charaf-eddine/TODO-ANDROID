package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R

class SubToDoItemBinding private constructor(
    var rootView: ConstraintLayout,
    var titleView: TextView,
    var descView: TextView,
    var endDateView: TextView,
    var completedView: ImageView,
    var deleteView: ImageView,
    var editView: ImageView
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup, z: Boolean): SubToDoItemBinding {
            val inflate: View = layoutInflater.inflate(R.layout.subtodo_item, viewGroup, false)
            if (z) {
                viewGroup.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): SubToDoItemBinding {
            var i = R.id.title_txt
            val titleView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
            if(titleView != null){
                i = R.id.description_txt
                val descView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                if(descView != null){
                    i = R.id.subtodo_end_date_text_view
                    val endDateView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                    if(endDateView != null){
                        i = R.id.complete_indicator
                        val completedView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
                        if(completedView != null){
                            i = R.id.delete_view
                            val deleteView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
                            if(deleteView != null) {
                                i = R.id.edit_view
                                val editView: ImageView? =
                                    ViewBindings.findChildViewById<View>(view, i) as ImageView?
                                if (editView != null) {
                                    return SubToDoItemBinding(
                                        view as ConstraintLayout,
                                        titleView,
                                        descView,
                                        endDateView,
                                        completedView,
                                        deleteView,
                                        editView
                                    )

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