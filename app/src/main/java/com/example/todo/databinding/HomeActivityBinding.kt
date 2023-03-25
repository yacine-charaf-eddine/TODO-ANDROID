package com.example.todo.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.example.todo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivityBinding private constructor(
    var rootView: ConstraintLayout,
    var swipeRefreshLayout: SwipeRefreshLayout,
    var recyclerView: RecyclerView,
    var floatingActionButton: FloatingActionButton
) : ViewBinding {

    companion object{
        fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, z: Boolean): HomeActivityBinding {
            val inflate: View = layoutInflater.inflate(R.layout.activity_home, viewGroup, false)
            if (z) {
                viewGroup?.addView(inflate)
            }
            return bind(inflate)
        }

        private fun bind(view: View): HomeActivityBinding {
            var i = R.id.swipe_refresh_layout
            val swipeRefreshLayout: SwipeRefreshLayout? = ViewBindings.findChildViewById<View>(view, i) as SwipeRefreshLayout?
            if(swipeRefreshLayout != null){
                i = R.id.recycler_view
                val recyclerView: RecyclerView? = ViewBindings.findChildViewById<View>(view, i) as RecyclerView?
                if(recyclerView != null){
                    i = R.id.floatingActionButton
                    val floatingActionButton: FloatingActionButton? = ViewBindings.findChildViewById<View>(view, i) as FloatingActionButton?
                    if(floatingActionButton != null){
                        return HomeActivityBinding(view as ConstraintLayout, swipeRefreshLayout, recyclerView, floatingActionButton)
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