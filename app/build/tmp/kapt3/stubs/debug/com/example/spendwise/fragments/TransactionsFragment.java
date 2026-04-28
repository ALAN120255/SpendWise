package com.example.spendwise.fragments;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001b\u001a\u00020\u001cH\u0002J&\u0010\u001d\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u001a\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0018\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0012H\u0002J\u0018\u0010*\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0012H\u0002J\u0010\u0010+\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020\u0012H\u0002J\b\u0010-\u001a\u00020\u001cH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00120\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\f0\f0\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/spendwise/fragments/TransactionsFragment;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "repo", "Lcom/example/spendwise/repositories/AppRepository;", "adapter", "Lcom/example/spendwise/TransactionAdapter;", "allItems", "", "Lcom/example/spendwise/TransactionItem;", "selectedPhotoUri", "Landroid/net/Uri;", "selectedDateMillis", "", "cameraUri", "pickPhoto", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "takePhoto", "currentPhotoLabel", "Landroid/widget/TextView;", "currentPhotoPreview", "Landroid/widget/ImageView;", "currentClearBtn", "Landroid/view/View;", "updatePhotoUI", "", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "loadTransactions", "chipGroup", "Lcom/google/android/material/chip/ChipGroup;", "query", "filterList", "showAddDialog", "sym", "onResume", "app_debug"})
public final class TransactionsFragment extends androidx.fragment.app.Fragment {
    private com.example.spendwise.repositories.AppRepository repo;
    private com.example.spendwise.TransactionAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.spendwise.TransactionItem> allItems;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri selectedPhotoUri;
    private long selectedDateMillis;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri cameraUri;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> pickPhoto = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.net.Uri> takePhoto = null;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView currentPhotoLabel;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView currentPhotoPreview;
    @org.jetbrains.annotations.Nullable()
    private android.view.View currentClearBtn;
    
    public TransactionsFragment() {
        super();
    }
    
    private final void updatePhotoUI() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadTransactions(com.google.android.material.chip.ChipGroup chipGroup, java.lang.String query) {
    }
    
    private final void filterList(com.google.android.material.chip.ChipGroup chipGroup, java.lang.String query) {
    }
    
    private final void showAddDialog(java.lang.String sym) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
}