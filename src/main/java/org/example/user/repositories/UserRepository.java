package org.example.user.repositories;

import org.example.user.models.Address;
import org.example.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u")
    public Optional<List<User>> getAllUsers();

    public User save(User user);

    @Override
    public Optional<User> findById(Long id);

    public Optional<User> findByEmail(String email);

    @Query("select a from Address a where a.city=:city and a.street=:street and a.zipcode=:zipcode")
    public Optional<Address> getAddressOfUser(String city,String street,String zipcode);
}
