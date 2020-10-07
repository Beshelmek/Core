package org.beshelmek.core.dao;

import org.beshelmek.core.dao.repositories.UserRepository;
import org.beshelmek.core.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@EnableTransactionManagement
public class UserDAO {
    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("unchecked")
    public Optional<User> getByID(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
