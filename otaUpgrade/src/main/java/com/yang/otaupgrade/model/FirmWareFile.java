package com.yang.otaupgrade.model;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * FirmWareFile
 */

public class FirmWareFile {

    private int code;
    private String path;

    private Uri pathUri;

    private ContentResolver contentResolver;

    private ArrayList<Partition> list = new ArrayList<>();

    public FirmWareFile(String filePath,boolean isQuick) {
        this.path = filePath;
        analyzeFile(filePath,isQuick);
    }

    public FirmWareFile(ContentResolver contentResolver,Uri uri, boolean isQuick) {
        this.pathUri = uri;
        this.path = uri.getPath();
        this.contentResolver = contentResolver;
        analyzeFile(uri,isQuick);
    }

    private void analyzeFile(Uri path,boolean isQuick) {

        if (contentResolver == null) {
            code = 102;
            return;
        }

        int size = 0;
        StringBuffer result = new StringBuffer();
        int flag = 0;
        String address = "";


//        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(contentResolver.openInputStream(path)));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                readline = readline.trim();
                size = Integer.parseInt(readline.substring(1,3),16);
                if(readline.substring(7,9).equals("04")){
                    if(result.length()>0){
                        list.add(new Partition(address,result.toString(),isQuick));
                    }
                    address = readline.substring(9,13);
                    flag = 0;
                    result = new StringBuffer("");
                    continue;
                }
                if(readline.substring(7,9).equals("05") || readline.substring(7,9).equals("01")){
                    list.add(new Partition(address,result.toString(),isQuick));
                    break;
                }

                if(flag == 0){
                    flag = 1;
                    address = address + readline.substring(3,7);
                }

                result.append(readline.substring(9,9+size*2));

                //Log.e("result=",result.toString());
            }
        } catch (FileNotFoundException e) {
            code = 100;
        } catch (IOException e) {
            code = 101;
        }

        code = 200;
    }

    private void analyzeFile(String path,boolean isQuick) {

        int size = 0;
        StringBuffer result = new StringBuffer();
        int flag = 0;
        String address = "";

        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                readline = readline.trim();
                size = Integer.parseInt(readline.substring(1,3),16);
                if(readline.substring(7,9).equals("04")){
                    if(result.length()>0){
                        list.add(new Partition(address,result.toString(),isQuick));
                    }
                    address = readline.substring(9,13);
                    flag = 0;
                    result = new StringBuffer("");
                    continue;
                }
                if(readline.substring(7,9).equals("05") || readline.substring(7,9).equals("01")){
                    list.add(new Partition(address,result.toString(),isQuick));
                    break;
                }

                if(flag == 0){
                    flag = 1;
                    address = address + readline.substring(3,7);
                }

                result.append(readline.substring(9,9+size*2));

                //Log.e("result=",result.toString());
            }
        } catch (FileNotFoundException e) {
            code = 100;
        } catch (IOException e) {
            code = 101;
        }

        code = 200;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<Partition> getList() {
        return list;
    }

    public long getLength(){
        long size = 0;
        for (Partition partition : list){
            size += partition.getPartitionLength();
        }

        return size;
    }

    public String getPath() {
        return path;
    }
}
