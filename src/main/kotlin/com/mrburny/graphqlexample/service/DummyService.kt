package com.mrburny.graphqlexample.service

import com.mrburny.graphqlexample.schema.ExtraParam
import com.mrburny.graphqlexample.schema.SampleRequest
import com.mrburny.graphqlexample.schema.SampleResponse

object DummyService {
    private const val MAX_AGE_IN_MILLIS = 43200000
    private const val VALUE_PARAM_NAME = "value"
    private const val EXTRA_PARAM_NAME = "extra"

    fun evaluate(input: SampleRequest): SampleResponse =
        if (input.isAlternative()) {
            SampleResponse(input.someOtherValue, "alternative", emptyList())
        } else {
            var value = input.defaultValue
            var reason = "default"
            var params = emptyList<ExtraParam>()

            if (input.hasExtraParams()) {
                val paramsMap = input.configParams.associate { Pair(it.key, it.value) }

                if (paramsMap.containsKey(VALUE_PARAM_NAME)) {
                    value = paramsMap[VALUE_PARAM_NAME]!!
                    reason = "params"
                    if (paramsMap.containsKey(EXTRA_PARAM_NAME)) {
                        params = listOf(ExtraParam(EXTRA_PARAM_NAME, paramsMap[EXTRA_PARAM_NAME]))
                    }
                }
            }

            SampleResponse(value, reason, params)
        }

    private fun SampleRequest.isAlternative() =
        someOtherValue != null && ageInMillis != null && ageInMillis < MAX_AGE_IN_MILLIS

    private fun SampleRequest.hasExtraParams() = configParams != null && configParams.isNotEmpty()
}