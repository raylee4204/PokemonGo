package city.samaritan.pokemongo

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun LocalDateTime.toIsoFormat() =
    DateTimeFormatter.ISO_INSTANT.format(this.atZone(ZoneId.systemDefault()))

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.parse(this), ZoneId.systemDefault())
}

fun LatLng.toDbFormat(): String {
    return "$latitude:$longitude"
}

fun String.parseLatLng(): LatLng {
    val splitCoords = split(":")
    return LatLng(splitCoords[0].toDouble(), splitCoords[1].toDouble())
}

fun ImageView.loadImage(url: String, size: Int) {
    Glide.with(this).clear(this)
    Glide.with(this)
        .load(url)
        .override(size)
        .into(this)
}

fun Int.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        context.resources.displayMetrics
    ).toInt()
}