package com.imudev.newskat.room

import androidx.room.TypeConverter
import com.imudev.newskat.model.headline.Source


class Converters {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return source.name;
    }

    @TypeConverter
    fun stringToSource(name : String) : Source {
        return Source(name, name)
    }
}