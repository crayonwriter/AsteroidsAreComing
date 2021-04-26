package com.crayonwriter.asteroidsarecoming.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase.Companion.getDatabase
import com.crayonwriter.asteroidsarecoming.network.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        //get a database and a repository
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        repository.refreshAsteroids()

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
