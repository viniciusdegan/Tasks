package br.edu.ifsp.scl.tasks.presentation.viewmodel

import br.edu.ifsp.scl.tasks.domain.model.Task

sealed class ManageTaskState {
    data object InsertSuccess : ManageTaskState()
    data object UpdateSuccess : ManageTaskState()
    data object DeleteSuccess : ManageTaskState()
    data object ShowLoading : ManageTaskState()
    data object HideLoading : ManageTaskState()
    data class GetByIdSuccess(val task: Task) : ManageTaskState()
    data class Failure(val errorMessage: String) : ManageTaskState()

}