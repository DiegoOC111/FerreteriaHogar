package com.example.ferreteriahogar.utils



import okhttp3.ResponseBody
import retrofit2.http.*
import com.example.ferreteriahogar.data.*

interface ApiService {

    // ---------- AUTH ----------
    @POST("auth/login")
    suspend fun login(@Body req: LoginRequest): LoginResponse

    @GET("auth/status")
    suspend fun authStatus(): String

    @POST("auth/bootstrap-admin")
    suspend fun bootstrapAdmin(): ResponseBody


    // ---------- USERS ----------
    @GET("users/me")
    suspend fun getProfile(): User

    @GET("users/all")
    suspend fun getAllUsers(): List<UserAD>

    @POST("users/create-user")
    suspend fun createUser(@Body req: RegisterRequest): ResponseBody

    @PUT("users/{username}")
    suspend fun updateUser(
        @Path("username") username: String,
        @Body req: UpdateUserRequest
    ): User

    @DELETE("users/{username}")
    suspend fun deleteUser(@Path("username") username: String): ResponseBody


    // ---------- INVENTORY ----------
    @GET("inventory")
    suspend fun getInventories(): List<Inventory>

    @GET("inventory/{code}")
    suspend fun getInventory(@Path("code") code: String): Inventory

    @GET("inventory/{code}/full")
    suspend fun getFullInventory(@Path("code") code: String): InventoryFull

    @POST("inventory")
    suspend fun createInventory(@Body inv: InventoryAD): Inventory

    @PUT("inventory")
    suspend fun updateInventory(@Body inv: InventoryAD): Inventory

    @DELETE("inventory/{code}")
    suspend fun deleteInventory(@Path("code") code: String): ResponseBody


    // ---------- PRODUCTS ----------
    @GET("products")
    suspend fun getProducts(): List<ProductAD>

    @GET("products/{code}")
    suspend fun getProduct(@Path("code") code: String): ProductAD

    @POST("products")
    suspend fun createProduct(@Body p: ProductAD): Product

    @PUT("products")
    suspend fun updateProduct(@Body p: ProductAD): ProductAD

    @DELETE("products/{code}")
    suspend fun deleteProduct(@Path("code") code: String): ResponseBody


    // ---------- INVENTORY PRODUCT ----------
    @GET("inventory-product/inventory/{inventoryCode}")
    suspend fun getProductsByInventory(@Path("inventoryCode") inventoryCode: String): List<InventoryProduct>

    @GET("inventory-product/{inventoryCode}/{productCode}")
    suspend fun getInventoryProduct(
        @Path("inventoryCode") inventoryCode: String,
        @Path("productCode") productCode: String
    ): InventoryProduct

    @POST("inventory-product")
    suspend fun createInventoryProduct(@Body ip: InventoryProduct): InventoryProduct

    @POST("inventory-product/{inventoryCode}/scan/{productCode}/{qty}")
    suspend fun addInventoryProductByScan(
        @Path("inventoryCode") inventoryCode: String,
        @Path("productCode") productCode: String,
        @Path("qty") qty: Int
    ): InventoryProduct

    @PUT("inventory-product")
    suspend fun updateInventoryProduct(@Body ip: InventoryProduct): InventoryProduct

    @DELETE("inventory-product/{inventoryCode}/{productCode}")
    suspend fun deleteInventoryProduct(
        @Path("inventoryCode") inventoryCode: String,
        @Path("productCode") productCode: String
    ): ResponseBody
}