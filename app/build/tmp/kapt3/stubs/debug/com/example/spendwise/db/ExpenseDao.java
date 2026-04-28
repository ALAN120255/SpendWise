package com.example.spendwise.db;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u0012\u00c0\u0006\u0001"}, d2 = {"Lcom/example/spendwise/db/ExpenseDao;", "", "getAll", "", "Lcom/example/spendwise/db/ExpenseEntity;", "userId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "expense", "(Lcom/example/spendwise/db/ExpenseEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "", "id", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotal", "", "app_debug"})
@androidx.room.Dao()
public abstract interface ExpenseDao {
    
    @androidx.room.Query(value = "SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(int userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.spendwise.db.ExpenseEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.spendwise.db.ExpenseEntity expense, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM expenses WHERE id = :id AND userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(int id, int userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM expenses WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotal(int userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
}