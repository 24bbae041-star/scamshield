package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.TrustedContact
import kotlinx.coroutines.flow.Flow

@Dao
interface TrustedContactDao {
    @Query("SELECT * FROM trusted_contacts ORDER BY isPrimary DESC, id ASC")
    fun getAllContacts(): Flow<List<TrustedContact>>

    @Query("SELECT * FROM trusted_contacts WHERE isPrimary = 1 LIMIT 1")
    suspend fun getPrimaryContact(): TrustedContact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: TrustedContact): Long

    @Update
    suspend fun updateContact(contact: TrustedContact)

    @Delete
    suspend fun deleteContact(contact: TrustedContact)

    @Query("UPDATE trusted_contacts SET isPrimary = 0")
    suspend fun resetPrimaryFlags()

    @Query("UPDATE trusted_contacts SET isPrimary = 1 WHERE id = :id")
    suspend fun setPrimaryContact(id: Long)

    @Query("DELETE FROM trusted_contacts")
    suspend fun clearAllContacts()
}
