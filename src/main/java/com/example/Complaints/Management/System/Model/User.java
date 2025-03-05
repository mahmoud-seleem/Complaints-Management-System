package com.example.Complaints.Management.System.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue(value = "USER")
public class User extends GeneralUser {
}
