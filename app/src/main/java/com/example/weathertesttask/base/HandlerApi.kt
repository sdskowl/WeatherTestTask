package com.example.weathertesttask.base

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface ApiResult<T : Any, E : Any>
class ApiSuccess<T : Any, E : Any>(val data: T) : ApiResult<T, E>
class ApiError<T : Any, E : Any>(val data: E, val statusCode: Int) : ApiResult<T, E>
class ApiException<T : Any, E : Any>(val exception: Throwable) : ApiResult<T, E>

class HandlerApi {
    val gson = Gson()
    private val cache: MutableMap<Pair<String, Long>, ApiResult<*, *>> = mutableMapOf()
    private val mutex = Mutex()
    suspend fun <T : Any, E : Any> addResponse(url: String, response: ApiResult<T, E>) {
        mutex.withLock {
            cache.put(Pair(url, System.currentTimeMillis()), response)
        }
    }

    suspend fun <T : Any, E : Any> getApiResult(url: String, delay: Long): ApiResult<T, E>? {
        mutex.withLock {
            val key = cache.keys.firstOrNull { it.first == url } ?: return null
            if ((key.second + delay) < System.currentTimeMillis()) {
                cache.remove(key)
                return null
            }
            val response = cache[key]
            return if (response is ApiResult<*, *>) {
                response as ApiResult<T, E>
            } else null
        }
    }

    suspend inline fun <reified T : Any, reified E : Any> handle(
        withCacheUrl: String = "",
        delay: Long = 5000L,
        execute: suspend () -> Response<T>
    ): ApiResult<T, E> {
        if (withCacheUrl.isNotEmpty()) {
            val apiResultCache = getApiResult<T, E>(withCacheUrl, delay)
            apiResultCache?.let {
                return it
            }
        }
        val result: ApiResult<T, E> = try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiSuccess(body)
            } else {
                val errorBody = parseErrorBody<E>(response.errorBody())
                ApiError(
                    statusCode = response.code(),
                    data = errorBody
                )
            }
        } catch (e: HttpException) {
            ApiException(e)
        } catch (e: Throwable) {
            ApiException(e)
        }
        addResponse(withCacheUrl, result)
        return result
    }

    inline fun <reified E : Any> parseErrorBody(errorBody: ResponseBody?): E {
        return gson.fromJson(errorBody?.string().orEmpty(), E::class.java)
    }
}

suspend fun <T : Any, E : Any> ApiResult<T, E>.onSuccess(
    executable: suspend (T) -> Unit
): ApiResult<T, E> = apply {
    if (this is ApiSuccess<T, E>) {
        executable(data)
    }
}

suspend fun <T : Any, E : Any> ApiResult<T, E>.onError(
    executable: suspend (data: ApiError<T, E>) -> Unit
): ApiResult<T, E> = apply {
    if (this is ApiError<T, E>) {
        executable(this)
    }
}

suspend fun <T : Any, E : Any> ApiResult<T, E>.onException(
    executable: suspend (e: Throwable) -> Unit
): ApiResult<T, E> = apply {
    if (this is ApiException<T, E>) {
        executable(exception)
    }
}
