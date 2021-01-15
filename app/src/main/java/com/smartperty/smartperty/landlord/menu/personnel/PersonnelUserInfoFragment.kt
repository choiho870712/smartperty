package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_add.view.spinner
import kotlinx.android.synthetic.main.fragment_personnel_user_info.view.*

class PersonnelUserInfoFragment : Fragment() {

    private lateinit var root: View
    private var imageBuffer: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_user_info, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle("編輯個人資訊")

        if (GlobalVariables.personnelUserInfoUsage != "read") {
            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.button_submit -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("是否送出個人資料？")

                        builder.setPositiveButton("是") { _, _ ->

                            if (imageBuffer != null)
                                GlobalVariables.personnel.icon = imageBuffer
                            GlobalVariables.personnel.name = root.textView_name.text.toString()
                            GlobalVariables.personnel.email = root.textView_email.text.toString()
                            GlobalVariables.personnel.cellPhone = root.textView_phone.text.toString()
                            GlobalVariables.personnel.sex = root.button_select_sex.text.toString()
                            GlobalVariables.personnel.annual_income = root.button_select_income.text.toString()
                            GlobalVariables.personnel.industry = root.button_select_industry.text.toString()
                            GlobalVariables.personnel.profession = root.button_select_profession.text.toString()


                            when(GlobalVariables.personnelUserInfoUsage) {
                                "create" -> {
//                                    if (GlobalVariables.personnel.auth == "tenant")
//                                        GlobalVariables.estate.tenant = GlobalVariables.personnel
//                                    Utils.createAccount(
//                                        GlobalVariables.personnel,GlobalVariables.estate.objectId)
                                    Utils.createAccount(
                                        GlobalVariables.personnel,"nil")
                                }
                                "update" -> {
                                    Utils.updateAccount(GlobalVariables.personnel)
                                }
                                else -> {

                                }
                            }

                            GlobalVariables.personnelUserInfoUsage = "read"
                            root.findNavController().navigateUp()
                        }

                        // create dialog and show it
                        requireActivity().runOnUiThread{
                            val dialog = builder.create()
                            dialog.show()
                        }

                        true
                    }
                    else -> false
                }
            }
        }

        if (GlobalVariables.personnel.icon != null)
            root.image_userIcon.setImageBitmap(GlobalVariables.personnel.icon)
        root.textView_name.setText(GlobalVariables.personnel.name)
        root.textView_email.setText(GlobalVariables.personnel.email)
        root.textView_phone.setText(GlobalVariables.personnel.cellPhone)
        if (GlobalVariables.personnel.profession!= "nil")
            root.button_select_profession.setText(GlobalVariables.personnel.profession)
        if (GlobalVariables.personnel.sex!= "nil")
            root.button_select_sex.setText(GlobalVariables.personnel.sex)
        if (GlobalVariables.personnel.industry!= "nil")
            root.button_select_industry.setText(GlobalVariables.personnel.industry)
        if (GlobalVariables.personnel.annual_income!= "nil")
            root.button_select_income.setText(GlobalVariables.personnel.annual_income)

        //createSpinner()

        root.button_select_image.setOnClickListener {
            pickImageFromGallery()
        }

        root.button_select_industry.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("批發/零售/服務")
            arrayAdapter.add("製造/營造業")
            arrayAdapter.add("金融保險/租賃")
            arrayAdapter.add("政府機構/公營事業")
            arrayAdapter.add("軍警消")
            arrayAdapter.add("教育機構")
            arrayAdapter.add("醫療機構")
            arrayAdapter.add("運輸/倉儲")
            arrayAdapter.add("汽車買賣/直傳銷")
            arrayAdapter.add("律師/會計/記帳/公證/代書/估價服務")
            arrayAdapter.add("珠寶銀樓業/拍賣行/不動產相關行業")
            arrayAdapter.add("汽柴油批發零售業")
            arrayAdapter.add("汽柴油批發零售業")
            arrayAdapter.add("國防工業")
            arrayAdapter.add("殺傷性武器工具機製造業")
            arrayAdapter.add("財團或社團法人/公會/宗教")
            arrayAdapter.add("當舖、地下金融、虛擬貨幣、博弈業、八大行業")
            arrayAdapter.add("第三方/電子支付機構、金流公司")
            arrayAdapter.add("其他")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_select_industry.text = strName
            }
            builderSingle.show()
        }

        root.button_select_sex.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("男")
            arrayAdapter.add("女")
            arrayAdapter.add("不公開")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_select_sex.text = strName
            }
            builderSingle.show()
        }

        root.button_select_income.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("NT10萬以下")
            arrayAdapter.add("NT10萬~20萬")
            arrayAdapter.add("NT20萬~40萬")
            arrayAdapter.add("NT40萬~60萬")
            arrayAdapter.add("NT60萬~1,00萬")
            arrayAdapter.add("NT1,00萬~1,20萬")
            arrayAdapter.add("NT1,20萬~1,50萬")
            arrayAdapter.add("NT1,50萬以上")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_select_income.text = strName
            }
            builderSingle.show()
        }

        root.button_select_profession.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("一般職員")
            arrayAdapter.add("中高階主管")
            arrayAdapter.add("獎佣金制人員")
            arrayAdapter.add("獎佣金制主管")
            arrayAdapter.add("勞力服務者")
            arrayAdapter.add("專業人員/四師")
            arrayAdapter.add("企業主/家族企業")
            arrayAdapter.add("學生")
            arrayAdapter.add("約聘僱人員")
            arrayAdapter.add("退休/家管")
            arrayAdapter.add("其他")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_select_profession.text = strName
            }
            builderSingle.show()
        }

        return root
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            GlobalVariables.personnel.auth = "landlord"
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    private fun createSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.personnel_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            root.spinner.adapter = adapter
        }

        root.spinner.onItemSelectedListener = SpinnerActivity()
    }


    // the code of getting image /////////////////////////////////

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            root.image_userIcon.setImageURI(data?.data)
            imageBuffer = root.image_userIcon.drawable.toBitmap()
        }
    }
}