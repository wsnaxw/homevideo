package com.martiiin.hv.config;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class FileJSON {

    private String filePath;

    private JSONObject json;

    public FileJSON (String filePath){
        this.filePath = filePath;
    }

    public void read(){
        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream ois  ;
        String jsonStr;
        try {
            ois = new BufferedInputStream(new FileInputStream(file));
            byte[]tt=new byte[ois.available()];
            ois.read(tt, 0, tt.length);
            jsonStr =new String(tt,"UTF-8");
            JSONObject json = JSONObject.parseObject(jsonStr);
            this.json = json;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void writeJSON(JSONObject jsonObject) {

        BufferedWriter out = null;


        try {
            File file = new File(filePath);
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false)));
            String asd = jsonObject.toJSONString();
            out.write(asd);
            out.flush();
            out.close();

        }catch (Exception e){

        }


    }




    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
