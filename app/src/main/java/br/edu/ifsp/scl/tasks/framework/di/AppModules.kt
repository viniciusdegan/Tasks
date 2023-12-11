package br.edu.ifsp.scl.tasks.framework.di

import androidx.room.Room
import br.edu.ifsp.scl.tasks.data.repository.TasksRepositoryImpl
import br.edu.ifsp.scl.tasks.domain.usecase.TaskControlUseCase
import br.edu.ifsp.scl.tasks.framework.datasource.TasksDatabase
import br.edu.ifsp.scl.tasks.presentation.viewmodel.TaskControlViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                TasksDatabase::class.java,
                TasksDatabase.TASKS_DATABASE_NAME
            )
            .build()
    }
    single { get<TasksDatabase>().tasksDao() }
    single<TaskControlUseCase> { TasksRepositoryImpl(get()) }
    viewModel { TaskControlViewModel(get()) }
}
