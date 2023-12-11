package br.edu.ifsp.scl.tasks

import android.app.Application
import br.edu.ifsp.scl.tasks.framework.di.appModules
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TasksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@TasksApplication)
            modules(appModules)
        }
    }
}