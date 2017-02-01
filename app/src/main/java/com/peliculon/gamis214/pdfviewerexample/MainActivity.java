package com.peliculon.gamis214.pdfviewerexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.asset.CopyAsset;
import es.voghdev.pdfviewpager.library.asset.CopyAssetThreadImpl;

public class MainActivity extends AppCompatActivity {

    private Button btnViewer,button_viewPager;
    private String encodeFileToBase64Binary;
    private StringBuilder textBase64;
    private PDFViewPager pdfViewPager;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnViewer = (Button) findViewById(R.id.button);
        button_viewPager = (Button) findViewById(R.id.button_viewPager);

        verifyStoragePermissions(this);

        btnViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //getPDF();
                    //saveFileInDirectory();
                    //getPDF2();
                    getPDF3();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        button_viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getPDF4();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void getPDF4() throws Exception{
        //-->GETTING THE JSON WITH BASE64 PDF
        JSONObject jsonObject = new JSONObject(jsonData.JsonResponse);
        String base64Info = jsonObject.getString("jsonData");
        //-->DECODE THE (BASE64 - STRING) TO ARRAY OF BYTES[]
        byte[] pdfAsBytes = Base64.decode(base64Info, Base64.DEFAULT);

        //--> **** GET THE ACCESS TO MEMORY AND CREATE FILE AND DIRECTORY **** <---///
        Storage storage;
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(this);
        }

        if(!storage.isDirectoryExists("PDF_READER")){
            storage.createDirectory("PDF_READER");
        }

        if (!storage.isFileExist("PDF_READER","SP.pdf")){
            storage.createFile("PDF_READER","SP.pdf",pdfAsBytes);
        }
        //--> **************************************************************** <---///

        //--> GET THE FILE AND SHOW IN SOME APP TO SHOW PDF
        pdfViewPager = new PDFViewPager(this, Environment.getExternalStorageDirectory() + "/PDF_READER/SP.pdf");
        //pdfViewPager.
        //--> ********************************************* <--///
    }

    private void getPDF3() throws Exception{
        //-->GETTING THE JSON WITH BASE64 PDF
        JSONObject jsonObject = new JSONObject(jsonData.JsonResponse);
        String base64Info = jsonObject.getString("jsonData");
        //-->DECODE THE (BASE64 - STRING) TO ARRAY OF BYTES[]
        byte[] pdfAsBytes = Base64.decode(base64Info, Base64.DEFAULT);

        //--> **** GET THE ACCESS TO MEMORY AND CREATE FILE AND DIRECTORY **** <---///
        Storage storage;
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(this);
        }

        if(!storage.isDirectoryExists("PDF_READER")){
            storage.createDirectory("PDF_READER");
        }

        if (!storage.isFileExist("PDF_READER","SP.pdf")){
            storage.createFile("PDF_READER","SP.pdf",pdfAsBytes);
        }
        //--> **************************************************************** <---///

        //--> GET THE FILE AND SHOW IN SOME APP TO SHOW PDF
        File file = new File(Environment.getExternalStorageDirectory() + "/PDF_READER/SP.pdf");
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
        //--> ********************************************* <--///

    }

    private void getPDF2() throws IOException{
        File dir = Environment.getExternalStorageDirectory();
        File myDirToSave = new File(dir,"Download/base64Test.txt");

        textBase64 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(myDirToSave));
            String line;

            while ((line = br.readLine()) != null) {
                textBase64.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        byte[] pdfAsBytes = Base64.decode(textBase64.toString(), Base64.DEFAULT);

        Storage storage;
        storage = SimpleStorage.getExternalStorage();
        storage.createDirectory("PDF_READER");
        storage.createFile("PDF_READER","test2.pdf",pdfAsBytes);

        File file = new File(Environment.getExternalStorageDirectory() + "/PDF_READER/test2.pdf");
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);

    }

    private void getPDF() throws IOException{

        File dir = Environment.getExternalStorageDirectory();
        File myDirToSave = new File(dir,"Download/example.pdf");
        //encodeFileToBase64Binary = encodeFileToBase64(myDirToSave);

        Storage storage;
        storage = SimpleStorage.getExternalStorage();
        storage.createDirectory("PDF_READER");
        storage.createFile("PDF_READER","test.pdf",loadFile(myDirToSave));

        //String bytesPDF = storage.readTextFile("PDF_READER", "test.pdf");
        //byte[] pdfAsBytes = Base64.decode(bytesPDF, Base64.DEFAULT);

        File file = new File(Environment.getExternalStorageDirectory() + "/PDF_READER/test.pdf");
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);

    }

    private void saveFileInDirectory() throws Exception {

        /*File folder = new File(Environment.getExternalStorageDirectory() + "/Download/PDF_TEST");
        if(!folder.exists())
        {
            folder.mkdirs();
            //pdffile.createNewFile();
        }
        byte[] pdfAsBytes = Base64.decode(encodeFileToBase64Binary, Base64.DEFAULT);
        File filePDF = new File(folder,"test.pdf");
        filePDF.createNewFile();*/

        /*File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/example.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);*/

    }

    private static String encodeFileToBase64(File fileName) throws IOException{
        byte[] bytes = loadFile(fileName);
        String encodedString = Base64.encodeToString(bytes,Base64.DEFAULT);
        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }
}
