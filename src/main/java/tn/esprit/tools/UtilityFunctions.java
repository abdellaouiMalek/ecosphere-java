/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tools;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hello
 */
public class UtilityFunctions {
    //if you have an array attribute in an sql table and you wonna insert in it a value 
    public static String getJsonArray(List l){
        StringBuilder sb = new StringBuilder("[");
                    for (int i = 0; i < l.size(); i++) {
                    sb.append("\"").append(l.get(i)).append("\"");
                     if (i != l.size() - 1) {
                     sb.append(",");
                    }
                     }
                    sb.append("]");
                    String jsonStringArray = sb.toString();
                    return jsonStringArray;
    }
    
    public static  String saveImage(File image){
        String src = image.getPath();
        String fileName=String.valueOf((new Date()).getTime()*1000)+(Math.random()*1000+1);
        String fileType=getExtention(image.getName());
        String dest="C:\\xampp\\htdocs\\img\\"+fileName+"."+fileType;
        try {
            Path tmp =Files.move(Paths.get(src),Paths.get(dest));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(fileName+"."+fileType);
        return fileName+"."+fileType;
        
}
    public static String getExtention(String fileName){
        System.out.println(fileName);
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(fileType);
        return fileType;
    } 
    
    public static boolean verifyEmailAddress(String email){
        String email_reg =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    Pattern email_pat = Pattern.compile(email_reg);

        Matcher matcher = email_pat.matcher(email);
        return matcher.matches();
    }
        
    
    public static boolean verifyPassword(String password){
        if(password==null||password=="")
           return false;
        String regex =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";  
        //Compiler regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
        
    }
    public static boolean verifyName(String name){
        
     if (name.matches("[a-zA-Z]{3,}")) {
    return true;
        }else {
    return false;
}
    }
    public static boolean verifyTitle(String title){
        
     if (title.length()>6) {
    return true;
        }else {
    return false;
}
    }
    public static boolean verifyDescription(String desc){
        
     if (desc.length() >6) 
    return true;
        else {
    return false;
}
    }
    public static boolean verifyNumber(String number){
        
     if (number.matches(".*\\d+.*")) {
return true;
     } else {
    return false;
            }
}
    
}
