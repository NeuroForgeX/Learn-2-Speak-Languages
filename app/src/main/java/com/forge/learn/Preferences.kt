package com.forge.learn

import android.content.Context
import androidx.compose.runtime.mutableStateSetOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime
import java.time.LocalDateTime.now

val valuesErrored = HashMap<Int, List<LocalDateTime>>()
val valuesMarkedForFuture = mutableStateSetOf<Int>()

private val gson = Gson()

fun markValueForFuture(id: Int) {
    valuesMarkedForFuture.add(id)
}

fun removeMarkedValueForFuture(id: Int) {
    valuesMarkedForFuture.remove(id)
}

fun isValueMarkedForFuture(id: Int): Boolean {
    return valuesMarkedForFuture.contains(id)
}

fun getReviewQuestions(): Set<Int> {
    return valuesMarkedForFuture + valuesErrored.keys
}

fun markValueErrored(id: Int) {
    val existingList = valuesErrored.computeIfAbsent(id) { ArrayList() }
    valuesErrored[id] = existingList + now()
}

fun loadPreferences(context: Context) {
    val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Load errored values
    val erroredJson = prefs.getString("values_errored", null)
    if (erroredJson != null) {
        val type = object : TypeToken<HashMap<Int, List<LocalDateTime>>>() {}.type
        valuesErrored.clear()
        valuesErrored.putAll(gson.fromJson(erroredJson, type))
    }

    // Load marked for future values
    val futureSet = prefs.getStringSet("values_marked_future", emptySet())
    valuesMarkedForFuture.clear()
    valuesMarkedForFuture.addAll(futureSet?.map { it.toInt() } ?: emptySet())
}

fun savePreferences(context: Context) {
    val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    // Save errored values
    val erroredJson = gson.toJson(valuesErrored)
    editor.putString("values_errored", erroredJson)

    // Save marked for future values
    val futureSet = valuesMarkedForFuture.map { it.toString() }.toSet()
    editor.putStringSet("values_marked_future", futureSet)

    editor.apply()
}
