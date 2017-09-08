package domain.api.post

import domain.DomainHolder
import domain.SingleDisposableUseCase
import io.reactivex.Scheduler

class PostUseCase(
        private val something: String,
        asyncExecutionScheduler: Scheduler?,
        postExecutionScheduler: Scheduler) : SingleDisposableUseCase<DomainPostResult>(
        asyncExecutionScheduler = asyncExecutionScheduler,
        postExecutionScheduler = postExecutionScheduler) {
        private val parameterMapper = DomainPostParameterMapper()

    override fun buildUseCase() = DomainHolder.functionalityProvider.api()
            .post(parameterMapper.from(something))
}
