package com.example.spendwise

import java.text.SimpleDateFormat
import java.util.*

data class UserProfile(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val email: String = "",
    val profileImageUrl: String = ""
) {
    fun fullName() = "$firstName $lastName".trim()
    fun initials(): String {
        val f = firstName.firstOrNull()?.uppercaseChar() ?: ' '
        val l = lastName.firstOrNull()?.uppercaseChar() ?: ' '
        return "$f$l".trim().ifBlank { email.firstOrNull()?.uppercaseChar()?.toString() ?: "?" }
    }
}

data class Expense(
    val id: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false,
    val photoUrl: String = ""
) {
    fun getFormattedDate(): String =
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(date))

    fun getCategoryIcon(): String = when (category.lowercase()) {
        "food"          -> "🍔"
        "transport"     -> "🚗"
        "shopping"      -> "🛍️"
        "health"        -> "💊"
        "entertainment" -> "🎬"
        "utilities"     -> "💡"
        "rent"          -> "🏠"
        "education"     -> "📚"
        else            -> "💳"
    }
}

data class Income(
    val id: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false
) {
    fun getFormattedDate(): String =
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(date))

    fun getCategoryIcon(): String = when (category.lowercase()) {
        "salary"     -> "💼"
        "freelance"  -> "💻"
        "investment" -> "📈"
        "gift"       -> "🎁"
        "rental"     -> "🏠"
        else         -> "💰"
    }
}

data class TransactionItem(
    val id: String,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Long,
    val isExpense: Boolean,
    val icon: String,
    val photoUrl: String = ""
)

// ── Gamification ──────────────────────────────────────────────
data class UserLevel(
    val level: Int,
    val title: String,
    val xp: Int,
    val xpForNext: Int,
    val badge: String,
    val unlockCondition: String
)

object XPSystem {
    val levels = listOf(
        UserLevel(1, "Budget Beginner",  0,   100, "🌱", "Log first 5 expenses"),
        UserLevel(2, "Smart Spender",    100, 250, "💡", "Stay within category limits for one week"),
        UserLevel(3, "Savings Sidekick", 250, 500, "🎯", "Reach 25% of monthly savings goal"),
        UserLevel(4, "Money Maestro",    500, 800, "🏆", "Avoid overspending for one month"),
        UserLevel(5, "Budget Ninja",     800, 800, "🥷", "Achieve all goals for three consecutive months")
    )

    fun calculateXP(expenseCount: Int, withinBudget: Boolean, savingsGoalPct: Double): Int {
        var xp = 0
        xp += minOf(expenseCount * 10, 100)
        if (withinBudget) xp += 75
        if (savingsGoalPct >= 0.25) xp += 50
        if (savingsGoalPct >= 1.0)  xp += 100
        return xp
    }

    fun getLevelForXP(xp: Int): UserLevel = levels.lastOrNull { xp >= it.xp } ?: levels.first()

    fun getProgressPercent(xp: Int): Int {
        val current = getLevelForXP(xp)
        if (current.level == 5) return 100
        val next = levels.getOrNull(current.level) ?: return 100
        val range = next.xp - current.xp
        val earned = xp - current.xp
        return ((earned.toFloat() / range) * 100).toInt().coerceIn(0, 100)
    }
}
