package ru.practicum.android.diploma.data.dto

enum class CurrencyIds(val code: String, val symbol: String) {
    RUR("RUR", "\u20BD"), // Символ рубля
    RUB("RUB", "\u20BD"), // Символ рубля
    BYR("BYR", "Br"),
    USD("USD", "$"),
    KZT("KZT", "₸"),
    UAH("UAH", "₴"),
    AZN("AZN", "₼"),
    UZS("UZS", "лв"),
    GEL("GEL", "₾"),
    KGS("KGS", "лв"),
    EUR("EUR", "€")
}
