package com.example.clinica;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.PendingViewHolder> {
    ArrayList<Doctors > drList;
    PeopleDatabase dataBaseClass;

    public PendingListAdapter(ArrayList<Doctors> drList) {
        this.drList = drList;
    }
    @NonNull
    @Override
    public PendingListAdapter.PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new PendingListAdapter.PendingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pending_doctor_form_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PendingListAdapter.PendingViewHolder holder, int position) {
        holder.doctorName.setText("Doctor's Name:" + drList.get(position).getName());
        holder.doctorEmail.setText("Doctor's Email:" + drList.get(position).getUserEmail());
        holder.doctorSpecialty.setText("Doctor's Specialty:" + drList.get(position).getSpecialty());
        holder.workingHoursStart.setText("From:" + drList.get(position).getWorkingHoursStart());
        holder.workingHourEnd.setText("To:" + drList.get(position).getWorkingHoursEnd());
        holder.docLocation.setText("Location:" + drList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return drList.size();
    }
    class PendingViewHolder extends RecyclerView.ViewHolder{
        TextView doctorName;
        TextView doctorSpecialty;
        TextView docLocation;
        TextView workingHoursStart;
        TextView workingHourEnd;
        TextView doctorEmail;
        Button acceptButton,refuseButton;
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.docName);
            doctorEmail = itemView.findViewById(R.id.docEmail);
            doctorSpecialty = itemView.findViewById(R.id.docSpeciality);
            docLocation = itemView.findViewById(R.id.docLocation);
            workingHourEnd = itemView.findViewById(R.id.workingHoursEnd);
            workingHoursStart = itemView.findViewById(R.id.workingHoursStart);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            refuseButton = itemView.findViewById(R.id.refuseButton);
            dataBaseClass = new PeopleDatabase(itemView.getContext());
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    dataBaseClass.acceptDoctor(drList.get(getAbsoluteAdapterPosition()).getUserEmail());
                    drList.remove(drList.get(getAbsoluteAdapterPosition()));
                    PendingDoctors.getPendingListAdapter().notifyDataSetChanged();
                }
            });
            refuseButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    dataBaseClass.refuseDoctor(drList.get(getAbsoluteAdapterPosition()).getUserEmail());
                    drList.remove(drList.get(getAbsoluteAdapterPosition()));
                    PendingDoctors.getPendingListAdapter().notifyDataSetChanged();
                }
            });
        }

    }
}
