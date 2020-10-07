package org.beshelmek.core.jpa;

import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer id;

    @Column(name = "login")
    public String login;

    @Column(name = "email")
    public String email;

    @Column(name = "uuid")
    @Type(type = "org.hibernate.type.UUIDCharType")
    public UUID uuid;

    @Column(name = "email_checked", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean email_checked;

    @Column(name = "otp_secret")
    public String otp_secret;

    @Column(name = "reg_time")
    public String reg_time;

    @Column(name = "realmoney")
    public Double realmoney;

    @Column(name = "money")
    public Double money;

    @Column(name = "last_play")
    public Integer last_play;

    @Column(name = "vk_id")
    public Integer vk_id;

    @Column(name = "banned_at")
    public Timestamp banned_at;

    @Column(name = "reputation")
    public Integer reputation;

    @Column(name = "last_ip")
    public String last_ip;

    @Column(name = "passchange_time")
    public Integer passchange_time;

    @Column(name = "email_confirmed_at")
    public Integer email_confirmed_at;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", uuid=" + uuid +
                ", email_checked=" + email_checked +
                ", otp_secret='" + otp_secret + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", realmoney=" + realmoney +
                ", money=" + money +
                ", last_play=" + last_play +
                ", vk_id=" + vk_id +
                ", banned_at=" + banned_at +
                ", reputation=" + reputation +
                ", last_ip='" + last_ip + '\'' +
                ", passchange_time=" + passchange_time +
                ", email_confirmed_at=" + email_confirmed_at +
                '}';
    }

    public void setEmail(String s) {
        this.email = s;
    }
}
