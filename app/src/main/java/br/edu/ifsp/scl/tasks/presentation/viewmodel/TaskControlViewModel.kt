package br.edu.ifsp.scl.tasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.tasks.domain.model.Task
import br.edu.ifsp.scl.tasks.domain.usecase.TaskControlUseCase
import br.edu.ifsp.scl.tasks.domain.utils.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskControlViewModel(private val useCase: TaskControlUseCase) : ViewModel() {

    private val _stateList = MutableStateFlow<TaskListState>(TaskListState.HideLoading)
    val stateList = _stateList.asStateFlow()

    private val _stateManagement = MutableStateFlow<ManageTaskState>(ManageTaskState.HideLoading)
    val stateManagement = _stateManagement.asStateFlow()
    fun insert(task: Task) {
        viewModelScope.launch {
            _stateManagement.value = ManageTaskState.ShowLoading
            useCase.insert(task)
            _stateManagement.value = ManageTaskState.HideLoading
            _stateManagement.value = ManageTaskState.InsertSuccess
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            _stateList.value = TaskListState.ShowLoading
            val response = useCase.getAllTasks()
            _stateList.value = TaskListState.HideLoading
            response.flow(
                { tasks ->
                    if (tasks.isNotEmpty())
                        _stateList.value = TaskListState.SearchAllSuccess(tasks)
                    else
                        _stateList.value = TaskListState.EmptyState
                }, {
                    _stateList.value = TaskListState.Failure(it)
                }
            )
        }
    }

    fun getTaskById(id: Int) {
        viewModelScope.launch {
            _stateManagement.value = ManageTaskState.ShowLoading
            val response = useCase.getTaskById(id)
            _stateManagement.value = ManageTaskState.HideLoading
            response.flow(
                { task ->
                    _stateManagement.value = ManageTaskState.GetByIdSuccess(task)
                }, {
                    _stateManagement.value = ManageTaskState.Failure(it)
                }
            )
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            _stateManagement.value = ManageTaskState.ShowLoading
            useCase.update(task)
            _stateManagement.value = ManageTaskState.HideLoading
            _stateManagement.value = ManageTaskState.UpdateSuccess
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            _stateManagement.value = ManageTaskState.ShowLoading
            useCase.delete(task)
            _stateManagement.value = ManageTaskState.HideLoading
            _stateManagement.value = ManageTaskState.DeleteSuccess
        }
    }
}
