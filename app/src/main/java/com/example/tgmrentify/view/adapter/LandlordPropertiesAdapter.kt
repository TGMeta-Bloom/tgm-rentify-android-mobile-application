package com.example.tgmrentify.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.R
import com.example.tgmrentify.model.Property

class LandlordPropertiesAdapter(
    private val properties: List<Property>,
    private val onEditClick: (Property) -> Unit,
    private val onDeleteClick: (Property) -> Unit
) : RecyclerView.Adapter<LandlordPropertiesAdapter.PropertyViewHolder>() {

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPropertyImage: ImageView = itemView.findViewById(R.id.iv_property_image)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_property_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_property_description)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_property_location)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_property_price)
        val tvType: TextView = itemView.findViewById(R.id.tv_property_type)
        val tvContact: TextView = itemView.findViewById(R.id.tv_contact_number)
        val btnEdit: View = itemView.findViewById(R.id.btn_edit)
        val btnDelete: View = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_landlord_my_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        
        holder.tvTitle.text = property.title
        holder.tvDescription.text = property.description
        holder.tvLocation.text = property.location
        holder.tvPrice.text = "Rs. ${property.rentAmount.toInt()}"
        holder.tvType.text = property.propertyType
        holder.tvContact.text = property.contactNumber
        
        // Load image (Placeholder logic, ideally use Glide)
        // holder.ivPropertyImage.setImageResource(R.drawable.ic_property_image2) 
        // Since we have static resources for now, let's just leave the default from XML or cycle them if needed.
        // For simplicity in this step, we rely on the XML default.
        
        holder.btnEdit.setOnClickListener { onEditClick(property) }
        holder.btnDelete.setOnClickListener { onDeleteClick(property) }
    }

    override fun getItemCount(): Int = properties.size
}