package com.dama.backend.dama.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dama.backend.dama.model.Friend; // Import Friend
import com.dama.backend.dama.model.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // Import OneToMany
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users") // Changed to "users" as per your code. My previous suggestion was "_user"
public class User implements UserDetails {
    @Id
    @GeneratedValue // Consider GenerationType.IDENTITY for better control with PostgreSQL
    private Integer id;
    @Column(unique = true)
    private String username;
    private String name; // Changed from 'firstname'
    private String surname; // Changed from 'lastname'
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // This user is user1 in these friendship records
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL) // Removed orphanRemoval for now, add if needed
    private List<Friend> friendshipsInitiated; // Renamed for clarity

    // This user is user2 in these friendship records
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL) // Removed orphanRemoval for now, add if needed
    private List<Friend> friendshipsReceived; // Renamed for clarity

    // Important: REMOVE THE OLD @ManyToMany "friends" field!
    // @ManyToMany(mappedBy="users", cascade=CascadeType.ALL)
    // @JsonManagedReference
    // private List<Friend> friends;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return password;
    }
}