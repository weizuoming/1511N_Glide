package com.dash.a1511n.presenter;

import com.dash.a1511n.model.FragmentFenLeiRightModel;
import com.dash.a1511n.model.bean.ChildFenLeiBean;
import com.dash.a1511n.presenter.inter.FenLeiRightPresenterInter;
import com.dash.a1511n.view.Iview.FenLeiRightInter;
import com.dash.a1511n.view.fragment.FragmentFenLeiRight;

/**
 * Created by Dash on 2018/1/25.
 */
public class FragmentFenLeiRightPresenter implements FenLeiRightPresenterInter {

    private FenLeiRightInter fenLeiRightInter;
    private FragmentFenLeiRightModel fragmentFenLeiRightModel;

    public FragmentFenLeiRightPresenter(FenLeiRightInter fenLeiRightInter) {
        this.fenLeiRightInter = fenLeiRightInter;

        fragmentFenLeiRightModel = new FragmentFenLeiRightModel(this);
    }

    public void getChildData(String childFenLeiUrl, int cid) {

        fragmentFenLeiRightModel.getChildData(childFenLeiUrl,cid);
    }

    @Override
    public void onSuncess(ChildFenLeiBean childFenLeiBean) {

        fenLeiRightInter.getSuccessChildData(childFenLeiBean);

    }
}
