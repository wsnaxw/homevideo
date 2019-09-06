package com.martiiin.hv.controller;


import com.alibaba.fastjson.JSONObject;
import com.martiiin.hv.config.FileJSON;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HVController {


    @Autowired
    FileJSON fileJSON;

    @ResponseBody
    @RequestMapping("video/play/{id}")
    public void play(HttpServletResponse response,@PathVariable("id") String id) throws Exception {
        fileJSON.read();
        JSONObject json = fileJSON.getJson();
        Map map = json.getInnerMap();
        String filePath = (String) map.get(id);
        File file = new File(filePath);


        InputStream in = new FileInputStream(file);

        response.addHeader("Content-Type","video/mp4;charset=utf-8");
        IOUtils.copy(in,response.getOutputStream());
        response.flushBuffer();

    }



    @RequestMapping("index")
    public String index(Model model) {
        fileJSON.read();
        JSONObject json = fileJSON.getJson();
        Map map = json.getInnerMap();
        List list = new ArrayList();
        System.out.println(map.keySet());
        map.forEach((k,v)->{
            list.add(k);
        });
        model.addAttribute("list",list);
        return "video/index.html";
    }


    @RequestMapping("video/{id}")
    public String video(Model model, @PathVariable("id") String id, HttpServletResponse response) throws Exception {



        model.addAttribute("name",id);
        return "video/VideoDemo.html";
    }



    @ResponseBody
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
