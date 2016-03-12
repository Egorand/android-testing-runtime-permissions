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

package me.egorand.contactssync.data;

import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class Contact {

    public final String name;
    public final String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Contact fromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY));
        String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.DATA));
        return new Contact(name, phoneNumber);
    }
}
