package com.smartperty.smartperty.login.beforeLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tools.CommonViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_before_login_view_pager.view.*


class BeforeLoginViewPagerFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: CommonViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_before_login_view_pager, container, false)

        viewPagerAdapter = CommonViewPagerAdapter(manager = childFragmentManager)
        viewPagerAdapter.addFragment(
            BeforeLoginPageFragment(
                "雲端數據", "即時，一致，正確，遠距工作"
            )
        )
        viewPagerAdapter.addFragment(
            BeforeLoginPageFragment(
                "自動化耗時工作", "所有修繕紀錄與維修費用，計算報酬率，產生報告書"
            )
        )
        viewPagerAdapter.addFragment(
            BeforeLoginPageFragment(
                "分析數據", "幫您分析數據，尋找下一個機會"
            )
        )
        viewPagerAdapter.addFragment(
            BeforeLoginPageFragment(
                "提昇房客體驗", "把繁瑣的是交給軟體做，把時間精神留給服務客戶，建立關係"
            )
        )

        viewPager = root.view_pager
        viewPager.adapter = viewPagerAdapter

        val indicator = root.indicator
        indicator.setViewPager(viewPager)

//        viewPagerAdapter.registerDataSetObserver(indicator.dataSetObserver)

        root.button_start_login.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_beforeLoginViewPagerFragment_to_loginFragment
            )
        }

        return root
    }

}