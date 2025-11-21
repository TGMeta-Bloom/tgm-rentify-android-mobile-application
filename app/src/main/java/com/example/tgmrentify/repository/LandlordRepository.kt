package com.example.tgmrentify.repository
import com.example.tgmrentify.model.Property
import kotlinx.coroutines.delay

/**
 * Placeholder Repository for Landlord Module.
 * In the final version, this class will handle Firebase Firestore/Storage calls.
 */
class LandlordRepository {

    /**
     * Simulates fetching properties belonging ONLY to the logged-in landlord (data isolation).
     */
    suspend fun getLandlordProperties(landlordId: String): List<Property> {
        // Simulate network/database latency
        delay(1000)

        // Return static mock data for the frontend demo
        return listOf(
            Property(
                propertyId = "P001",
                landlordId = landlordId,
                title = "Modern City Apartment",
                location = "Colombo 03",
                rentAmount = 120000.0,
                imageUrls = listOf("https://picsum.photos/id/1/200/300")
            ),
            Property(
                propertyId = "P002",
                landlordId = landlordId,
                title = "Suburban Family House",
                location = "Kaduwela",
                rentAmount = 65000.0,
                imageUrls = listOf("https://picsum.photos/id/10/200/300")
            ),
            Property(
                propertyId = "P003",
                landlordId = landlordId,
                title = "Studio Unit near University",
                location = "Nugegoda",
                rentAmount = 35000.0,
                imageUrls = listOf("https://picsum.photos/id/20/200/300")
            ),
                    Property(
                        propertyId = "P004",
                        landlordId = landlordId,
                        title = "Studio Unit near University",
                        location = "Matara",
                        rentAmount = 35000.0,
                        imageUrls = listOf("https://picsum.photos/id/20/200/300")
                    ),
            Property(
                propertyId = "P004",
                landlordId = landlordId,
                title = "Studio Unit near University",
                location = "Akuressa",
                rentAmount = 35000.0,
                imageUrls = listOf("https://picsum.photos/id/20/200/300")
            )
        )
    }
}