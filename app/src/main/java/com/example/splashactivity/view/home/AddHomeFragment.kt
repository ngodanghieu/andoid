package layout

import android.R.attr

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HolderHomeLis
import com.example.splashactivity.view.Adapter.ListHomeAdapter
import com.example.splashactivity.view.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException
import java.io.InputStream

import android.Manifest
import android.app.Activity
import android.os.Build.*

import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.splashactivity.model.home.HomeRequest
import com.example.splashactivity.util.Constant
import kotlinx.android.synthetic.main.activity_main.*


class AddHomeFragment : Fragment(),View.OnClickListener {
    lateinit var mActivity : HomeActivity
    lateinit var recyclerView : RecyclerView
    lateinit var list : ArrayList<HolderHomeLis>
    lateinit var list_home_view : View
    lateinit var list_home_label : TextView
    lateinit var list_home_value : TextView
    lateinit var home_add_name : EditText
    lateinit var list_home_price : AutoCompleteTextView
    lateinit var list_home_descstrion : EditText
    lateinit var iamge_view : View
    lateinit var listimage : View
    lateinit var ab_done : AppCompatImageView
     var idHome :Long ? = -1
    var realpath :String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_home_recyclerview)
        list_home_view = view.findViewById(R.id.list_home_view)
        list_home_label = view.findViewById(R.id.list_home_label)
        list_home_value = view.findViewById(R.id.list_home_value)
        home_add_name = view.findViewById(R.id.home_add_name)
        list_home_price = view.findViewById(R.id.list_home_price)
        list_home_descstrion = view.findViewById(R.id.list_home_descstrion)
        iamge_view = view.findViewById(R.id.iamge_view)
        listimage = view.findViewById(R.id.listimage)
        ab_done = view.findViewById(R.id.ab_done)
        getData()
        setOnClick()
        list_home_view.setOnClickListener({
            hdienORShowList()
        })

        iamge_view.setOnClickListener({
        hdienORShowImage()
        })

        ab_done.setOnClickListener({
            if (this.idHome != -1L){
                var home : HomeRequest = HomeRequest()
                home.id = idHome
                home.content = list_home_descstrion.text.toString()
                home.price = list_home_price.text.toString().toDouble()
                home.imageUrl=" "
                saveHome(home)
            }else{
                Toast.makeText(mActivity,"chu chon nha",Toast.LENGTH_LONG).show()
            }
        })
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): AddHomeFragment {
            return AddHomeFragment()
        }

        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }
    fun getData(){
        val token : String = mActivity.getPref().getString(Constant.AUTHOCATION, "")
        mActivity.service.getDataByUser(token,65)?.enqueue(object : Callback<ResponseData?> {
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Log.d("HOME ADD","k data")
            }
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if(response.isSuccessful && response.body() != null){
                    initSpinnerHome(response.body()!!.content as ArrayList<HolderHomeLis>)
                }
            }
        })
    }

    fun initSpinnerHome(list : ArrayList<HolderHomeLis>){
        val homeListAdapter = ListHomeAdapter(list,mActivity,this)
        recyclerView.layoutManager= GridLayoutManager(mActivity,1)
        recyclerView.adapter=homeListAdapter
    }

    fun drawValueDeafalut(){
        val item : HolderHomeLis = mActivity.itemHomeSelect
        idHome = item.homeId
        home_add_name.setText(item.title)
        list_home_label.setText("ban da chon nha")
        list_home_value.setText(item.title)
        var adapter = ArrayAdapter<String>(
            mActivity, // Context
            android.R.layout.simple_dropdown_item_1line, // Layout
            item.prices // Array
        )
        list_home_price.setAdapter(adapter)
        list_home_price.threshold = 0

        hdienORShowList()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.list_home_view -> {
                if (recyclerView.visibility == View.GONE)
                    recyclerView.visibility = View.GONE
                else
                    recyclerView.visibility = View.VISIBLE
            }
        }
    }

    fun hdienORShowList(){
        if (recyclerView.visibility == View.GONE)
            recyclerView.visibility = View.VISIBLE
        else
            recyclerView.visibility = View.GONE
    }
    fun hdienORShowImage(){
        if (listimage.visibility == View.GONE)
            listimage.visibility = View.VISIBLE
        else
            listimage.visibility = View.GONE
    }

    fun setOnClick(){
        list_home_view.setOnClickListener(this)
    }

    fun onclikimageViewthemhang(v: View?) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 123)
    }

    fun saveHome(home : HomeRequest ){
        mActivity.showDialogLoading()
        mActivity.service.creaateHome(mActivity.userBase!!.token.toString(),home)!!.enqueue(object : Callback<ResponseData?> {
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()?.status == 1){
                        Log.d("CREATE_HOME", "====================OK========================")
                    }else{
                        Log.d("CREATE_HOME", "====================ERROR========================")
                    }
                }
                mActivity.hideDialogLoading()
            }

            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                mActivity.hideDialogLoading()
            }
        })
    }

//    fun selecrImage(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                PackageManager.PERMISSION_DENIED){
//                //permission denied
//                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
//                //show popup to request runtime permission
//                requestPermissions(permissions, PERMISSION_CODE);
//            }
//            else{
//                //permission already granted
//                pickImageFromGallery();
//            }
//        }
//        else{
//            //system OS is < Marshmallow
//            pickImageFromGallery();
//        }
//    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(mActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            image_view.setImageURI(data?.data)
        }
    }

}