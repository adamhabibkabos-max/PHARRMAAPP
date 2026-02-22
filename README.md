# PharmaConnect (Native Android, Kotlin)

PharmaConnect is a production-style Android app for pharmacy inventory and order management.

## Tech stack
- Kotlin
- MVVM architecture
- Room Database
- LiveData + Flow
- RecyclerView
- Material Design components
- Navigation Component
- ViewBinding

## Folder structure
```
app/src/main/java/com/pharmaconnect/
  data/local/        # Room DAOs + database
  model/             # Entities (Medicine, Order)
  repository/        # Repository abstraction over DAOs
  ui/
    auth/            # Login
    dashboard/       # Summary dashboard
    inventory/       # Medicines list + search/filter
    medicine/        # Add/Edit medicine form
    orders/          # Orders list + date filtering
    reports/         # Reporting by date range
    scanner/         # Barcode scanner activity
  viewmodel/         # ViewModels + factory
  utils/             # Date utilities
```

## Architecture overview
- **UI (Fragments/Activity)** handles rendering and user actions.
- **ViewModel** manages UI state and business logic.
- **Repository** coordinates app data and hides DAO details.
- **Room (DAO + Entities + Database)** persists medicines and orders locally.
- **Navigation Graph** controls screen transitions and argument passing.

## Features implemented
1. Login (demo local auth: `admin/admin123`) and navigate to dashboard.
2. Dashboard summary cards for total medicines, low stock, expiring soon, and total orders.
3. Inventory list with RecyclerView, item click to edit, FAB to add medicine.
4. Add/Edit medicine form with validation and Room save.
5. Barcode scanner via ZXing with CAMERA runtime permission.
6. Search and filters (expired, low stock, category).
7. Orders listing and date-range filtering.
8. Reports with date-range selector, completed/failed counts, and revenue.
9. Room entities (Medicine, Order), DAOs, database, and repository layer.

## Build and run (Android Studio Arctic Fox+)
1. Open project in Android Studio.
2. Let Gradle sync complete.
3. Run `app` on a device/emulator (API 24+).

## Release APK build instructions
### Option A: Android Studio UI
1. **Build > Generate Signed Bundle / APK**.
2. Select **APK**.
3. Provide keystore path + passwords.
4. Choose `release` build type.
5. Build and collect APK from `app/release/`.

### Option B: CLI
```bash
./gradlew assembleRelease
```
Generated APK:
`app/build/outputs/apk/release/app-release.apk`

## Signing config example
`app/build.gradle` includes an example `signingConfigs.release` that reads environment variables:
- `PHARMA_RELEASE_STORE_FILE`
- `PHARMA_RELEASE_STORE_PASSWORD`
- `PHARMA_RELEASE_KEY_ALIAS`
- `PHARMA_RELEASE_KEY_PASSWORD`

Set these in your shell or CI secrets before release builds.
