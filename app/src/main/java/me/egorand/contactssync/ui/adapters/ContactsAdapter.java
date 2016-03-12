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

package me.egorand.contactssync.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import me.egorand.contactssync.R;
import me.egorand.contactssync.data.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;

    private final List<Contact> contacts;

    public ContactsAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.contacts = new ArrayList<>();
    }

    public void setContacts(List<Contact> contacts) {
        int oldSize = contacts.size();
        this.contacts.clear();
        notifyItemRangeRemoved(0, oldSize);
        this.contacts.addAll(contacts);
        notifyItemRangeInserted(0, contacts.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.row_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        renderContactIcon(holder.contactIcon, contact);
        holder.contactName.setText(contact.name);
        holder.contactPhoneNumber.setText(contact.phoneNumber);
    }

    private void renderContactIcon(ImageView imageView, Contact contact) {
        String contactInitial = String.valueOf(contact.name.charAt(0));
        int color = ContextCompat.getColor(imageView.getContext(), R.color.colorAccent);
        TextDrawable drawable = TextDrawable.builder().buildRound(contactInitial, color);
        imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView contactIcon;
        final TextView contactName;
        final TextView contactPhoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            this.contactIcon = (ImageView) itemView.findViewById(R.id.contact_icon);
            this.contactName = (TextView) itemView.findViewById(R.id.contact_name);
            this.contactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
        }
    }
}
