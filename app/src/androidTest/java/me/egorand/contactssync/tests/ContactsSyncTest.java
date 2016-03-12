/*
 * Copyright 2016 Egor Andreevici
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.egorand.contactssync.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import me.egorand.contactssync.R;
import me.egorand.contactssync.data.Contact;
import me.egorand.contactssync.ui.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static me.egorand.contactssync.utils.UiAutomatorUtils.assertViewWithTextIsVisible;
import static me.egorand.contactssync.utils.UiAutomatorUtils.denyCurrentPermission;
import static me.egorand.contactssync.utils.UiAutomatorUtils.denyCurrentPermissionPermanently;
import static me.egorand.contactssync.utils.UiAutomatorUtils.grantPermission;
import static me.egorand.contactssync.utils.UiAutomatorUtils.openPermissions;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactsSyncTest {

    public static final Contact[] TEST_CONTACTS = {
            new Contact("Alice", "+49123123"),
            new Contact("Bob", "+49124124"),
            new Contact("Christy", "+49125125")};

    @Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private UiDevice device;

    @Before
    public void setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void a_shouldDisplayPermissionRequestDialogAtStartup() throws Exception {
        assertViewWithTextIsVisible(device, "ALLOW");
        assertViewWithTextIsVisible(device, "DENY");

        // cleanup for the next test
        denyCurrentPermission(device);
    }

    @Test
    public void b_shouldDisplayShortRationaleIfPermissionWasDenied() throws Exception {
        denyCurrentPermission(device);

        onView(withText(R.string.permission_denied_rationale_short)).check(matches(isDisplayed()));
        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));
    }

    @Test
    public void c_shouldDisplayLongRationaleIfPermissionWasDeniedPermanently() throws Exception {
        denyCurrentPermissionPermanently(device);

        onView(withText(R.string.permission_denied_rationale_long)).check(matches(isDisplayed()));
        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));

        // will grant the permission for the next test
        onView(withText(R.string.grant_permission)).perform(click());
        openPermissions(device);
        grantPermission(device, "Contacts");
    }

    @Test
    public void d_shouldLoadContactsIfPermissionWasGranted() throws Exception {
        for (Contact contact : TEST_CONTACTS) {
            onView(withText(contact.name)).check(matches(isDisplayed()));
            onView(withText(contact.phoneNumber)).check(matches(isDisplayed()));
        }
    }
}
