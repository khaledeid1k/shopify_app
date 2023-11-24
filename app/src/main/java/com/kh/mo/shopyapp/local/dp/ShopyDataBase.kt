package com.kh.mo.shopyapp.local.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.utils.Constants.DATA_BASE

@Database(entities = [LineItemEntity::class], version = 1)
abstract class ShopyDataBase : RoomDatabase(){
    abstract fun shopyDao(): ShopyDao
    companion object{
        @Volatile
        private  var instance: ShopyDataBase?=null

        fun getShopyDataBaseDataBaseInstance(context: Context): ShopyDataBase {
            return instance ?: synchronized(this){
                val instanceHolder=  Room.databaseBuilder(context.applicationContext,
                    ShopyDataBase::class.java,DATA_BASE).build()
                instance =instanceHolder
                instanceHolder

            }
        }
    }
}