package com.smartperty.smartperty.tenant.home.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.dataAnalysis.DataAnalysisPageFragment
import com.smartperty.smartperty.tools.CommonViewPagerAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_data_analysis.view.*

class ContractUploadFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: CommonViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_upload, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        viewPager = root.view_pager
        viewPagerAdapter = CommonViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.addFragment(
            ContractUploadImageFragment(), "上傳圖片")
        viewPagerAdapter.addFragment(
            ContractUploadPdfFragment(), "選擇PDF")
        viewPagerAdapter.addFragment(
            ContractUploadTextFragment(), "自訂範例合約")
        viewPager.adapter = viewPagerAdapter

        root.tab_layout.setupWithViewPager(viewPager)

        return root
    }

}