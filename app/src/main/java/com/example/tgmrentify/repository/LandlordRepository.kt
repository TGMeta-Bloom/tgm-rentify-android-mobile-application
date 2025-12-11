package com.example.tgmrentify.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tgmrentify.model.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class LandlordRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val propertiesCollection = firestore.collection("properties")

    // Get current landlord's ID safely
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    /**
     * Fetch properties for the currently logged-in landlord
     */
    fun getLandlordProperties(): LiveData<List<Property>> {
        val propertiesLiveData = MutableLiveData<List<Property>>()
        val landlordId = currentUserId ?: return propertiesLiveData

        propertiesCollection
            .whereEqualTo("landlordId", landlordId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("LandlordRepo", "Listen failed.", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val propertyList = snapshot.documents.mapNotNull { doc ->
                        // Manually map fields to Property because direct .toObject() needs no-arg constructor
                        // which data classes might not have without default values or plugins
                        try {
                            Property(
                                propertyId = doc.getString("propertyId") ?: doc.id,
                                landlordId = doc.getString("landlordId") ?: "",
                                title = doc.getString("title") ?: "",
                                description = doc.getString("description") ?: "",
                                location = doc.getString("location") ?: "",
                                rentAmount = doc.getDouble("rentAmount") ?: 0.0,
                                propertyType = doc.getString("propertyType") ?: "Apartment",
                                status = doc.getString("status") ?: "Available",
                                contactNumber = doc.getString("contactNumber") ?: "",
                                imageUrls = (doc.get("imageUrls") as? List<String>) ?: emptyList()
                            )
                        } catch (e: Exception) {
                            Log.e("LandlordRepo", "Error mapping document: ${doc.id}", e)
                            null
                        }
                    }
                    propertiesLiveData.value = propertyList
                }
            }
        return propertiesLiveData
    }

    /**
     * Add a new property to Firestore
     */
    suspend fun addProperty(property: Property) {
        // Ensure propertyId is consistent if not provided
        val finalProperty = if (property.propertyId.isEmpty()) {
            property.copy(propertyId = UUID.randomUUID().toString())
        } else {
            property
        }
        
        propertiesCollection.document(finalProperty.propertyId).set(finalProperty).await()
    }

    /**
     * Update an existing property in Firestore
     */
    suspend fun updateProperty(property: Property) {
        propertiesCollection.document(property.propertyId).set(property).await()
    }

    /**
     * Delete a property and its associated images
     */
    suspend fun deleteProperty(propertyId: String) {
        val landlordId = currentUserId ?: throw Exception("User not logged in")

        // 1. Delete Firestore Document
        propertiesCollection.document(propertyId).delete().await()

        // 2. Delete Images from Storage (Best Effort)
        // Note: Firebase Storage doesn't support deleting a folder directly.
        // We typically need to list files and delete them.
        // However, since we might not know all filenames without a list call (which is async),
        // we can try to delete known paths or skip this step if handled by Cloud Functions.
        // For this specific requirement "include logic to delete images":
        
        try {
            val storageRef = storage.reference.child("property_images/$landlordId/$propertyId")
            val listResult = storageRef.listAll().await()
            for (item in listResult.items) {
                item.delete().await()
            }
        } catch (e: Exception) {
            Log.e("LandlordRepo", "Error deleting images for property: $propertyId", e)
            // We don't block the property deletion if image deletion fails (e.g., folder empty)
        }
    }

    /**
     * Upload an image to Firebase Storage and return the download URL
     */
    suspend fun uploadPropertyImage(uri: Uri, propertyId: String): String {
        val landlordId = currentUserId ?: throw Exception("User not logged in")
        val uniqueFilename = UUID.randomUUID().toString() + ".jpg"
        
        // Path: property_images/{landlordId}/{propertyId}/{unique_filename}.jpg
        val ref = storage.reference.child("property_images/$landlordId/$propertyId/$uniqueFilename")
        
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}