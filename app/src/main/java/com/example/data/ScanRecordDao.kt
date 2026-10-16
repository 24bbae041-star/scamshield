package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.ScanRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanRecordDao {
    @Query("SELECT * FROM scan_records ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanRecord>>

    @Query("SELECT * FROM scan_records WHERE id = :id LIMIT 1")
    suspend fun getScanById(id: Long): ScanRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(record: ScanRecord): Long

    @Update
    suspend fun updateScan(record: ScanRecord)

    @Delete
    suspend fun deleteScan(record: ScanRecord)

    @Query("DELETE FROM scan_records WHERE id = :id")
    suspend fun deleteScanById(id: Long)

    @Query("DELETE FROM scan_records")
    suspend fun clearAllScans()
}
