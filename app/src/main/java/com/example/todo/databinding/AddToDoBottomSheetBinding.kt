package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R

class AddToDoBottomSheetBinding private constructor(
    var rootView: ConstraintLayout,
    var titleView: EditText,
    var endDateView: TextView,
    var descView: EditText,
    var submitView: Button
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, z: Boolean): AddToDoBottomSheetBinding {
            val inflate: View = layoutInflater.inflate(R.layout.add_to_do_bottom_sheet, viewGroup, false)
            if (z) {
                viewGroup?.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): AddToDoBottomSheetBinding {
            var i = R.id.title_view
            val titleView: EditText? = ViewBindings.findChildViewById<View>(view, i) as EditText?
            if(titleView != null){
                i = R.id.end_date_view
                val endDateView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                if(endDateView != null){
                    i = R.id.description_view
                    val descView: EditText? = ViewBindings.findChildViewById<View>(view, i) as EditText?
                    if(descView != null){
                        i = R.id.submit_button
                        val submitView: Button? = ViewBindings.findChildViewById<View>(view, i) as Button?
                        if(submitView != null){
                            return AddToDoBottomSheetBinding(view as ConstraintLayout, titleView, endDateView, descView, submitView)
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