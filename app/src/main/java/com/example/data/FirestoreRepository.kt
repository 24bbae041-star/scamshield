package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.example.model.ScanRecord
import com.example.model.TrustedContact
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val firestore: FirebaseFirestore
) {
    companion object {
        private const val TAG = "FirestoreRepo"

        fun create(context: Context): FirestoreRepository {
            val dbId = context.applicationContext.getString(R.string.firestore_database_id)
            val db = FirebaseFirestore.getInstance(dbId)
            return FirestoreRepository(db)
        }
    }

    private val auth = Firebase.auth

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun saveScanToCloud(scan: ScanRecord) {
        val uid = auth.currentUser?.uid ?: return
        try {
            val scanMap = hashMapOf<String, Any>(
                "userId" to uid,
                "type" to scan.type,
                "title" to scan.title,
                "snippet" to scan.snippet,
                "riskLevel" to scan.riskLevel,
                "evidenceListJson" to scan.evidenceListJson,
                "explanation" to scan.explanation,
                "nextSteps" to scan.nextSteps,
                "independentVerification" to scan.independentVerification,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("users")
                .document(uid)
                .collection("scans")
                .document(scan.id.toString())
                .set(scanMap)
                .await()
            Log.d(TAG, "Scan record ${scan.id} saved to Firestore cloud")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save scan record to Firestore", e)
        }
    }

    suspend fun saveContactToCloud(contact: TrustedContact) {
        val uid = auth.currentUser?.uid ?: return
        try {
            val contactMap = hashMapOf<String, Any>(
                "userId" to uid,
                "name" to contact.name,
                "relationship" to contact.relationship,
                "phoneNumber" to contact.phoneNumber,
                "isPrimary" to contact.isPrimary,
                "avatarEmoji" to contact.avatarEmoji,
                "notes" to contact.notes,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("users")
                .document(uid)
                .collection("trustedContacts")
                .document(contact.id.toString())
                .set(contactMap)
                .await()
            Log.d(TAG, "Trusted contact ${contact.name} saved to Firestore cloud")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save contact to Firestore", e)
        }
    }

    suspend fun deleteContactFromCloud(contactId: Long) {
        val uid = auth.currentUser?.uid ?: return
        try {
            firestore.collection("users")
                .document(uid)
                .collection("trustedContacts")
                .document(contactId.toString())
                .delete()
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete contact from Firestore", e)
        }
    }
}
