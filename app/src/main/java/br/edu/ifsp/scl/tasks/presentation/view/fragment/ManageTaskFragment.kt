package br.edu.ifsp.scl.tasks.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.tasks.R
import br.edu.ifsp.scl.tasks.databinding.FragmentAddTaskBinding
import br.edu.ifsp.scl.tasks.domain.model.Task
import br.edu.ifsp.scl.tasks.domain.utils.Constants.LABEL_PUT_EXTRA_TASK_ID
import br.edu.ifsp.scl.tasks.presentation.viewmodel.ManageTaskState
import br.edu.ifsp.scl.tasks.presentation.viewmodel.TaskControlViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class ManageTaskFragment : Fragment() {

    private val viewModel: TaskControlViewModel by viewModel()

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var taskId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBundleId()
        setupViewmodel()
        setupManagementTaskButtons()
        setupView()
    }

    private fun setupBundleId() {
        taskId = requireArguments().getInt(LABEL_PUT_EXTRA_TASK_ID)
    }

    private fun setupView() {
        if (taskId != 0) {
            viewModel.getTaskById(taskId)
        } else {
            setupAddTask()
        }
    }

    private fun setupAddTask() {
        binding.apply {
            buttonAddTask.visibility = View.VISIBLE
            buttonUpdateTask.visibility = View.GONE
            buttonDeleteTask.visibility = View.GONE
        }
    }

    private fun setupViewmodel() {
        lifecycleScope.launch{
            viewModel.stateManagement.collect{
                when(it){
                    ManageTaskState.HideLoading->{}
                    ManageTaskState.ShowLoading->{}
                    ManageTaskState.InsertSuccess->{
                        Toast.makeText(
                            activity,getString(R.string.label_add_task),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    is ManageTaskState.Failure->{}
                    is ManageTaskState.GetByIdSuccess->fillFields(it.task)
                    ManageTaskState.UpdateSuccess->{
                        Toast.makeText(
                            activity,getString(R.string.label_change_task),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    ManageTaskState.DeleteSuccess->{
                        Toast.makeText(
                            activity,getString(R.string.label_remove_task),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun fillFields(searchTask: Task) {
        task = searchTask
        binding.apply {
            editTextTaskName.setText(searchTask.name)
            editTextWriters.setText(searchTask.writer)
            editTextStatus.setText(searchTask.status)
            buttonAddTask.visibility = View.GONE
            buttonUpdateTask.visibility = View.VISIBLE
            buttonDeleteTask.visibility = View.VISIBLE
        }
    }

    private fun setupManagementTaskButtons() {
        binding.buttonAddTask.setOnClickListener {
            binding.apply {
                task = Task(
                    editTextTaskName.text.toString(),
                    editTextWriters.text.toString(),
                    editTextStatus.text.toString()
                )
            }
            viewModel.insert(task)
        }
        binding.buttonUpdateTask.setOnClickListener {
            binding.apply {
                task = Task(
                    editTextTaskName.text.toString(),
                    editTextWriters.text.toString(),
                    editTextStatus.text.toString(),
                    id = taskId
                )
            }
            viewModel.update(task)
        }
        binding.buttonDeleteTask.setOnClickListener {
            viewModel.delete(task)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
