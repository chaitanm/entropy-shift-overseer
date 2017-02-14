package com.entropyshift.overseer.credentials;

import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class UserCredentialsDao implements IUserCredentialsDao
{

    private HibernateTemplate template;

    public UserCredentialsDao(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.DEFAULT)
    public UserCredentials getUserCredentialsByUsername(String username) throws UserCredentialsNotFoundException
    {
        UserCredentials credentials = (UserCredentials) template.get(UserCredentials.class, username);
        if(credentials == null) throw new UserCredentialsNotFoundException();
        return credentials;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void insertUserCredentials(UserCredentials userCredentials) {
        template.persist(userCredentials);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateUserCredentials(UserCredentials userCredentials) {
        template.update(userCredentials);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void deleteUserCredentialsByUsername(String username) throws UserCredentialsNotFoundException {
        UserCredentials credentials = (UserCredentials) template.get(UserCredentials.class, username);
        if(credentials == null) throw new UserCredentialsNotFoundException();
        template.delete(credentials);
    }

}
