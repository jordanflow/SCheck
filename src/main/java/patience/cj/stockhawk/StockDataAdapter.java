package patience.cj.stockhawk;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

/**
 * Created by cj on 9/1/2017.
 */

public class StockDataAdapter extends RecyclerView.Adapter<StockDataAdapter.StockViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public StockDataAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup v, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.stock_list_view, v, false);
        return new StockViewHolder(view);
    }

    @Override
    public  void onBindViewHolder(StockViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(StockDataContract.StockEntry._ID);
        int dateIndex = mCursor.getColumnIndex(StockDataContract.StockEntry.DATE);
        int closeIndex = mCursor.getColumnIndex(StockDataContract.StockEntry.CLOSE);

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(dateIndex);
        int priority = mCursor.getInt(closeIndex);

        holder.itemView.setTag(id);
        holder.dateOfClose.setText(description);
        holder.closeOfDay.setText(String.valueOf(priority));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView dateOfClose;
        TextView closeOfDay;

        public StockViewHolder(View itemView) {
            super(itemView);
            dateOfClose = (TextView) itemView.findViewById(R.id.date_of_close);
            closeOfDay = (TextView) itemView.findViewById(R.id.close_of_day);
        }

    }
}
