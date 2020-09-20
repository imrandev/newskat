package com.imudev.newskat.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.imudev.newskat.model.headline.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(article: Article) : Long

    @Query("select * from article")
    fun findArticles() : LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}