package com.tanjiajun.androidgenericframework.data.remote.repository

import com.tanjiajun.androidgenericframework.data.model.ListData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryMapper
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryResponseData
import com.tanjiajun.androidgenericframework.utils.dateFormatForRepository
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 2020/5/6.
 */
class RepositoryRemoteDataSource(
        retrofit: Retrofit
) {

    private val service: Service = retrofit.create(Service::class.java)

    suspend fun fetchRepositories(languageName: String,
                                  fromDateTime: LocalDateTime): List<RepositoryData> =
            service
                    .fetchRepositories(
                            query = "language:${languageName} created:>${fromDateTime.format(dateFormatForRepository())}",
                            sort = "stars"
                    )
                    .items
                    ?.map { RepositoryMapper.toRepositoryData(it) }
                    ?: emptyList()

    interface Service {

        @GET("search/repositories")
        suspend fun fetchRepositories(@Query("q") query: String,
                                      @Query("sort") sort: String): ListData<RepositoryResponseData>

    }

}