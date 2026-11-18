package com.programmers.programmers_community.dao;

import com.programmers.programmers_community.model.User;
import com.programmers.programmers_community.util.ObjectDbConnection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// DAO (Data Access Object): this class is the only one that talks directly to the database.
// The service calls these methods, so the rest of the app never touches the database itself.
public class UserDAO {

	// TODO: change it so it returns true or false
	// Saves a new user in the database
	public void registerUser(User newUser) {
		// Every method opens its own EntityManager, which is the object we use to work with the database
		EntityManager em = ObjectDbConnection.getEntityManager();
		try {
			// A transaction groups the changes so they are saved all together or not at all
			em.getTransaction().begin();
			// persist() prepares the new user to be inserted in the database
			em.persist(newUser);
			// commit() saves the changes for real
			em.getTransaction().commit();
		} catch (Exception e) {
			// If something fails we undo the transaction (rollback) so nothing is left half saved
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			// We throw the exception again so the caller knows that something went wrong
			throw e;
		} finally {
			// The EntityManager is always closed, whether it worked or not
			em.close();
		}
	}
	// Looks for a user by its username (it is the primary key of the User class)
	// If the user doesn't exist it returns null
	public User consultUser(String username) {
		EntityManager em = ObjectDbConnection.getEntityManager();
		try {
			// find() searches an object by its primary key
			return em.find(User.class, username);
		} finally {
			em.close();
		}
	}

	// TODO: change it so it returns true or false
	// Updates the password, gender and language of an existing user
	public void modifyUser(User user) {
		EntityManager em = ObjectDbConnection.getEntityManager();
		try {
			// We look for the user we want to modify, if it exists we change its data, if not we do nothing
			User existingUser = em.find(User.class, user.getUsername());
			if (existingUser != null) {
				em.getTransaction().begin();
				// existingUser is managed by the EntityManager, so these changes are saved when we commit
				existingUser.setPassword(user.getPassword());
				existingUser.setGender(user.getGender());
				existingUser.setLanguage(user.getLanguage());
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	// Returns the list of users shown in the table of the home screen
	// If language is null it returns all the users, otherwise only the ones with that language
	// The current user is never included in the list
	public List<User> consultByLanguage(String language, User user) {
		EntityManager em = ObjectDbConnection.getEntityManager();
		try {
			// The queries are written in JPQL, which is like SQL but uses classes and attributes instead of tables and columns
			TypedQuery<User> query;
			if (language == null) {
				// We search for the users with a different username from the current user
				query = em.createQuery("SELECT u FROM User u WHERE u.username <> :actual", User.class);
				// :actual is a named parameter, here we give it its value
				query.setParameter("actual", user.getUsername());
			} else {
				// We search for the users with a different username from the current user and a equal language
				query = em.createQuery("SELECT u FROM User u WHERE u.language = :leng AND u.username <> :actual", User.class);
				query.setParameter("leng", language);
				query.setParameter("actual",user.getUsername());
			}
			// Runs the query and returns the results as a list
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}