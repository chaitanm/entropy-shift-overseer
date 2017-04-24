package com.entropyshift.user.profile;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.UDT;

/**
 * Created by chaitanya.m on 2/20/17.
 */
@UDT(keyspace = "overseer", name = "name")
public class NameInformation
{
    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("middle_name")
    private String middleName;

    public NameInformation()
    {

    }

    public NameInformation(final String firstName, final String middleName, final String lastName)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }
}
