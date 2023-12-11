package br.edu.ifsp.scl.tasks.presentation.view.fragment;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.tasks.R
import br.edu.ifsp.scl.tasks.databinding.FragmentTaskListBinding
import br.edu.ifsp.scl.tasks.domain.model.Task
import br.edu.ifsp.scl.tasks.domain.utils.Constants.LABEL_PUT_EXTRA_TASK_ID
import br.edu.ifsp.scl.tasks.presentation.view.adapter.TaskAdapter
import br.edu.ifsp.scl.tasks.presentation.viewmodel.TaskControlViewModel
import br.edu.ifsp.scl.tasks.presentation.viewmodel.TaskListState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : Fragment() {

    private val viewModel:TaskControlViewModel by viewModel()

    private var _binding:FragmentTaskListBinding?=null
    private val binding get()=_binding!!

    private lateinit var taskAdapter:TaskAdapter
    override fun onCreateView(
        inflater:LayoutInflater,container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View{
        _binding=FragmentTaskListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?){
        super.onViewCreated(view,savedInstanceState)
        viewModel.getAllTasks()
        setupViewmodel()
        setupAddTaskButton()
    }

    override fun onResume(){
        super.onResume()
        viewModel.getAllTasks()
    }

    private fun setupViewmodel(){
        lifecycleScope.launch{
            viewModel.stateList.collect{
                when(it){
                    TaskListState.EmptyState->binding.textEmptyState.visibility = View.VISIBLE
                    is TaskListState.Failure->{}
                    TaskListState.HideLoading->binding.loadingTaskList.visibility = View.GONE
                    TaskListState.ShowLoading->binding.loadingTaskList.visibility = View.VISIBLE
                    is TaskListState.SearchAllSuccess->setupRecycler(it.tasks)
                }
            }
        }
    }

    private fun setupRecycler(tasks:List<Task>){
        taskAdapter=TaskAdapter(tasks,::onTaskClick).apply{submitList(tasks)}
        binding.recyclerTasksList.adapter=taskAdapter
    }

    private fun onTaskClick(task:Task){
        val bundle=Bundle()
        bundle.putInt(LABEL_PUT_EXTRA_TASK_ID,task.id)
        findNavController().navigate(R.id.go_to_addTaskFragment,bundle)
    }

    private fun setupAddTaskButton(){
        binding.buttonAddTask.setOnClickListener{
            val bundle=Bundle()
            bundle.putInt(LABEL_PUT_EXTRA_TASK_ID,-1)
            findNavController().navigate(R.id.go_to_addTaskFragment,Bundle())
        }
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding=null
    }
}