package com.smartperty.smartperty.landlord.menu.finance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_finance.view.*


class FinanceFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_finance, container, false)

        root.recyclerView_finance_income.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = FinanceAdapter(requireActivity(), root,
                GlobalVariables.finance.income
            )
        }

        root.recyclerView_finance_outcome.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = FinanceAdapter(requireActivity(), root,
                GlobalVariables.finance.outcome
            )
        }

        val incomeValue = GlobalVariables.finance.income[GlobalVariables.finance.income.lastIndex].value
        val outcomeValue = GlobalVariables.finance.outcome[GlobalVariables.finance.outcome.lastIndex].value
        val total = incomeValue - outcomeValue
        root.textView_finance_total.text = total.toString()

        return root
    }

}