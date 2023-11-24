package com.kh.mo.shopyapp.local.dp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kh.mo.shopyapp.model.entity.LineItemEntity
@Dao
interface ShopyDao {

    @Query("SELECT * FROM Line_Items")
    suspend fun getAllLinetItems(): List<LineItemEntity>

    @Query("DELETE FROM Line_Items WHERE product_id = :productId")
    suspend fun deleteLinetItems(productId:Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLinetItems(lineItemEntity:LineItemEntity)



}