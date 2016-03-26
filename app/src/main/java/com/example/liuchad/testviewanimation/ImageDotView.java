package com.example.liuchad.testviewanimation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class ImageDotView extends View implements ViewPager.OnPageChangeListener{
	
	private int mCount;
	private int mCurrentNum;
	private int mSpace;

    private Bitmap mNormalBitmap;
    private Bitmap mSelectBitmap;

    public ViewPager.OnPageChangeListener mDelegatePageListener;

	public ImageDotView(Context context) {
		this(context,null);
	}
	
	public ImageDotView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ImageDotView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init(){
		mSpace = (int) (getResources().getDisplayMetrics().density*8);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xx_hh_bq_fy);
        mSelectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xx_hh_bq_fy_pre);
	}

    public void setSelectBitmap(int resId){
        mSelectBitmap = BitmapFactory.decodeResource(getResources(),resId);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        mDelegatePageListener = listener;
    }

    public void setImageCount(int count){
		mCount = count;
		if(mCount <= 1){
			setVisibility(View.GONE);
		}else{
			invalidate();
		}
	}

    public void setSpace(int space){
        mSpace = space;
        invalidate();
    }
	
	public void setSelection(int num){
		mCurrentNum = num;
		invalidate();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setViewPager(final ViewPager viewPager){
        if(viewPager == null){
            return;
        }
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ImageDotView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    ImageDotView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                setSelection(viewPager.getCurrentItem());
                viewPager.setOnPageChangeListener(ImageDotView.this);
            }
        });
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mCount > 0){
            int width = getWidth();
            int picWidth = mSelectBitmap.getWidth();
            int allWidth = picWidth*mCount+mSpace*(mCount-1);
            int left = (width - allWidth)/2;
            int itemWidth = picWidth+mSpace;
            for(int i=0;i<mCount;i++){
                if(mCurrentNum == i){
                    canvas.drawBitmap(mSelectBitmap, left+itemWidth*i,0, null);
                }else{
                    canvas.drawBitmap(mNormalBitmap, left+itemWidth*i,0,null);
                }

            }

		}
	}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(mDelegatePageListener != null){
            mDelegatePageListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }
//        mCurrentNum = position;
//        mScrollX = positionOffset * (getWidth() - 2 * mSpace) / mCount;
//        invalidate();
    }

    @Override
    public void onPageSelected(int i) {
        if(mDelegatePageListener != null){
            mDelegatePageListener.onPageSelected(i);
        }
        mCurrentNum = i;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if(mDelegatePageListener != null){
            mDelegatePageListener.onPageScrollStateChanged(i);
        }

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentNum = savedState.currentPosition;
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mCurrentNum;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
