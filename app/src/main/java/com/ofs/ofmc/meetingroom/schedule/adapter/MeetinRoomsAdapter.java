package com.ofs.ofmc.meetingroom.schedule.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.schedule.ChooseMeetingroomView;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class MeetinRoomsAdapter extends RecyclerView.Adapter<MeetinRoomsAdapter.ViewHolder> {

    private ArrayList<MeetingRoom> mDataset;
    private RealmResults<MeetingRoom> realmDataset;
    private ChooseMeetingroomView.ClickListener mClickListener;

    public MeetinRoomsAdapter(ArrayList<MeetingRoom> myDataset,ChooseMeetingroomView.ClickListener mClickListener) {
        mDataset = myDataset;
        this.mClickListener = mClickListener;
    }

    public MeetinRoomsAdapter(RealmResults<MeetingRoom> realmDataset, ChooseMeetingroomView.ClickListener mClickListener) {
        this.realmDataset = realmDataset;
        this.mClickListener = mClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mRoomId;
        public CardView mItemContainer;
        public TextView mRoomName;
        public TextView mRoomPhase;
        public TextView mRoomSeatings;

        public ViewHolder(View v) {
            super(v);
            mItemContainer = (CardView) v.findViewById(R.id.item_container);
            mRoomName = (TextView) v.findViewById(R.id.name);
            mRoomPhase = (TextView) v.findViewById(R.id.phase);
            mRoomSeatings = (TextView) v.findViewById(R.id.seating);
        }
    }


    @Override
    public MeetinRoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meetingroom, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MeetinRoomsAdapter.ViewHolder holder, final int position) {

        if(null != mDataset){
            holder.mRoomName.setText(mDataset.get(position).getmRoomName());
            holder.mRoomPhase.setText(mDataset.get(position).getmRoomPhase());
            holder.mRoomSeatings.setText(String.valueOf(mDataset.get(position).getmSeating()));
            holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mClickListener!=null)
                        mClickListener.setOnListItemChooseListener(mDataset.get(position),position);
                }
            });
        }else if(null!=realmDataset){
            holder.mRoomName.setText(realmDataset.get(position).getmRoomName());
            holder.mRoomPhase.setText(realmDataset.get(position).getmRoomPhase());
            holder.mRoomSeatings.setText(String.valueOf(realmDataset.get(position).getmSeating()));
            holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mClickListener!=null)
                        mClickListener.setOnListItemChooseListener(realmDataset.get(position),position);
                }
            });
        }else{
            //
        }

    }



    @Override
    public int getItemCount() {
        if (null != mDataset)
            return  mDataset.size();
        else if(null!= realmDataset)
            return  realmDataset.size();
        else
            return 0;
    }
}
