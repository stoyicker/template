package data.post

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.FluentMemoryPolicyBuilder
import com.nytimes.android.external.store3.base.impl.FluentStoreBuilder
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.middleware.moshi.MoshiParserFactory
import data.RequestFacade
import data.api.Api
import data.api.ApiHolder
import domain.post.DomainPostParameters
import domain.post.DomainPostResult
import io.reactivex.Single
import okio.BufferedSource
import java.util.concurrent.TimeUnit

/**
 * A source is a cache-enabled request execution abstraction that delegates caching to Store.
 */
internal object PostSource : RequestFacade<DomainPostParameters, Single<DomainPostResult>> {
    private val store : Store<PostResponse, PostParameters>
    private val requestMapper = PostRequestMapper()
    private val responseMapper = PostResponseMapper()

    init {
        store = FluentStoreBuilder.parsedWithKey<PostParameters, BufferedSource, PostResponse>(
                Fetcher { fetch(it, ApiHolder.apiImpl) }) {
            parsers = listOf(MoshiParserFactory.createSourceParser(PostResponse::class.java))
            memoryPolicy = FluentMemoryPolicyBuilder.build {
                expireAfterWrite = 10
                expireAfterTimeUnit = TimeUnit.MINUTES
            }
            stalePolicy = StalePolicy.REFRESH_ON_STALE
        }
    }

    override fun fetch(parameters: DomainPostParameters): Single<DomainPostResult> =
            store.fetch(requestMapper.from(parameters)).map { responseMapper.from(it) }

    private fun fetch(postParameters: PostParameters, api: Api) =
            api.post(postParameters).map { it.source() }
}
