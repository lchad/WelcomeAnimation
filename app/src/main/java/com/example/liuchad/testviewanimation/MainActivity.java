package com.example.liuchad.testviewanimation;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.florent37.viewanimator.ViewAnimator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.vp_guide) ViewPager vpGuide;
    @Bind(R.id.btnLogin) Button btnLogin;
    @Bind(R.id.btnPsw) Button btnPsw;
    @Bind(R.id.btn_enter) Button btnEnter;
    @Bind(R.id.tv_skip) TextView tvSkip;
    @Bind(R.id.chat_smile_dot) ImageDotView dotView;

    private List<View> pageViews;

    private static final int GUIDE_PAGE_COUNT = 4;

    private boolean isInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initPageViews();
        isInit = true;
        dotView.setSelectBitmap(R.drawable.xx_hh_bq_fy_red);
        dotView.setImageCount(GUIDE_PAGE_COUNT);
        btnLogin.setOnClickListener(this);
        btnPsw.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                dotView.setSelection(position);
                if (position == GUIDE_PAGE_COUNT - 1) {
                    tvSkip.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                    btnPsw.setVisibility(View.VISIBLE);
                    btnEnter.setVisibility(View.GONE);
                    addButtonAnimation();
                } else {
                    btnLogin.setVisibility(View.GONE);
                    btnPsw.setVisibility(View.GONE);
                    btnEnter.setVisibility(View.GONE);
                    tvSkip.setVisibility(View.VISIBLE);
                }
                if (position == GUIDE_PAGE_COUNT - 1) {
                    dotView.setVisibility(View.INVISIBLE);
                } else {
                    dotView.setVisibility(View.VISIBLE);
                }
                addAnimation(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vpGuide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pageViews.get(position));
                return pageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pageViews.get(position));
            }
        });
        addAnimation(0);
    }

    private void initPageViews() {
        pageViews = new ArrayList<>();
        pageViews.add(getChatPage());
        pageViews.add(getUBanPage());
        pageViews.add(getCallPage());
        pageViews.add(getDiskPage());
    }

    private void addAnimation(int position) {
        switch (position) {
            case 0:
                addChatAnimation();
                break;
            case 1:
                addUBanAnimation();
                break;
            case 2:
                addCallAnimation();
                break;
            case 3:
                addDiskAnimation();
                break;
            default:
                break;
        }
    }

    private void addButtonAnimation() {
        if (!isInit) return;
        isInit = false;
        AlphaAnimation animButton = new AlphaAnimation(0, 1);
        animButton.setStartOffset(2500);
        animButton.setDuration(400);
        if (btnLogin.isShown()) {
            btnLogin.startAnimation(animButton);
            btnPsw.startAnimation(animButton);
        } else if (btnEnter.isShown()) {
            btnEnter.startAnimation(animButton);
        }
    }

    private void addDiskAnimation() {
        if (pageViews.size() < GUIDE_PAGE_COUNT) return;
        ViewGroup viewGroup = (ViewGroup) pageViews.get(3);
        if (viewGroup.isShown()) {
            viewGroup.findViewById(R.id.cloud_line_background).setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.cloud_text))
            .alpha(0, 1)
            .startDelay(200)
            .duration(500)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.cloud_head))
            .alpha(0, 1)
            .startDelay(1000)
            .duration(500)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.cloud_phone))
            .dp()
            .translationX(-255, 0)
            .alpha(0, 1)
            .startDelay(2000)
            .duration(600)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.cloud_main))
            .alpha(0, 1)
            .startDelay(1500)
            .duration(600)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.cloud_line_background))
            .dp()
            .translationX(0, 255)
            .startDelay(2000)
            .duration(600)
            .start();
    }

    private void addChatAnimation() {
        if (pageViews.size() < GUIDE_PAGE_COUNT) return;
        ViewGroup viewGroup = (ViewGroup) pageViews.get(0);
        if (viewGroup.isShown()) {
            viewGroup.findViewById(R.id.chat_background).setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_text))
            .alpha(0, 1)
            .startDelay(200)
            .duration(500)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_head))
            .alpha(0, 1)
            .startDelay(400)
            .duration(400)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_content))
            .alpha(0, 1)
            .startDelay(800)
            .duration(1600)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_background))
            .dp()
            .translationY(0, 185)
            .startDelay(2000)
            .duration(2000)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_yellow))
            .alpha(0, 1)
            .startDelay(2000)
            .duration(800)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_blue))
            .alpha(0, 1)
            .startDelay(2800)
            .duration(800)
            .start();

        ViewAnimator
            .animate(viewGroup.findViewById(R.id.chat_green))
            .alpha(0, 1)
            .startDelay(3600)
            .duration(800)
            .start();
    }

    private void addCallAnimation() {
        if (pageViews.size() < GUIDE_PAGE_COUNT) return;
        ViewGroup viewGroup = (ViewGroup) pageViews.get(2);
        if (viewGroup.isShown()) {
            viewGroup.findViewById(R.id.call_background_tl).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.call_background_tr).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.call_background_bl).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.call_background_br).setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_text))
            .alpha(0, 1)
            .startDelay(200)
            .duration(500)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_head))
            .alpha(0, 1)
            .startDelay(500)
            .duration(400)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_background_tl))
            .dp()
            .translationY(0, -134)
            .startDelay(1000)
            .duration(800)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_background_tr))
            .dp()
            .translationY(0, 134)
            .startDelay(1000)
            .duration(800)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_background_bl))
            .dp()
            .translationY(0, -156)
            .startDelay(1000)
            .duration(800)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.call_background_br))
            .dp()
            .translationY(0, 133)
            .startDelay(1000)
            .duration(800)
            .start();

        ViewAnimator
            .animate(
                viewGroup.findViewById(R.id.call_yellow_one),
                viewGroup.findViewById(R.id.call_yellow_two),
                viewGroup.findViewById(R.id.call_blue),
                viewGroup.findViewById(R.id.call_green))
            .alpha(0, 1)
            .startDelay(1400)
            .duration(500)
            .start();
    }

    private void addUBanAnimation() {
        if (pageViews.size() < GUIDE_PAGE_COUNT) return;
        ViewGroup viewGroup = (ViewGroup) pageViews.get(1);
        if (viewGroup.isShown()) {
            viewGroup.findViewById(R.id.uban_backgroud).setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_text))
            .alpha(0, 1)
            .startDelay(200)
            .duration(500)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_head))
            .alpha(0, 1)
            .startDelay(500)
            .duration(400)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_content))
            .alpha(0, 1)
            .startDelay(800)
            .duration(400)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_backgroud))
            .dp()
            .translationX(0, 240)
            .startDelay(1400)
            .duration(1000)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_green))
            .alpha(0, 1)
            .startDelay(1500)
            .duration(400)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_blue))
            .alpha(0, 1)
            .startDelay(1700)
            .duration(400)
            .start();

        ViewAnimator.animate(viewGroup.findViewById(R.id.uban_yellow))
            .alpha(0, 1)
            .startDelay(2200)
            .duration(400)
            .start();
    }

    private ViewGroup getChatPage() {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this, R.layout.page_chat, null);
        viewGroup.setVisibility(View.GONE);
        return viewGroup;
    }

    private ViewGroup getCallPage() {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this, R.layout.page_call, null);
        viewGroup.setVisibility(View.GONE);
        return viewGroup;
    }

    private ViewGroup getUBanPage() {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this, R.layout.page_uban, null);
        viewGroup.setVisibility(View.GONE);
        return viewGroup;
    }

    private ViewGroup getDiskPage() {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this, R.layout.page_cloud, null);
        viewGroup.setVisibility(View.GONE);
        return viewGroup;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
            case R.id.tv_skip:
                finish();
                return;
            case R.id.btnPsw:
                finish();
                break;
            case R.id.btn_enter:
                finish();
                return;
            default:
                break;
        }
    }
}
