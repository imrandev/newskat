package com.imudev.newskat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.room.dao.ArticleDao

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsKatDb : RoomDatabase() {

    abstract fun getArticleDao() : ArticleDao

    companion object {
        @Volatile
        private var instance: NewsKatDb? = null
        private var lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsKatDb::class.java,
                "newskat_db"
            ).build()

    }
}