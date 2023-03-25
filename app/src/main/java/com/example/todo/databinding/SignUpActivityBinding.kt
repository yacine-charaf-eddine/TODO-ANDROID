package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R
import com.google.android.material.textfield.TextInputEditText

class SignUpActivityBinding private constructor(
    var rootView: ConstraintLayout,
    var backView: ImageView,
    var emailView: TextInputEditText,
    var signUpButtonView: TextView,
    var signInText: TextView
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, z: Boolean): SignUpActivityBinding {
            val inflate: View = layoutInflater.inflate(R.layout.activity_signup, viewGroup, false)
            if (z) {
                viewGroup?.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): SignUpActivityBinding {
            var i = R.id.back_image_view
            val backView: ImageView? = ViewBindings.findChildViewById<View>(view, i) as ImageView?
            if(backView != null){
                i = R.id.email_edit_text
                val emailView: TextInputEditText? = ViewBindings.findChildViewById<View>(view, i) as TextInputEditText?
                if(emailView != null){
                    i = R.id.sign_up_button
                    val signUpButtonView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                    if(signUpButtonView != null){
                        i = R.id.sign_in_text_view
                        val signInText: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                        if(signInText != null){
                            return SignUpActivityBinding(view as ConstraintLayout,backView, emailView, signUpButtonView, signInText)
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