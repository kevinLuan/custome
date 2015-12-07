package com.kevin.zuyu.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MysqlManager {
  @Value("#{customeConfig['mysql_dump_command']}")
  private String dump_command;
  @Value("#{customeConfig['back_dir']}")
  public String back_dir;

  private String getBackPath() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH");
    String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
    String path = back_dir + File.separator + date + File.separator;
    File file = new File(path);
    if (!file.exists()) {
      System.out.println("创建目录 path:" + path);
      file.mkdirs();
    }
    return path + dateFormat.format(new Date()) + ".sql";
  }

  public void backDB() throws IOException {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    System.out.println("[" + date + "]dump_command");
    Runtime rt = Runtime.getRuntime();
    Process child = rt.exec(dump_command);
    InputStream in = child.getInputStream();
    InputStreamReader streamReader = new InputStreamReader(in, "utf8");

    FileOutputStream fos = new FileOutputStream(new File(getBackPath()));
    OutputStreamWriter writer = new OutputStreamWriter(fos, "utf8");
    String inStr;
    BufferedReader br = new BufferedReader(streamReader);
    while ((inStr = br.readLine()) != null) {
      writer.write(inStr + "\n\r");
    }
    writer.flush();
    in.close();
    br.close();
    writer.close();
    fos.close();
  }
}
