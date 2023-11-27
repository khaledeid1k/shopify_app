package com.kh.mo.shopyapp.model.ui

data class CurrencySettingModel(
    val title: String,
    val key: SupportedCurrencies,
    var isThePreference: Boolean = false
)

data class LanguageSettingModel(
    val title: String,
    val key: SupportedLanguages,
    var isThePreference: Boolean = false
)