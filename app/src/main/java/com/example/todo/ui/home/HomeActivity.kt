package com.example.todo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.commons.SnappingLinearLayoutManager
import com.example.todo.commons.viewmodel.BaseViewModel
import com.example.todo.commons.viewmodel.BaseViewModelFactory
import com.example.todo.commons.viewmodel.ViewModelRegistrar
import com.example.todo.data.api.ToDoMapper
import com.example.todo.data.domain.ApiServiceGenerator
import com.example.todo.data.session.ToDoSession
import com.example.todo.data.source.RemoteTodoDataSource
import com.example.todo.data.source.ToDoRepository
import com.example.todo.data.storage.SharedPrefStorageDataSource
import com.example.todo.data.storage.StorageRepository
import com.example.todo.databinding.HomeActivityBinding
import com.example.todo.ui.authentication.signin.SignInActivity
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import com.example.todo.ui.toDo.adapter.ToDoAction
import com.example.todo.ui.toDo.adapter.ToDosAdapter
import com.example.todo.ui.toDo.adapter.payload.SubToDoPayload
import com.example.todo.ui.toDo.adapter.payload.ToDoPayload
import com.example.todo.ui.toDo.add.ToDoBottomSheet
import com.example.todo.ui.toDo.viewmodel.ToDoActionsViewModel

class HomeActivity : AppCompatActivity(), HomeActivityHandle, ViewModelRegistrar {
    private val binding: HomeActivityBinding by lazy {
        return@lazy HomeActivityBinding.inflate(layoutInflater, null, false)
    }

    private val homeViewModel: HomeViewModel by lazy {
        return@lazy ViewModelProvider(this, BaseViewModelFactory{
            HomeViewModel(ToDoRepository(RemoteTodoDataSource(ApiServiceGenerator.INSTANCE.getToDoClient(), ToDoMapper())))
        })[HomeViewModel::class.java]
    }

    private val toDoActionsViewModel: ToDoActionsViewModel by lazy {
        return@lazy ViewModelProvider(this, BaseViewModelFactory{
            ToDoActionsViewModel(ToDoRepository(RemoteTodoDataSource(ApiServiceGenerator.INSTANCE.getToDoClient(), ToDoMapper())))
        })[ToDoActionsViewModel::class.java]
    }

    private val toDosAdapter: ToDosAdapter by lazy {
        return@lazy ToDosAdapter(this) { handleToDoAction(it) }
    }
    var layoutManager: LinearLayoutManager? = null

    private fun handleToDoAction(action: ToDoAction) {
        if (action is ToDoAction.CompleteToDo){
            toDoActionsViewModel.toggleCompleteToDo(action.getToDo())
        }else if (action is ToDoAction.CompleteSubToDo){
            toDoActionsViewModel.toggleCompleteSubToDo(action.getSubToDo())
        }else if (action is ToDoAction.ViewSubToDos){
            homeViewModel.fetchSubToDos(action.getToDo())
        } else if (action is ToDoAction.AddSubToDo){
            showAddToDoBottomSheet(ToDoBottomSheet(action.getToDo(), ToDoBottomSheet.SUB_TODO_Add))
        } else if (action is ToDoAction.EditSubToDo){
            showAddToDoBottomSheet(ToDoBottomSheet(action.getSubToDo(), ToDoBottomSheet.SUB_TODO_Edit))
        }else if (action is ToDoAction.DeleteSubToDo){
            toDoActionsViewModel.deleteSubToDO(action.getSubToDo())
        }else if (action is ToDoAction.EditToDo){
            showAddToDoBottomSheet(ToDoBottomSheet(action.getToDo(), ToDoBottomSheet.TODO_EDIT))
        }else if (action is ToDoAction.DeleteToDo){
            toDoActionsViewModel.deleteToDO(action.getToDo())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.rootView)
        title = ToDoSession.INSTANCE.getEmail()
        registerViewModels(homeViewModel, toDoActionsViewModel)

        binding.floatingActionButton.setOnClickListener {
            showAddToDoBottomSheet(ToDoBottomSheet())
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.fetchToDos()
        }

        this.layoutManager = SnappingLinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = toDosAdapter
        binding.recyclerView.setHasFixedSize(true)

        toDoActionsViewModel.toDoAddedLiveData.observe(this) {
            toDosAdapter.addItem(it)
            binding.recyclerView.postDelayed({binding.recyclerView.smoothScrollToPosition(toDosAdapter.itemCount - 1)}, 200)
        }

        toDoActionsViewModel.toDoUpdatedLiveData.observe(this) {
            toDosAdapter.applyPayload(ToDoPayload.ToDoUpdated(it.id!!, it))
        }

        toDoActionsViewModel.subToDoAddedLiveData.observe(this) {
            toDosAdapter.notifySubToDoAdded(it)?.let { it1 -> binding.recyclerView.postDelayed({binding.recyclerView.smoothScrollToPosition(it1)}, 200) }
        }

        toDoActionsViewModel.subToDoUpdatedLiveData.observe(this) {
            toDosAdapter.applyPayload(SubToDoPayload.SubToDoUpdated(it.id!!, it))
        }

        toDoActionsViewModel.subToDoDeletedLiveData.observe(this) {
             toDosAdapter.notifySubToDoDeleted(it)
        }

        toDoActionsViewModel.toDoDeletedLiveData.observe(this) {
            toDosAdapter.notifyToDoDeleted(it)
        }

        homeViewModel.toDosLiveData.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            if (it.page == 0) {
                toDosAdapter.removeAll()
                toDosAdapter.addItems(it.items)
                //applyPostCommentEntryAction()
            }
        })

        homeViewModel.subToDosLiveData.observe(this, Observer {
            it.first?.let { it1 -> toDosAdapter.showSubToDos(it1, it.second) }
        })
    }

    private fun showAddToDoBottomSheet(toDoBottomSheet: ToDoBottomSheet) {
        toDoBottomSheet.show(supportFragmentManager, ToDoBottomSheet.TAG)
    }

    override fun onAddToDoRequested(toDo: ToDo) {
        toDoActionsViewModel.addToDo(toDo)
    }

    override fun onAddSubToDoRequested(subToDo: SubToDo) {
        toDoActionsViewModel.addSubToDo(subToDo)
    }

    override fun onUpdateToDoRequested(toDo: ToDo) {
        toDoActionsViewModel.updateToDo(toDo)
    }

    override fun onUpdateSubToDoRequested(subToDo: SubToDo) {
        toDoActionsViewModel.updateSubToDo(subToDo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.itemId
        return if (itemId == R.id.sign_out) {
            StorageRepository(SharedPrefStorageDataSource(this)).clear()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    override fun registerViewModels(vararg viewModelArr: ViewModel) {
        for (viewModel in viewModelArr) {
            registerViewModel(viewModel)
        }
    }

    private fun registerViewModel(viewModel: ViewModel) {
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver(viewModel as LifecycleObserver)
        }
        if (viewModel is BaseViewModel) {
            registerViewModel(viewModel)
        }
    }
    private fun registerViewModel(baseViewModel: BaseViewModel) {
        baseViewModel.getToastLiveData().observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}