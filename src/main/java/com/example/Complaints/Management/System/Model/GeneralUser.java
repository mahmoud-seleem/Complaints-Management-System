package com.example.Complaints.Management.System.Model;

import com.example.Complaints.Management.System.Repository.GeneralUserRepo;
import com.example.Complaints.Management.System.Utils.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",discriminatorType = DiscriminatorType.STRING)
@Table(name = "general_user")
public class GeneralUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, name = "user_name")
    private String userName = "user";

    @Column(nullable = false, name = "password")
    private String password = "123456";

    @Column(name = "email")
    private String email = "user@gmail.com";

    @Column(name = "age")
    private Integer age = 24;

//    @Enumerated(EnumType.STRING)
    //@Column(name = "role",insertable=false, updatable=false)
    @Formula("role")
    private String role;

    @ElementCollection
    @CollectionTable(name = "user_phones", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone")
    private List<String> phoneNumbers = new ArrayList<>();


    public GeneralUser(String userName){
        this.userName = userName;
    }
    public GeneralUser(Long userId, String userName, String password, String email, int age) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public GeneralUser() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public String toString(){
        return "userId = " + getUserId() +"\n"
        +"userName = " + getUserName() +"\n"
        +"email = " + getEmail() +"\n"
        +"password = " + getPassword() +"\n"
        +"age = " + getAge() +"\n"
        +"phones = " + getPhoneNumbers().toString() +"\n" ;
    }
}
