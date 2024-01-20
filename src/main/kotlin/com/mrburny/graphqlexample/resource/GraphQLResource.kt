package com.mrburny.graphqlexample.resource

import com.mrburny.graphqlexample.schema.QueryResolver
import com.mrburny.graphqlexample.schema.SampleRequest
import com.mrburny.graphqlexample.schema.SampleResponse
import com.mrburny.graphqlexample.service.DummyService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.concurrent.CompletableFuture

@Controller
class GraphQLResource : QueryResolver {
    @QueryMapping
    override fun sampleIdempotentCall(@Argument("request") request: SampleRequest): CompletableFuture<SampleResponse> =
        CompletableFuture<SampleResponse>().completeAsync { DummyService.evaluate(request) }
}