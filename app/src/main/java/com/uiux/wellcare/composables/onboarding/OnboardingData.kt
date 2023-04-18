package com.uiux.wellcare.composables.onboarding

import androidx.annotation.RawRes
import com.uiux.wellcare.R

class OnboardingData(@RawRes val resId: Int, val title: String, val desc: String)

val listData = listOf(
    OnboardingData(
        R.raw.ill_goal,
        "Define Your Goal",
        "Aliquam pharetra tortor nec odio pellentesque dignissim. Etiam ultricies sollicitudin est sit amet rutrum.",
    ),
    OnboardingData(
        R.raw.ill_ilustration,
        "Green Marketing",
        "Maecenas gravida, ipsum eget ultricies cursus, nisl lectus ullamcorper mi, egestas blandit mi sem vitae lorem.",
    ),
    OnboardingData(
        R.raw.ill_ilustration_1,
        "Clear Task",
        "Vestibulum interdum dolor sit amet orci pulvinar, id mattis mi laoreet. Aliquam venenatis metus velit, in fringilla urna fermentum id.",
    ),
    OnboardingData(
        R.raw.ill_security,
        "Secured Storage",
        "Vivamus convallis odio posuere, tempus nulla eget, ullamcorper erat. In ut tortor consequat, pharetra ex id, interdum mauris.",
    ),
)