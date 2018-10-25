package com.example.vustk.goodfoodv101.tabPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.view.PagerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Farm;
import com.squareup.picasso.Picasso;

import java.util.List;

//for main page first view (image view for live broadcast)
class ForImageAdapter extends PagerAdapter {
    private List<Farm> farmList;


    private Context context;
    private LayoutInflater layoutInflater;

    public ForImageAdapter(Context context, List<Farm> farmList) {
        this.farmList = farmList;
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return farmList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        Farm farm = farmList.get(position);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView txtFarmName = (TextView) view.findViewById(R.id.txtFarmNme);
        TextView txtFarmAddress = (TextView)view.findViewById(R.id.txtFarmAddress);

        txtFarmName.setText(farm.getName());
        txtFarmAddress.setText(farm.getLocation());
        Picasso.get().load(farm.getCover()).into(imageView);

/*        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), farm[position], Toast.LENGTH_SHORT).show();
            }
        });
*/
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


}