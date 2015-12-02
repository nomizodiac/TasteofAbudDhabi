package org.progos.tasteofabuddhabicms.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.utility.Commons;
import org.progos.tasteofabuddhabicms.utility.FontFactory;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class VenueMapFragment extends Fragment {

    Context context;
    ImageViewTouch mImage;
    TextView venueMapLbl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_venue_maps, container, false);
        uInit(view);

        Bitmap mapImage = BitmapFactory.decodeResource(getResources(), R.drawable.mapimage);
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        mImage.setImageBitmap(mapImage);

        return view;
    }

    private void uInit(View view) {

        mImage = (ImageViewTouch) view.findViewById(R.id.image);
        venueMapLbl = (TextView) view.findViewById(R.id.venueMapLbl);

        venueMapLbl.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_BOLD));
    }
}
