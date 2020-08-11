package org.peng.utils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @authoer sunpeng
 * @create 2020-08-11
 */
public class ZipUtil {

    /**
     *
     * @param file
     * @return void
     * @author SunPeng
     * @description 根据路径随机复制压缩文件里面三个压缩项
     * @date 2020/8/11 16:46
     */
    public static void readZip(String file) {
        File fi = new File(file);
        if (fi.isDirectory()) {
            File[] files = fi.listFiles();
            for (File f : files) {
                String path = f.getPath();
                readZip(path);
            }
        } else {
            try {
                List<ZipEntry> list = new ArrayList<>();
                ZipFile zf = new ZipFile(file);
                ZipEntry ze = null;
                for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                    ze = (ZipEntry) entries.nextElement();
                    String fileName = ze.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("/")+1);
                    if(!ze.isDirectory()&&!ze.getName().contains("png")&&fileName.indexOf(".")!=0&&!ze.getName().contains("jpg")){
                        list.add(ze);
                    }

                }
//                for(ZipEntry zipEntry:list){
//                    System.out.println(zipEntry.getName());
//                }
                Random random = new Random();
                int size = list.size() > 3 ? 3 : list.size();
                Set<Integer> index = new LinkedHashSet<>();
                while (index.size() < size) {
                    index.add(random.nextInt(size));
                }
                for (Integer num : index) {
                    ZipEntry entry = list.get(num.intValue());
                    String name = entry.getName();
//                    name = name.replaceAll("/", "\\\\");
                    String line = null;
                    BufferedWriter writer = null;
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
//                        System.out.println(name);
//                        System.out.println("*********");
                        String path = "/opt/repo3"+File.separator+name;
//                        System.out.println(path);
//                        System.out.println("*********");
                        String subPath = path.substring(0,path.lastIndexOf("/"));
//                        System.out.println(subPath);
//                        System.out.println("*********");
                        File file1 = new File(subPath);
                        if(!file1.exists()){
                            file1.mkdirs();
                        }
                        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
                        while ((line = in.readLine()) != null) {
                            writer.write(line + "\n");
                        }
                        writer.flush();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        readZip(args[0]);
    }
}
