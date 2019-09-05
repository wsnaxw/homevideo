package com.martiiin.hv.controller;


import com.alibaba.fastjson.JSONObject;
import com.martiiin.hv.config.FileJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HVController {


    @Autowired
    FileJSON fileJSON;


    @RequestMapping("getVideo")
    public String disk(){
        System.out.println(fileJSON.getJson());

        JSONObject json = fileJSON.getJson();

        if (json!=null){
            return json.toJSONString();
        }else {
            return "nothing";
        }




    }

    @RequestMapping("scanAll")
    public List getVideoFile(){
        JSONObject jsonObject = new JSONObject();
        List<String> VideoFile = new ArrayList<>();
        for(File disk:File.listRoots()){

            for (File files:disk.listFiles()){
                getAllFile(files.getAbsolutePath(),jsonObject,VideoFile);
            }
        }
        fileJSON.writeJSON(jsonObject);
        return VideoFile;
    }




    public void getAllFile(String filePath,JSONObject jsonObject,List<String> list) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        getAllFile(file2.getAbsolutePath(), jsonObject, list);
                    } else {

                        if (file2.getName().endsWith(".mp4")) {
                            jsonObject.put(file2.getName(), file2.getAbsolutePath());
                            list.add(file2.getAbsolutePath());
                        }
                    }
                }
            } else {
                System.out.println("文件不存在!");
            }
        }

    }







}
