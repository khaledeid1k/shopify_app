package com.kh.mo.shopyapp.local.dp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopyDao {

    @Query("SELECT * FROM favorite_table WHERE customerId = :customerId")
    fun getAllFavorites(customerId: Long): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorite_table WHERE productId = :productId")
    suspend fun deleteFavorite(productId:Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favoriteEntity:FavoriteEntity):Long


    @Query("SELECT COALESCE(COUNT(*), 0) FROM favorite_table WHERE productId = :productId")
    suspend fun checkProductInFavorite(productId: Long): Int


}