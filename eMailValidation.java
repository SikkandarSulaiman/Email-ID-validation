
//Reference: https://en.wikipedia.org/wiki/Email_address

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class email{  
    
        static boolean fail;    /*flag to acknowledge when given string
                                  doesn't satisfy conditions for a mail id*/ 
        String allowInLocal = "!#$%&'*+-/=?^_`{|}~.";
                                /*allowable special characters in local part 
                                  of mail id outside double quotes*/
    
email(String in){
            String[] split;
            String local;
            String domain;
    
    //check for occurence of double quotes
    if(count(in,'"')!=0){
    int fq = in.indexOf("\"");
    int lq = in.lastIndexOf("\"");
    
    //There should be atleast 2 double quotes (opening & closing)
    //Because a single " is not allowed in a mail id
    if(fq==lq){
        fail = true;
        return;
       }
    
    //'@' should not occur before the opening double quotes
    //which means there may be more than 1 '@' or there is presence
    //of quotes in domain part of mail id which is not allowed
    if((in.substring(0,in.indexOf("\""))).contains("@")==true){
        fail=true;
        return;
    }
    
    //Remove the quoted substring since anything can be allowed inside quotes
    //For example,
    //mymail"  ();@:.my"id@mail.com becomes mymailid@mail.com
    String str_quoted = in.substring(fq+1,lq);
    in = in.substring(0,fq)+in.substring(lq+1,in.length());
    }
    
    //Now validate only the remaining string after removing quoted part
    //say, mymailid.com
    
    //There should not be a dot follwed by another dot
    if(in.contains("..")==true){
        fail = true;
        return;
    }
    
    //There should be only one occurence of '@'
    if(count(in,'@')!=1){
        fail = true;
        return;
       }
    
    //Split the mail id into local(before '@') and domain(after '@') parts
    try{
        split = in.split("@");
        local = split[0];
        domain = split[1];
    }
    //check for condition, neither domain part
    catch(ArrayIndexOutOfBoundsException e){
        fail = true;
        return;
    }
    //nor local part should be empty
    if(local.isEmpty()==true){
        fail = true;
        return;
    }
    
    //Local part of mail id should not start or end with a dot
    if(local.startsWith(".")==true || local.endsWith(".")==true){
        fail = true;
        return;
    }
    //Domain part of mail id should not start or end with a dot or hyphen
    if(domain.startsWith(".") || domain.endsWith(".") || domain.startsWith("-") || domain.endsWith("-")){
        fail = true;
        return;
    }
    
    
    //Local part can contain only alphanumeric characters and the special
    //characters mentioned in the string 'allowInLocal'
    for(char c:local.toCharArray()){
        if(c>='a' && c<='z');
        else if(c>='A' && c<='Z');
        else if(c>='0' && c<='9');
        else if(allowInLocal.indexOf(c)!=-1);
        else{
            fail = true;
            return;
        }
    }
    //Domain part can be an IP address enclosed within square braces
    if(domain.charAt(0)=='[' && domain.charAt(domain.length()-1)==']'){
        if(ip(domain.substring(1,domain.length()-1))==false){
            fail = true;
            return;
    }}
    //Or else domain part can also contain only alphanumeric characters
    //but no special characters except dot and hyphen
    else{
    for(char c:domain.toCharArray()){
        if(c>='a' && c<='z');
        else if(c>='A' && c<='Z');
        else if(c>='0' && c<='9');
        else if(c=='.' || c=='-');
        else{
            fail = true;
            return;
        }
    }}
    
    //If all the above conditions are satisfied, the string is a valid mail id
    fail = false;
}



public static void main(String[] arg){
    
JTextField txt = new JTextField();
JFrame F = new JFrame("E-mail ID validation");
JButton b = new JButton("Validate");
JLabel l = new JLabel();
JLabel head = new JLabel("Enter your mail id below:");

b.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String id = txt.getText();
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {}
        if(id.isEmpty()==true){
            l.setForeground(Color.blue);
            l.setText("Enter a mail id in the textbox and retry");}
        else{
        email f=new email(id);
        if(fail==true)
            l.setForeground(Color.red);
        else if(fail==false)
            l.setForeground(Color.GREEN);
        l.setText(f.validity());
    }}
});

head.setBounds(10,30,300,35);
txt.setBounds(10,70,300,35);
b.setBounds(10,110,300,35);
l.setBounds(10,150,250,35);
F.add(txt);
F.add(b);
F.add(l);
F.add(head);
F.setSize(340,350);
F.setResizable(false);
F.setLayout(null);
F.setVisible(true);
F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}



//Converts the boolean flag 'fail' to string for printing purpose
String validity(){
    if(fail==true)
        return "Not valid";
    else
        return "Valid";
}



//Program to count number of occurences of a character in a given string
int count(String str, char c){
    int occ = 0;
    for(char i:str.toCharArray()){
        if(i==c)
        occ++;
       }
    return occ;
}



//Checks whether given string is a valid IPv4 address
boolean ip(String str){
    for(char c:str.toCharArray()){
        if((c>='0' && c<='9') || c=='.');
        else return false;
    }
        if(count(str,'.')!=3)
            return false;
        if(str.startsWith(".") || str.endsWith("."))
            return false;
        String[] arr = str.split("\\.");
        for(int i=0;i<4;i++){
            try{
            if(Integer.parseInt(arr[i])>255)
                return false;
            }
            catch(ArrayIndexOutOfBoundsException e){
                return false;
            }
            }
    return true;
}

//Flaws in this program
/*
1. This program works fine for mail id with only one pair of double quotes
    For example '  mymailid"{)}();:  !we!"@gmail.com  '   is ok.
    In case of  '  mymailid"()()" ;1: "abc"@gmail.com '   is actually not valid
        because the substring   ;1:   is out of quotes but this program
        considers   ()()" ;1: "abc    as one substring inside one pair of qoutes
        and validates it as true.
2. Domain part can contain any version of IP address but this program allows
    only IPv4 address.
*/

}
