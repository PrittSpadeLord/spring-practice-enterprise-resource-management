package io.github.prittspadelord.erm.model;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Date;
import java.util.Objects;

public abstract class Employee {

    public enum Role {
        FRONTEND_DEVELOPER,
        BACKEND_DEVELOPER,
        FULLSTACK_DEVELOPER,
        TECH_LEAD,
        AGILIST,
        PRODUCT_MANAGER
    }

    @NonNull private String firstName;
    @Nullable private String middleName;
    @NonNull private String lastName;

    private long dateOfBirthMillis;
    private long dateOfJoiningMillis;

    @NonNull private Role role;

    public Employee(
            @NonNull String firstName,
            @Nullable String middleName,
            @NonNull String lastName,
            long dateOfBirthMillis,
            long dateOfJoiningMillis,
            @NonNull Role role
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirthMillis = dateOfBirthMillis;
        this.dateOfJoiningMillis = dateOfJoiningMillis;
        this.role = role;
    }

    @NonNull
    public String getFirstName() {
        return this.firstName;
    }

    @Nullable
    public String getMiddleName() {
        return this.middleName;
    }

    @NonNull
    public String getLastName() {
        return this.lastName;
    }

    @NonNull
    public String getFullName() {
        return this.firstName + " " + Objects.toString(this.middleName, "") + " " + this.lastName;
    }

    @NonNull
    public Date getBirthDate() {
        return new Date(this.dateOfBirthMillis);
    }

    // This thing needs to be updated
    public int getAge() {
        long ageMillis = (new Date()).getTime() - dateOfBirthMillis;

        float ageF = ((float) ageMillis)/((float) 1000L * 60L * 60L * 24L * 365L);

        return (int) Math.floor(ageF);
    }

    @NonNull
    public Date getJoiningDate() {
        return new Date(this.dateOfJoiningMillis);
    }
}
