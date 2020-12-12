package com.smartperty.smartperty.landlord.menu.dataAnalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tools.CommonViewPagerAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_data_analysis.view.*

class DataAnalysisFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: CommonViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_data_analysis, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        viewPager = root.view_pager
        viewPagerAdapter = CommonViewPagerAdapter(childFragmentManager)


        viewPagerAdapter.addFragment(DataAnalysisPageFragment("組別",
            GlobalVariables.dataAnalysisByGroupBarChartDataSet.dataList,
            GlobalVariables.dataAnalysisByGroupPieChartDataSet.dataList
        ), "組別")
        viewPagerAdapter.addFragment(DataAnalysisPageFragment("類型",
            GlobalVariables.dataAnalysisByTypeBarChartDataSet.dataList,
            GlobalVariables.dataAnalysisByTypePieChartDataSet.dataList
        ), "類型")
        viewPagerAdapter.addFragment(DataAnalysisPageFragment("坪數",
            GlobalVariables.dataAnalysisBySquareFtBarChartDataSet.dataList,
            GlobalVariables.dataAnalysisBySquareFtPieChartDataSet.dataList
        ), "坪數")
        viewPager.adapter = viewPagerAdapter

        root.tab_layout.setupWithViewPager(viewPager)

        return root

    }
}
