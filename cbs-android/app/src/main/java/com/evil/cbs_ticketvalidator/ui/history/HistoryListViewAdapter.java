package com.evil.cbs_ticketvalidator.ui.history;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.data.model.TripAttendAttempt;


import java.util.List;

public class HistoryListViewAdapter extends ArrayAdapter<TripAttendAttempt> {

    private final Activity context;
    private final List<TripAttendAttempt> items;

    public HistoryListViewAdapter(Activity context, List<TripAttendAttempt> items) {
        super(context, R.layout.history_list_view_layout, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history_list_view_layout, null, true);
        TripAttendAttempt attempt = items.get(position);
//        TextView ticketId = (TextView) rowView.findViewById(R.id.ticket_id_text_view);
//        ticketId.setText(String.format("TicketId: %d", attempt.getTicketId()));
        TextView entryDate = (TextView) rowView.findViewById(R.id.entry_date_text_view);
        entryDate.setText(String.format("Date: %s", attempt.getAttendDate()));
        TextView entryStatus = (TextView) rowView.findViewById(R.id.entry_status_text_view);
        entryStatus.setText(String.format("Entry status: %s", attempt.getAttemptStatus()));
        if (attempt.getAttemptStatus().toLowerCase().contains("not"))
            entryStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        else
            entryStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        return rowView;
    }
}
