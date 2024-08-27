package org.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "고객")
public class Customer {

    @Id
    @Column(name = "고객아이디")
    private String customerId;
    @Column(name = "고객이름")
    private String name;
    @Column(name = "나이", columnDefinition = "NUMBER(38, 0)")
    private Integer age;  // NUll Value 고려
    @Column(name = "등급")
    private String membershipLevel;
    @Column(name = "직업")
    private String occupation;
    @Column(name = "적립금", columnDefinition = "NUMBER(38, 0)")
    private Integer points;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
