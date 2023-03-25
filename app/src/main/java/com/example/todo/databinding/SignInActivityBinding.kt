package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R
import com.google.android.material.textfield.TextInputEditText

class SignInActivityBinding private constructor(
    var rootView: ConstraintLayout,
    var identityView: TextInputEditText,
    var signInButtonView: TextView,
    var signUpText: TextView
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, z: Boolean): SignInActivityBinding {
            val inflate: View = layoutInflater.inflate(R.layout.activity_signin, viewGroup, false)
            if (z) {
                viewGroup?.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): SignInActivityBinding {
            var i = R.id.identity_edit_text
            val identityView: TextInputEditText? = ViewBindings.findChildViewById<View>(view, i) as TextInputEditText?
            if(identityView != null){
                i = R.id.sign_in_button
                val signInButtonView: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                if(signInButtonView != null){
                    i = R.id.sign_up_text_view
                    val signUpText: TextView? = ViewBindings.findChildViewById<View>(view, i) as TextView?
                    if(signUpText != null){
                        return SignInActivityBinding(view as ConstraintLayout, identityView, signInButtonView, signUpText)
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