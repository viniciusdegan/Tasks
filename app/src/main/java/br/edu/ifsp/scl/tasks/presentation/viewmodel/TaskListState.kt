package br.edu.ifsp.scl.tasks.presentation.viewmodel

import br.edu.ifsp.scl.tasks.domain.model.Task

sealed class TaskListState {
    data class SearchAllSuccess(val tasks: List<Task>) : TaskListState()
    data class Failure(val errorMessage: String) : TaskListState()
    data object EmptyState : TaskListState()
    data object ShowLoading : TaskListState()
    data object HideLoading : TaskListState()
}
