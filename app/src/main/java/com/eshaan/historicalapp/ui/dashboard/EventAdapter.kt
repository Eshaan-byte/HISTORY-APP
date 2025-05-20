package com.eshaan.historicalapp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eshaan.historicalapp.data.model.HistoricalEvent
import com.eshaan.historicalapp.databinding.ItemHistoricalEventBinding

class EventAdapter(private val onItemClick: (HistoricalEvent) -> Unit) :
    ListAdapter<HistoricalEvent, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemHistoricalEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventViewHolder(
        private val binding: ItemHistoricalEventBinding,
        private val onItemClick: (HistoricalEvent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: HistoricalEvent) {
            binding.apply {
                tvEventName.text = event.event
                tvYearRange.text = "${event.startYear} - ${event.endYear}"
                tvLocation.text = event.location
                tvKeyFigure.text = "Key Figure: ${event.keyFigure}"

                root.setOnClickListener {
                    onItemClick(event)
                }
            }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<HistoricalEvent>() {
        override fun areItemsTheSame(oldItem: HistoricalEvent, newItem: HistoricalEvent): Boolean {
            return oldItem.event == newItem.event
        }

        override fun areContentsTheSame(oldItem: HistoricalEvent, newItem: HistoricalEvent): Boolean {
            return oldItem == newItem
        }
    }
}