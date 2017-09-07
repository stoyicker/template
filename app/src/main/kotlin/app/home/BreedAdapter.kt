package app.home

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import jorge.template.blank.R

internal class BreedAdapter : RecyclerView.Adapter<BreedAdapter.ViewHolder>() {
    private var items = emptyList<PresentationBreed>()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long = items[position].breed.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int) = parent?.let {
            ViewHolder(LayoutInflater.from(it.context).inflate(R.layout.item_breed, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        viewHolder?.renderFull(items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        if (payloads?.isEmpty() != false) {
            onBindViewHolder(holder, position)
        }
        val payload = Bundle()
        payloads?.forEach {
            when (it) {
                KEY_BREED -> payload.putString(KEY_BREED, it as String)
                KEY_PICTURE_URL -> payload.putString(KEY_PICTURE_URL, it as String)
                else -> throw IllegalStateException("Unexpected payload key $it")
            }
        }
        holder?.renderPartial(payload)
    }

    fun add(newItems: Iterable<PresentationBreed>) {
        if (!newItems.none()) {
            val additionStart = items.size
            items += newItems
            notifyItemRangeInserted(additionStart, newItems.count())
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val breedView = itemView.findViewById<TextView>(R.id.breed)
        private val pictureView = itemView.findViewById<ImageView>(R.id.picture)

        fun renderFull(item: PresentationBreed) {
            updateBreed(item.breed)
            updatePicture(item.pictureUrl)
        }

        fun renderPartial(payload: Bundle) {
            payload.getString(KEY_BREED)?.let { updateBreed(it) }
            payload.getString(KEY_PICTURE_URL)?.let { updatePicture(it) }
        }

        private fun updateBreed(newName: String) {
            breedView.text = newName
            pictureView.contentDescription = newName
        }

        private fun updatePicture(newPictureUrl: String) {
            Picasso.with(pictureView.context).load(newPictureUrl).into(pictureView)
        }
    }
}

private const val KEY_BREED = "jorge.template.blank.KEY_BREED"
private const val KEY_PICTURE_URL = "jorge.template.blank.KEY_PICTURE_URL"
