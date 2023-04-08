import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class UyeKayit {
    static String path = "kullanıcılar2.txt"; // Üyelerin olacağı dosyanın ismi
    static File log = new File(path);//dosyamızı oluşturma işlemi
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args) throws IOException//main class
    {
        while(true) {
            System.out.println("***Merhabalar, üye kayıt programımıza hoşgeldiniz***");
            System.out.println("Lütfen yapmak istediğiniz işlemi seçiniz.\n1-Elit üye ekleme\n2-Genel üye ekleme\n3-Mail gönderme\n4-Programı sonlandır");//Kullanıcı seçim menüsü
            int girilenSayi = Integer.parseInt(console.nextLine());//Kullanıcının hangi işlemi yaptıracağını kullanıcdan isteme işlemi
            dosyaOlusturulduMu();//dosyanın var olup olmadığını denetleyip eğer yoksa dosya oluşturan metot
            baslikOlusturulduMu();
            if (girilenSayi == 1) {
                dosyayaEkle(girilenSayi);//elit üye eklemek isteyen kullanıcıyı elit üye ekleme metoduna yollama işlemi
            } else if (girilenSayi == 2) {
                dosyayaEkle(girilenSayi);//genel üye eklemek isteyen kullanıcıyı genel üye ekleme metoduna yollama işlemi
            } else if (girilenSayi == 3) {
                EPostaYolla mail = new EPostaYolla();
                String gonderilecekMail = "";
                ArrayList<String> user = new ArrayList<String>();
                System.out.println("Mail gönderme menümüze hoşgeldiniz.Lütfen kime mail göndermek istediginizi seciniz");
                System.out.println("1-Elit üyelere mail\n2-Genel üyelere mail\n3-Tüm üyelere mail");
                int mailgonderimi = Integer.parseInt(console.nextLine());//kullanıcıdan kime mail göndereceğini alma işlemi
                if (mailgonderimi == 1) {
                    System.out.println("Lütfen elit üyelere göndermek istediğiniz maili giriniz:");
                    gonderilecekMail = console.nextLine();
                    user = kimeGonderilecek(mailgonderimi);//elit üyelere mail göndermek için mail metoduna yollama işlemi
                } else if (mailgonderimi == 2) {
                    System.out.println("Lütfen genel üyelere göndermek istediğiniz maili giriniz:");
                    gonderilecekMail = console.nextLine();
                    user = kimeGonderilecek(mailgonderimi);//genel üyelere mail göndermek için mail metoduna yollama işlemi
                } else if (mailgonderimi == 3) {
                    System.out.println("Lütfen tüm üyelere göndermek istediğiniz maili giriniz:");
                    gonderilecekMail = console.nextLine();
                    user = kimeGonderilecek(mailgonderimi);//tüm üyelere mail göndermek için mail metoduna yollama işlemi
                }
                System.out.println(user);
                for (String temp : user) {
                    System.out.println("Mail "+temp+" adresine gönderildi!");
                    mail.mailGonder(temp, gonderilecekMail);
                }
            }
            else if(girilenSayi==4){
                break;//programı sonlandırma işlemi
            }
        }
    }
    public static ArrayList<String> kimeGonderilecek(int secenek) throws FileNotFoundException//gönderilecek emailin kime gönderilmesi gerektiğini çalıştıran metot
    {
        FileReader reader = new FileReader(path);
        Scanner fileInput = new Scanner(reader);

        ArrayList<String> splitUsers = new ArrayList<String>();

        if (secenek == 1)//mail gönderilecek elit üyeleri ayırma işlemi
        {
            while (fileInput.hasNextLine())
            {

                String line = fileInput.nextLine();
                if (line.equals("#Genel Üyeler:"))
                {
                    return splitUsers;
                }
                else if(!line.equals("#Elit Üyeler:"))
                {
                    String[] usr = line.split("\t");
                    int i = 0;
                    for (String temp : usr)
                    {
                        if (i == 2)
                        {
                            splitUsers.add(temp);
                        }
                        i++;
                    }
                }
            }
        }
        else if (secenek == 2)//mail gönderilecek genel üyeleri ayırma işlemi
        {
            int check = 0;
            while (fileInput.hasNextLine())
            {
                String line = fileInput.nextLine();
                if (line.equals("#Genel Üyeler:"))
                {
                    check = 1;
                }
                if (check == 1)
                {
                    String[] usr = line.split("\t");
                    int i = 0;
                    for (String temp : usr)
                    {
                        if (i == 2)
                        {
                            splitUsers.add(temp);
                        }
                        i++;
                    }
                }
            }
        }
        else if (secenek == 3)//tüm kullanıcıların maillerini ayırma işlemi
        {
            while (fileInput.hasNextLine())
            {
                String line = fileInput.nextLine();
                if (!line.equals("#Elit Üyeler:") && !line.equals("#Genel Üyeler:"))
                {
                    String[] usr = line.split("\t");
                    int i = 0;
                    for (String temp : usr)
                    {
                        if (i == 2)
                        {
                            splitUsers.add(temp);
                        }
                        i++;
                    }
                }
            }
        }
        return splitUsers;
    }

    public static void dosyaOlusturulduMu() throws IOException
    {
        if(!log.exists())
        {
            log.createNewFile();
        }
    }

    public static void baslikOlusturulduMu() throws IOException
    {
        boolean check_elit = false;
        boolean check_genel = false;
        FileReader reader = new FileReader(path);
        Scanner fileInput = new Scanner(reader);

        FileWriter fileWriter = new FileWriter(log, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        while (fileInput.hasNextLine())
        {
            String line = fileInput.nextLine();
            if (line.equals("#Elit Üyeler:"))
            {
                check_elit = true;
            }
            if (line.equals("#Genel Üyeler:"))
            {
                check_genel = true;
            }
        }
        if (!check_elit)
        {
            bufferedWriter.write("#Elit Üyeler:\n");
        }
        if (!check_genel)
        {
            bufferedWriter.write("#Genel Üyeler:\n");
        }
        bufferedWriter.close();
    }

    public static elitUyeler elitUyeBilgi()//Elit üyeler hakkında bilgileri aldığımız metot
    {
        System.out.println("Elit üye ekleme menümüze hoşgeldiniz.Lütfen ekleyeceğiniz üyenin ismini giriniz:");
        String elituyeisim = console.nextLine();
        System.out.println("Lütfen soyismini giriniz:");
        String elituyesoyisim = console.nextLine();
        System.out.println("Lütfen e mailini giriniz:");
        String elituyeemail = console.nextLine();
        elitUyeler elit = new elitUyeler(elituyeisim,elituyesoyisim,elituyeemail);
        return elit;
    }

    public static genelUyeler genelUyeBilgi()//Genel üyeler hakkında bilgileri aldığımız metot
    {
        System.out.println("Genel uye ekleme menümüze hosgeldiniz.Lutfen ekleyeceginiz uyenin ismini giriniz");
        String geneluyeisim = console.nextLine();
        System.out.println("Lutfen soyismini giriniz.");
        String geneluyesoyisim = console.nextLine();
        System.out.println("Lutfen e mailini giriniz.");
        String geneluyeemail = console.nextLine();
        genelUyeler genel = new genelUyeler(geneluyeisim,geneluyesoyisim,geneluyeemail);
        return genel;
    }


    public static void dosyayaEkle(int option) throws IOException//Dosyaya eklenecek elit ve genel üyeleri dosyaya ekleme işlemi
    {
        Scanner sc = new Scanner(new File(path));
        StringBuffer buffer = new StringBuffer();
        FileReader reader = new FileReader(path);
        Scanner fileInput = new Scanner(reader);
        while (fileInput.hasNextLine())
        {
            String line = fileInput.nextLine();
            if (line.equals("#Elit Üyeler:") && option == 1)//Elit üyeleri elit üyeler başlığının altına ekleme işlemi
            {
                while (sc.hasNextLine())
                {
                    buffer.append(sc.nextLine()+System.lineSeparator());
                }
                String fileContents = buffer.toString();
                sc.close();
                elitUyeler info = elitUyeBilgi();
                String oldLine = "#Elit Üyeler:";
                String newLine = "#Elit Üyeler:\n" + info;
                fileContents = fileContents.replaceAll(oldLine, newLine);
                FileWriter writer = new FileWriter(path);
                writer.append(fileContents);
                writer.flush();
            }
            else if (line.equals("#Genel Üyeler:") && option == 2)//Genel üyeleri genel üyeler altına ekleme işlemi
            {
                while (sc.hasNextLine())
                {
                    buffer.append(sc.nextLine()+System.lineSeparator());
                }
                String fileContents = buffer.toString();
                sc.close();
                genelUyeler info = genelUyeBilgi();
                String oldLine = "#Genel Üyeler:";
                String newLine = "#Genel Üyeler:\n" + info;
                fileContents = fileContents.replaceAll(oldLine, newLine);
                FileWriter writer = new FileWriter(path);
                writer.append(fileContents);
                writer.flush();
            }
        }
    }
}
class Uyeler{//üyelerin olduğu ana classımız
    String isim;
    String soyisim;
    String email;
    void ekranaYaz(Uyeler e){
        System.out.println("Girdiginiz uyenin bilgileri="+this.isim+"\t"+this.soyisim+"\t"+ this.email);
    }
}

class elitUyeler extends Uyeler{//üyelerin alt classı olan elit üyeler class

    elitUyeler(String isim,String soyisim,String email){
        this.email=email;
        this.isim=isim;
        this.soyisim=soyisim;
    }

    public String toString()
    {
        return this.isim + "\t" + this.soyisim + "\t" + this.email;
    }

}
class genelUyeler extends Uyeler{//üyelerin alt classı olan genel üyeler class
    genelUyeler(String isim,String soyisim,String email){
        this.email=email;
        this.isim=isim;
        this.soyisim=soyisim;
    }

    public String toString()
    {
        return this.isim + "\t" + this.soyisim + "\t" + this.email;
    }
}

class EPostaYolla {//e posta yollama kod işlemlerini barındıran class

    public static void mailGonder(String toMail,String body) {
        String alıcıEMail= toMail;
        String gonderenEMail="ifurkangenc@hotmail.com";//Outlook kullandım.
        String sifre="fenerbahcefurkan41";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        try
        {
            Authenticator auth = new javax.mail.Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(gonderenEMail, sifre);
                }
            };

            Session session = Session.getInstance(properties, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.setText(body);
            msg.setSubject("Mail Başlığı");
            msg.setFrom(new InternetAddress(gonderenEMail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(alıcıEMail));
            Transport.send(msg);

        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }}

