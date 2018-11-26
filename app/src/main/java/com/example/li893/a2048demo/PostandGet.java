package com.example.li893.a2048demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


class GetRun {
    private ArrayList<String> rankList = new ArrayList<>();
    public String run() {
        try {
            URL url = new URL("http://lyj.corechan.cn/2048/rank");
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject;
            ArrayList<JSONObject> sortJsonArray = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                sortJsonArray.add(jsonObject);
            }
            Collections.sort(sortJsonArray, new SortByGrade());
            for (int i = 0; i < jsonArray.length() && i < 10; i++) {
                jsonObject = sortJsonArray.get(i);
                rankList.add(String.format("%-4s", i + 1) + " " + String.format("%-20s", jsonObject.getString("name")) + jsonObject.getInt("grade") + "\n");
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rankList.toString();
    }
}

class PostRun {
    private String name;
    private int grade;
    private String setJson;

    public PostRun(String name, int grade) {
        this.name = name;
        this.grade = grade;
        this.setJson = "{\"grade\":" + grade + ",\"name\": \"" + name + "\"}";
    }

    public void run() {

        try {
            URL url = new URL("http://lyj.corechan.cn/2048/rank");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("encoding", "UTF-8");//添加请求属性
            conn.setDoInput(true);//允许输入
            conn.setDoOutput(true);//允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            out.write(setJson);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


class SortByGrade implements Comparator<JSONObject> {
    public int compare(JSONObject o1, JSONObject o2) {
        try {
            if (o1.getInt("grade") < o2.getInt("grade"))
                return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}