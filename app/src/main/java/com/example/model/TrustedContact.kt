package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trusted_contacts")
data class TrustedContact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val relationship: String, // Son, Daughter, Grandchild, Spouse, Friend, Neighbor
    val phoneNumber: String,
    val isPrimary: Boolean = false,
    val avatarEmoji: String = "💜",
    val notes: String = ""
)
