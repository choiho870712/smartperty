package com.example.smartperty.tenant.home.contract


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.example.smartperty.R
import com.example.smartperty.tools.CommonViewPagerAdapter
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_contract.view.*

/**
 * A simple [Fragment] subclass.
 */
class TenantContractFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: CommonViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root = inflater.inflate(R.layout.tenant_fragment_contract, container, false)

        viewPager = root.viewpager_tenant_contract
        viewPagerAdapter = CommonViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.addFragment(TenantContractPage1Fragment(), "合約")
        viewPagerAdapter.addFragment(TenantContractPage2Fragment(), "證明")
        viewPager.adapter = viewPagerAdapter

        root.tab_layout_tenant_contract.setupWithViewPager(viewPager)

        return root
    }


}
