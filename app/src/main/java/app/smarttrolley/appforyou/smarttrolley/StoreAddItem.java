package app.smarttrolley.appforyou.smarttrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import utils.DatabaseHandler;
import utils.ProductDetail;

public class StoreAddItem extends AppCompatActivity {

    private IntentIntegrator qrScan;
    EditText productIDEditText ,productNameEditText,productPriceEditText,productWeightEditText,productQuantityEditText,productExpiryEditText,productBrandEditText,productCategoryEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        qrScan = new IntentIntegrator(StoreAddItem.this);

        productIDEditText =(EditText)findViewById(R.id.storeAddItem_productId_edittext);
        productNameEditText =(EditText)findViewById(R.id.storeAddItem_productname_edittext);
        productCategoryEditText =(EditText)findViewById(R.id.storeAddItem_productcategory_edittext);
        productPriceEditText =(EditText)findViewById(R.id.storeAddItem_productprice_edittext);
        productWeightEditText =(EditText)findViewById(R.id.storeAddItem_productweight_edittext);
        productBrandEditText =(EditText)findViewById(R.id.storeAddItem_productbrand_edittext);


    }





    public void openQRScanner(View view){

        qrScan.initiateScan();

    }

    public void addItemToStoreListing(View view){

        ProductDetail productDetail = new ProductDetail();

        String products = productIDEditText.getText().toString().trim().toUpperCase();
        if(products.length() >2){
            productDetail.setProductID(products);
        }


         products = productNameEditText.getText().toString().trim().toUpperCase();
        if(products.length() >2){
            productDetail.setProductName(products);
        }else{
            Toast.makeText(this, "Product Name missing", Toast.LENGTH_SHORT).show();
            return;
        }


         products = productPriceEditText.getText().toString().trim().toUpperCase();
        if(!products.isEmpty()){
            productDetail.setProductPrice(Integer.valueOf(products));
        }else{

            Toast.makeText(this, "PRoduct Price Missing", Toast.LENGTH_SHORT).show();
            return;
        }


         products = productWeightEditText.getText().toString().trim().toUpperCase();
        if(!products.isEmpty()){
            productDetail.setProductWeight(Integer.valueOf(products));
        }else{
            return;
        }





        products = productBrandEditText.getText().toString().trim().toUpperCase();
        if(products.length() >2){
            productDetail.setProductBrand(products+"");
        }else
        {
            Toast.makeText(this, "Product Brand Missing", Toast.LENGTH_SHORT).show();
            return;
        }


         products = productCategoryEditText.getText().toString().trim().trim().toUpperCase();
        if(products.length() >2){
            productDetail.setProductCategory(products);
        }

        DatabaseHandler databasehandler = new DatabaseHandler();
        databasehandler.insertProduct(productDetail);

        Toast.makeText(this, "Product added to store", Toast.LENGTH_SHORT).show();

        productIDEditText.setText("");
        productNameEditText.setText("");
        productCategoryEditText.setText("");
        productPriceEditText.setText("");
        productWeightEditText.setText("");
        productBrandEditText.setText("");



    }



    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    String productID = result.getContents();
                    productIDEditText.setText(productID);



                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
