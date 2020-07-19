package com.michaeludjiawan.githubfinder.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.michaeludjiawan.githubfinder.FakeApiService
import com.michaeludjiawan.githubfinder.R
import com.michaeludjiawan.githubfinder.UserFactory
import com.michaeludjiawan.githubfinder.data.api.ApiService
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    companion object {
        private val fakeApi = FakeApiService()

        @BeforeClass
        @JvmStatic
        fun injectModules() = loadModules
        private val loadModules by lazy { loadKoinModules(module { single<ApiService> { fakeApi } }) }
    }

    private val userFactory = UserFactory()
    private val placeholderQuery = "query"
    private val placeholderName = "name"

    @Before
    fun init() {
        fakeApi.setReturnError(false)
        fakeApi.addUser(userFactory.createUser(placeholderName))
        fakeApi.addUser(userFactory.createUser(placeholderName))
    }

    @Test
    fun load_defaultEmpty() {
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.rv_main_users)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.tv_main_info)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun search_showSuccess() {
        launchFragmentInContainer<MainFragment>()

        // Setup query and perform search
        onView(withId(R.id.et_main_input)).perform(clearText(), typeText(placeholderQuery))
        onView(withId(R.id.btn_main_search)).perform(click())

        onView(withId(R.id.rv_main_users)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.tv_main_info)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun search_showError() {
        fakeApi.setReturnError(true)
        launchFragmentInContainer<MainFragment>()

        // Setup query and perform search
        onView(withId(R.id.et_main_input)).perform(clearText(), typeText(placeholderQuery))
        onView(withId(R.id.btn_main_search)).perform(click())

        onView(withId(R.id.tv_main_info)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_main_users)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun search_showErrorThenSuccess() {
        fakeApi.setReturnError(true)
        launchFragmentInContainer<MainFragment>()

        // Setup query and perform search
        onView(withId(R.id.et_main_input)).perform(clearText(), typeText(placeholderQuery))
        onView(withId(R.id.btn_main_search)).perform(click())

        onView(withId(R.id.tv_main_info)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_main_users)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        fakeApi.setReturnError(false)
        onView(withId(R.id.btn_main_search)).perform(click())

        onView(withId(R.id.rv_main_users)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.tv_main_info)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun search_showEmptyWithMessage() {
        fakeApi.clear()
        launchFragmentInContainer<MainFragment>()

        // Setup query and perform search
        onView(withId(R.id.et_main_input)).perform(clearText(), typeText(placeholderQuery))
        onView(withId(R.id.btn_main_search)).perform(click())

        val emptyInfo = getInstrumentation().targetContext.getString(R.string.empty_result_info)

        onView(withId(R.id.tv_main_info)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_main_info)).check(matches(ViewMatchers.withText(emptyInfo)))
        onView(withId(R.id.rv_main_users)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.pb_main_progress_bar)).check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

}