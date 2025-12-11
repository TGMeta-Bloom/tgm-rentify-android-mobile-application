package com.example.tgmrentify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Property(
    val propertyId: String,


    val landlordId: String,
    val title: String,
    val description: String = "A beautiful place to live with modern amenities.", // Default description
    val location: String,
    val rentAmount: Double,
    val propertyType: String = "Apartment", // Default type

    val status: String = "Available", // Default status
    val contactNumber: String = "077-1234567", // Default contact
    val imageUrls: List<String>
) : Parcelable

