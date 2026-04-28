package com.example.spendwise.repositories;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0011J\u0006\u0010\u0017\u001a\u00020\u0015J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002JH\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u001c2\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\b\b\u0002\u0010!\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0004\b\"\u0010#J&\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110\u001c2\u0006\u0010 \u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0004\b%\u0010&J\u0006\u0010\'\u001a\u00020\u0015J\u000e\u0010(\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u0010*J\"\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020)2\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u0019H\u0086@\u00a2\u0006\u0002\u0010.J\u000e\u0010/\u001a\u000200H\u0086@\u00a2\u0006\u0002\u0010*J\u0016\u00101\u001a\u00020\u00152\u0006\u00102\u001a\u000200H\u0086@\u00a2\u0006\u0002\u00103J\u0016\u00104\u001a\u00020\u00152\u0006\u00105\u001a\u000206H\u0086@\u00a2\u0006\u0002\u00107J\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020609H\u0086@\u00a2\u0006\u0002\u0010*J\u0016\u0010:\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010<J\u0016\u0010=\u001a\u00020\u00152\u0006\u0010>\u001a\u00020?H\u0086@\u00a2\u0006\u0002\u0010@J\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020?09H\u0086@\u00a2\u0006\u0002\u0010*J\u0016\u0010B\u001a\u00020\u00152\u0006\u0010C\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010<R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006D"}, d2 = {"Lcom/example/spendwise/repositories/AppRepository;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "db", "Lcom/example/spendwise/db/SpendWiseDatabase;", "userDao", "Lcom/example/spendwise/db/UserDao;", "expenseDao", "Lcom/example/spendwise/db/ExpenseDao;", "incomeDao", "Lcom/example/spendwise/db/IncomeDao;", "prefs", "Landroid/content/SharedPreferences;", "getCurrentUserId", "", "isLoggedIn", "", "saveSession", "", "userId", "clearSession", "hashPassword", "", "password", "register", "Lkotlin/Result;", "firstName", "lastName", "gender", "email", "profileImageUrl", "register-bMdYcbs", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "getUserProfile", "Lcom/example/spendwise/UserProfile;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfile", "profile", "newPassword", "(Lcom/example/spendwise/UserProfile;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBudget", "", "setBudget", "amount", "(DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addExpense", "expense", "Lcom/example/spendwise/Expense;", "(Lcom/example/spendwise/Expense;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExpenses", "", "deleteExpense", "expenseId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addIncome", "income", "Lcom/example/spendwise/Income;", "(Lcom/example/spendwise/Income;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getIncomes", "deleteIncome", "incomeId", "app_debug"})
public final class AppRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendwise.db.SpendWiseDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendwise.db.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendwise.db.ExpenseDao expenseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendwise.db.IncomeDao incomeDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    
    public AppRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final int getCurrentUserId() {
        return 0;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void saveSession(int userId) {
    }
    
    public final void clearSession() {
    }
    
    private final java.lang.String hashPassword(java.lang.String password) {
        return null;
    }
    
    public final void logout() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.spendwise.UserProfile> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull()
    com.example.spendwise.UserProfile profile, @org.jetbrains.annotations.Nullable()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getBudget(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setBudget(double amount, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addExpense(@org.jetbrains.annotations.NotNull()
    com.example.spendwise.Expense expense, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getExpenses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.spendwise.Expense>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteExpense(@org.jetbrains.annotations.NotNull()
    java.lang.String expenseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addIncome(@org.jetbrains.annotations.NotNull()
    com.example.spendwise.Income income, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getIncomes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.spendwise.Income>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteIncome(@org.jetbrains.annotations.NotNull()
    java.lang.String incomeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}