/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Process.ActivationCode;
import Process.InsertInformation;
import Process.ShowData;
import com.MailSender.MailSender;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Pop Shady
 */
@ManagedBean(name = "Registertion")
@SessionScoped
@Entity
public class Registeration implements Serializable {

    public Registeration() {

        super();
    }

    public Registeration(int activation_Code, String firstName, String lastName, Date date, String countery, String email, Part personalPhoto, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.countery = countery;
        this.email = email;
        this.personalPhoto = personalPhoto;
        this.userName = userName;
        this.password = password;
        this.activation_Code = activation_Code;
    }

    private int ID;
    private String firstName;
    private String lastName;
    private Date date;
    private String countery;
    private String email;
    private Part personalPhoto;
    private String personalPhotoPATH;
    private String userName;
    private String password;
    private String verifyPassword;
    private int activation_Code;

    List<Part> indexPathes;

    @Column(unique = true)
    public int getActivation_Code() {
        return activation_Code;
    }

    public void setActivation_Code(int activation_Code) {
        this.activation_Code = activation_Code;
    }

    @Id
    @Column(name = "PK", length = 255, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Column(name = "FName", length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LName", length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "Countery", length = 255)
    public String getCountery() {
        return countery;
    }

    public void setCountery(String countery) {
        this.countery = countery;
    }

    @Column(name = "Email", length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public Part getPersonalPhoto() {
        return personalPhoto;
    }

    //
    @Column(name = "Path")
    public String getPersonalPhotoPATH() {
        return personalPhotoPATH;
    }

    public void setPersonalPhotoPATH(String personalPhotoPATH) {
        this.personalPhotoPATH = personalPhotoPATH;
    }
    
     /////////////// upload image
    private boolean uploded;

    @Transient
    public boolean isUploded() {
        return uploded;
    }

    public void setUploded(boolean uploded) {
        this.uploded = uploded;
    }
    
    ////

    public void setPersonalPhoto(Part personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    @Column(length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
    // Clear

    public void Cleare() {

        firstName = "";
        lastName = "";
         
        countery = "";
        email = "";

        userName = "";
        password = "";
        verifyPassword = "";

        activation_Code = 0;

    }

    ////////
    
    //Messge date 
    
       
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    /////
    @EJB
    private MailSender mailSender;

    //inseart (Registeration)
    public String savedInformmation() throws ClassNotFoundException {

        //name of image
        personalPhotoPATH = personalPhoto.getSubmittedFileName()+(Math.random()*100000);

        System.out.println("Check 1 : =======Name of image ======> is " + personalPhotoPATH );
        
        //assign Activation_Code
        activation_Code = ActivationCode.AssignRandom();

  //    System.out.println("=============> is " + date);

        // indexPathes=new ArrayList<>();
        //indexPathes.add(personalPhoto);
        String Result = "";

        if (!email.contains("@") || !email.contains(".com")) {

          //  System.out.println("=============> is " + email);

            // Result = " assests/validatedPages/RegisterValidate/EmaiValidator.html ";
        } else if (password.equals(verifyPassword)) {

           System.out.println("Check 1 : =======Name of image ======> is " + personalPhotoPATH );

            InsertInformation in = new InsertInformation();
            in.Inseart(this);

            uploadImage();

            ////// Send Activation Code to User that perform registertion
            //frist get Activation Code from the Table  where id = id of the user
            //String Code = ActivationCode.getActivationCode(ID);
           // System.err.println("ActivationCode is =======>" + activation_Code);

            // System.out.println("Doooooone"+Activation_Code);
            mailSender.sendEmail("shadysalah297@gmail.com", "shadysalah297", "shadyshady", email, activation_Code);
            //
            //secound Send it to .....
            Result = "Activation.xhtml";

        } else {

            //Pass dosn't matches
            Result = "validatedPages/MatchError.html";

        }

        Cleare();
        return Result;
    }

    
    
    //check ActivationCode
    public String checkActivationCode() {

        //System.out.println(personalPhotoPATH + "======" + activation_Code + "=xxxxxxxxx===>" + personalPhotoPATH + "===========");
        String Result = "";

        ActivationCode code = new ActivationCode();
        List<Registeration> li = code.checkActivationCod(activation_Code);

        for (Registeration r : li) {

            ID = r.ID;
            firstName = r.firstName;
            lastName = r.lastName;
            date = r.date;
            countery = r.countery;
            email = r.email;
            personalPhoto = r.personalPhoto;
            personalPhotoPATH = r.personalPhotoPATH;
            userName = r.userName;
            password = r.password;

        }

        //System.out.println(personalPhotoPATH + "======" + activation_Code + "=xxxxxxxxx===>" + personalPhotoPATH + "===========");

        if (li.isEmpty()) {

            //Error username pass then not login
            Result = "validatedPages/CodeError.html";

        } else {
            Result = "LogIn.xhtml";
        }

        Cleare();

        return Result;

    }

    
    
    // =>>>>>>>> &check login && get user Information 
    public String returnInformation() {

        //System.out.println(ID + "======" + firstName + "====>" + personalPhoto);
        String Result = "";

        ShowData show = new ShowData();
        List<Registeration> li = show.GetData(userName, password);

        for (Registeration r : li) {

            ID = r.ID;
            firstName = r.firstName;
            lastName = r.lastName;
            date = r.date;
            countery = r.countery;
            email = r.email;
            personalPhoto = r.personalPhoto;
            personalPhotoPATH = r.personalPhotoPATH;
            userName = r.userName;
            password = r.password;

            //   String s=li.get(0).getEmail();
            //System.out.println(personalPhotoPATH + "======" + personalPhotoPATH + "=xxxxxxxxx===>" + personalPhotoPATH + "===========");
        }
       // System.out.println(personalPhotoPATH + "======" + personalPhotoPATH + "=xxxxxxxxx===>" + personalPhotoPATH + "===========");

       // System.out.println("======" + li.isEmpty() + "====>" + personalPhoto + "=====> id =" + ID);

      
       
        if (li.isEmpty()) {

            //Error username pass then not login
            Result = "assests/validatedPages/LoginValidate/ErrorLogin.html";

        } else {
            Result = "ShowData.xhtml";
        }
        
        //Cleare();

        return Result;

    }

    
    
    
    /////////////// upload image
   
    public void uploadImage(/*Part personal_Photo*/) {

        try {

            System.out.println("==============> Name of image : " + personalPhotoPATH );

                       // problem is here : Part personalPhoto is null
            InputStream in = personalPhoto.getInputStream();

            System.err.println("==============>  Paaaaaaaaaaaaaaaart : " +  personalPhoto);

            System.err.println(" ==============> innnnnnnnnnput Streem : " + in);

            File file = new File("" + personalPhotoPATH);
            file.createNewFile();
            System.err.println("==============>"+file.getAbsoluteFile());
            FileOutputStream out = new FileOutputStream(file);

            byte[] buffer = new byte[1024];

            int lenth;

            while ((lenth = in.read(buffer)) > 0) {

                out.write(buffer, 0, lenth);

            }
            out.close();
            in.close();

            System.err.println(" ==============> Total Path : " + file.getAbsolutePath());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", file.getAbsolutePath());
            uploded = true;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

}
