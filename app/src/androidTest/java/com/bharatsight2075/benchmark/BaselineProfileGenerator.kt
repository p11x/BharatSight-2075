package com.bharatsight2075.benchmark

import androidx.benchmark.macro.ExperimentalTestApi
import androidx.benchmark.macro.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val macrobenchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun generateBaselineProfile() = macrobenchmarkRule.profile(
        packageName = "com.bharatsight2075"
    ) {
        pressHome()
        startActivityAndWait()
    }
}