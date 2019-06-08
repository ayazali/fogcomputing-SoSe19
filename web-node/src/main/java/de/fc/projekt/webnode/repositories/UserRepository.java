package de.fc.projekt.webnode.repositories;

import org.springframework.data.repository.CrudRepository;

import de.fc.projekt.webnode.models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}
