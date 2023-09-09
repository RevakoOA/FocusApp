package com.ostapr.focusapp.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ostapr.focusapp.core.network.FocusAppNetworkDataSource
import com.ostapr.focusapp.core.network.model.DelayInfo
import com.ostapr.focusapp.core.model.data.DelayInfo as CoreDelayInfo
import com.ostapr.focusapp.core.network.retrofit.RetrofitFocusAppNetworkApi.Companion.FOCUS_APP_BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitFocusAppNetworkApi {

    /** Response is parsed in [DelayInfo]. */
    @GET("api/config")
    suspend fun getDelay(): ResponseBody

    companion object {
        // TODO(ostapr) consider to move it to secret config file.
        const val FOCUS_APP_BASE_URL = "https://64dcc19ce64a8525a0f71825.mockapi.io/"
    }
}


/**
 * [Retrofit] backed implementation of [FocusAppNetworkDataSource].
 */
@Singleton
class RetrofitFocusAppNetwork @Inject constructor(
    networkJson: Json,
) : FocusAppNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(FOCUS_APP_BASE_URL)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RetrofitFocusAppNetworkApi::class.java)


    override suspend fun getUpdateDelay(): CoreDelayInfo =
        DelayInfo.parseFromResponse(networkApi.getDelay()).toCoreModel()
}
