package com.kh.mo.shopyapp.local.dp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
@Dao
interface ShopyDao {

    @Query("SELECT * FROM line_items_table")
    suspend fun getAllLinetItems(): List<LineItemEntity>

    @Query("DELETE FROM line_items_table WHERE product_id = :productId")
    suspend fun deleteLinetItems(productId:Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLinetItems(lineItemEntity:LineItemEntity)


    @Query("SELECT * FROM favorite_table")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Query("DELETE FROM favorite_table WHERE id = :productId")
    suspend fun deleteFavorite(productId:Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favoriteEntity:FavoriteEntity)


    @Query("SELECT COALESCE(COUNT(*), 0) FROM favorite_table WHERE id = :productId")
    suspend fun checkProductInFavorite(productId: Long): Int


}