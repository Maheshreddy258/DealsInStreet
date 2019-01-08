package com.deals.sine90.dealsinstreet.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.BannerDetails;
import com.deals.sine90.dealsinstreet.Login;
import com.deals.sine90.dealsinstreet.MapsActivity;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Food.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Food#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Food extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView banner1,banner2,banner3,banner4,banner5,banner6,like,like2,like3,lik4,like5,like6;
    Resources res;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,more1,more2,more3,more4,more5,more6,share,share2,share3,share4,share5,share6,direction,direction1,direction2,direction3,direction4,direction5;
    TextView liketext1,liketext2,liketext3,liketext4,liketext5,liketext6;
    SessionManagement sessionManagement;

    private OnFragmentInteractionListener mListener;

    public Food() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Food.
     */
    // TODO: Rename and change types and number of parameters
    public static Food newInstance(String param1, String param2) {
        Food fragment = new Food();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food, container, false);
        sessionManagement = new SessionManagement(getContext());

        //getting resources
        res = getContext().getResources();
        final int newColor = res.getColor(R.color.like_color);
        //getting ids from layout
       // banner1=(ImageView)view.findViewById(R.id.banner1);
     //   banner2=(ImageView)view.findViewById(R.id.banner2);
     //   banner3=(ImageView)view.findViewById(R.id.banner3);
     //   banner4=(ImageView)view.findViewById(R.id.banner4);
    //    banner5=(ImageView)view.findViewById(R.id.banner5);
      //  banner6=(ImageView)view.findViewById(R.id.banner6);
        like=(ImageView)view.findViewById(R.id.like);
        like2=(ImageView)view.findViewById(R.id.like2);
        like3=(ImageView)view.findViewById(R.id.like3);
    /*    lik4=(ImageView)view.findViewById(R.id.like4);
        like5=(ImageView)view.findViewById(R.id.like5);
        like6=(ImageView)view.findViewById(R.id.like6);*/
        ll1=(LinearLayout)view.findViewById(R.id.linear_like1);
        ll2=(LinearLayout)view.findViewById(R.id.linear_like2);
        ll3=(LinearLayout)view.findViewById(R.id.linear_like3);
       /* ll4=(LinearLayout)view.findViewById(R.id.linear_like4);
        ll5=(LinearLayout)view.findViewById(R.id.linear_like5);
        ll6=(LinearLayout)view.findViewById(R.id.linear_like6);*/
        more1=(LinearLayout)view.findViewById(R.id.linear_more);
        more2=(LinearLayout)view.findViewById(R.id.linear_more2);
        more3=(LinearLayout)view.findViewById(R.id.linear_more3);
       /* more4=(LinearLayout)view.findViewById(R.id.linear_more4);
        more5=(LinearLayout)view.findViewById(R.id.linear_more5);
        more6=(LinearLayout)view.findViewById(R.id.linear_more6);*/
        share=(LinearLayout)view.findViewById(R.id.share);
        share2=(LinearLayout)view.findViewById(R.id.share2);
        share3=(LinearLayout)view.findViewById(R.id.share3);
       /* share4=(LinearLayout)view.findViewById(R.id.share4);
        share5=(LinearLayout)view.findViewById(R.id.share5);
        share6=(LinearLayout)view.findViewById(R.id.share6);*/
        direction=(LinearLayout)view.findViewById(R.id.direction);
        direction1=(LinearLayout)view.findViewById(R.id.direction2);
        direction2=(LinearLayout)view.findViewById(R.id.direction3);
       // direction3=(LinearLayout)view.findViewById(R.id.direction4);
      /*  direction4=(LinearLayout)view.findViewById(R.id.direction5);
        direction5=(LinearLayout)view.findViewById(R.id.direction6);*/
        liketext1=(TextView)view.findViewById(R.id.liketext1);
        liketext2=(TextView)view.findViewById(R.id.liketext2);
        liketext3=(TextView)view.findViewById(R.id.liketext3);
     /*   liketext4=(TextView)view.findViewById(R.id.liketext4);
        liketext5=(TextView)view.findViewById(R.id.liketext5);
        liketext6=(TextView)view.findViewById(R.id.liketext6);*/
      /*  banner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image=((BitmapDrawable)banner1.getDrawable()).getBitmap();
                Intent intent=new Intent(getContext(), BannerDetails.class);
                startActivity(intent);
            }
        });*/
       /* banner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        banner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        banner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        banner5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        banner6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });*/
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    like.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                    liketext1.setTextColor(newColor);
                }else {
                    Intent intent=new Intent(getContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login to like the product", Toast.LENGTH_SHORT).show();
                }

            }
        });
       /* ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    like2.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    liketext4.setTextColor(newColor);
                }

                else {
                    Intent intent=new Intent(getContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login to like the product", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    like3.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    liketext3.setTextColor(newColor);
                } else {
                    Intent intent=new Intent(getContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login to like the product", Toast.LENGTH_SHORT).show();
                }


            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    lik4.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    liketext2.setTextColor(newColor);
                } else {
                    Intent intent=new Intent(getContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login to like the product", Toast.LENGTH_SHORT).show();
                }

            }
        });
      /*  ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    like5.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    liketext5.setTextColor(newColor);
                }

            }
        });
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManagement.isLoggedIn()==true)
                {
                    like6.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    liketext6.setTextColor(newColor);
                } else {
                    Intent intent=new Intent(getContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login to like the product", Toast.LENGTH_SHORT).show();
                }


            }
        });*/
        more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
       /* more4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        more5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });
        more6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BannerDetails.class);
                startActivity(intent);
            }
        });*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        share3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        /*share4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        share5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        share6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });*/
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        direction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);

            }
        });
        direction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
       /* direction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        direction4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        direction5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
