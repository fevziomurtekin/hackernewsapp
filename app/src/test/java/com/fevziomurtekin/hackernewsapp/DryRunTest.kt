package com.fevziomurtekin.hackernewsapp

import com.fevziomurtekin.di.onlineNewsApp
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.with
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.mockito.Mockito.mock

/**
 * Test Koin Modules.
 */

class DryRunTest : KoinTest{

    @After
    fun after(){
        closeKoin()
    }

    /**
     * is koin created?
     */
    @Test
    fun testRemoteConfiguration(){
        startKoin(onlineNewsApp) with  mock(BaseApplication::class.java)
        dryRun()
    }


}