package com.course.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@RequiredArgsConstructor

@Table(name="user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",nullable = false)
    private Long userId;

    @Basic
    @Column(name="user_name",nullable = false,unique=true,length = 50)
    @NonNull
    private String userName;
    @Basic
    @NonNull
    @Column(name="email",nullable = false,unique=true,length = 50)
    private String email;
    @Basic
    @NonNull
    @Column(name="password",nullable = false,length = 70)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
    joinColumns = {@JoinColumn(name="user_id")},
    inverseJoinColumns = {@JoinColumn(name="role_id")})
    private Set<Role> roles=new HashSet<>();

    @OneToOne(mappedBy = "userDetails")
    private Student student;
    @OneToOne(mappedBy = "userDetails")
    private Tutor tutor;

    public void assignRoleToUser(Role role)
    {
        this.roles.add(role);
        role.getUserDetails().add(this);
    }
    public void removeRoleFromUser(Role role)
    {
        this.roles.remove(role);
        role.getUserDetails().remove(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return userId.equals(that.userId) && userName.equals(that.userName) && email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email, password);
    }
}
