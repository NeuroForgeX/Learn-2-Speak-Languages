package com.forge.learn

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

private lateinit var translator: Translator

fun translateGermanToEnglish(wordInGerman: String, onResult: (String) -> Unit) {
    translator.downloadModelIfNeeded().onSuccessTask { _ ->
        translator.translate(wordInGerman)
    }.addOnSuccessListener { result ->
        onResult.invoke(result) // Success!
    }.addOnFailureListener { exception ->
        onResult.invoke("") // Failed (Network, storage, or translation error)
    }
}

fun initTranslator() {
    if (!::translator.isInitialized) {
        val options = TranslatorOptions.Builder() //
                .setSourceLanguage(TranslateLanguage.GERMAN) //
                .setTargetLanguage(TranslateLanguage.ENGLISH) //
                .build()
        translator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder().requireWifi()         // Restricted to Wi-Fi
                .build()
        translator.downloadModelIfNeeded(conditions);
    }
}
