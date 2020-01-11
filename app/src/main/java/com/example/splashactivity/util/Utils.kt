package com.example.splashactivity.util

import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import com.example.splashactivity.model.home.HomeModel
//import com.example.splashactivity.view.dialog.SpinnerItem
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class Utils {
    private var patternNormalizer: Pattern? = null
    private var patternD: Pattern? = null
    private var patternValidPassword: Pattern? = null
    private var decimalFormat: DecimalFormat? = null
    private var priceFormat: DecimalFormat? = null
    private var dateFormat: SimpleDateFormat? = null
    private var hourFormat: SimpleDateFormat? = null
    private var fullDateFormat: SimpleDateFormat? = null
    private var displayFormat: SimpleDateFormat? = null
    private var percentage //"#.##
            : DecimalFormat? = null
    private var percentageV2 //"#.##
            : DecimalFormat? = null
    @Synchronized
    fun instance(): Utils? {
        if (Utils.mInstance == null) {
            Utils.mInstance = com.example.splashactivity.util.Utils()
        }
        return Utils.mInstance
    }

    constructor(){
        patternNormalizer = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        patternD = Pattern.compile("[đ]")
        patternValidPassword =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,200}$")
        // format
        // format
        decimalFormat = DecimalFormat("###,###,###")
        priceFormat = DecimalFormat("###,###.#")
        percentage = DecimalFormat("#.##")
        percentageV2 = DecimalFormat("#.00")

        dateFormat = SimpleDateFormat("dd/MM/yyyy")
        //dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        displayFormat = SimpleDateFormat("yyyy-MM-dd, HH:mm")
        fullDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    }

     companion object  {
         lateinit var mInstance: Utils
    }

    fun fromHtml(source: String?): Spanned? {
        if (TextUtils.isEmpty(source))

            return SpannableString("")
         else
            return  Html.fromHtml(source)

    }

    fun convertUnicodeToAscii(input: String): String? {
        var input = input
        if (TextUtils.isEmpty(input)) {
            return input
        }
        input = input.toLowerCase()
        // replace dd
        input = patternD!!.matcher(input).replaceAll("d")
        val nfdNormalizedString =
            Normalizer.normalize(input, Normalizer.Form.NFD)
        return patternNormalizer!!.matcher(nfdNormalizedString).replaceAll("")
    }

//    fun convertCityToSpinner(homes: ArrayList<HomeModel>?): ArrayList<SpinnerItem>? {
//        val spinnerItems: ArrayList<SpinnerItem> = ArrayList<SpinnerItem>()
//        if (homes != null) {
//            val size = homes.size
//            for (i in 0 until size) {
//                val item = SpinnerItem()
//                item.setPosition(i)
//                item.setLabel(homes[i].title)
//                spinnerItems.add(item)
//            }
//        }
//        return spinnerItems
//    }

}