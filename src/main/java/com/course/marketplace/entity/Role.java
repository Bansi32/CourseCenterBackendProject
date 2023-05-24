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
@RequiredArgsConstructor
@ToString
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id",nullable = false)
    private Long roleId;
    @NonNull
    @Basic
    @Column(name="role_name",nullable = false,unique = true,length = 45)
    private String name;


    @ManyToMany(mappedBy="roles",fetch = FetchType.LAZY)
    private Set<UserDetails> userDetails=new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId.equals(role.roleId) && name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }


}
