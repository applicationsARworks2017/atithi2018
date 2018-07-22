package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.Activity.Expenses;
import com.mars.myguest.Pojo.Expensedetails;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.R;
import com.mars.myguest.Util.Constants;

import java.util.ArrayList;

/**
 * Created by Amaresh on 7/2/18.
 */

public class ExpensesAdapter extends BaseAdapter {
    Context _context;
    ArrayList<Expensedetails> mylist;
    Holder holder;
    public ExpensesAdapter(Expenses expenses, ArrayList<Expensedetails> expensedetailsArrayList) {
        this._context=expenses;
        this.mylist=expensedetailsArrayList;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView item_details,cost,date;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Expensedetails _pos=mylist.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)_context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.expense_adapter,viewGroup, false);
            holder.item_details=(TextView) view.findViewById(R.id.details);
            holder.cost=(TextView)view.findViewById(R.id.cost);
            holder.date=(TextView)view.findViewById(R.id.date);

            view.setTag(holder);
        }
        else {
            holder = (Holder) view.getTag();
        }
        holder.item_details.setTag(i);
        holder.cost.setTag(i);
        holder.date.setTag(i);

        holder.item_details.setText(_pos.getDetails());
        holder.cost.setText("Rs."+_pos.getAmount());
        holder.date.setText(Constants.getOurDate(_pos.getCreated()));
        return view;
    }
}
