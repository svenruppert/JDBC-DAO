package junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.model;

import java.util.Objects;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by RapidPM - Team on 08.09.16.
 */
public class User {

  private final Integer customerID;
  private final String firstname;
  private final String lastname;
  private final String email;

  public User(Integer customerID, String firstname, String lastname, String email) {
    this.customerID = customerID;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
  }

  public Integer getCustomerID() {
    return customerID;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerID, firstname, lastname, email);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    final User user = (User) o;
    return Objects.equals(customerID, user.customerID) &&
        Objects.equals(firstname, user.firstname) &&
        Objects.equals(lastname, user.lastname) &&
        Objects.equals(email, user.email);
  }

  @Override
  public String toString() {
    return "User{" +
        "customerID=" + customerID +
        ", firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email=" + email +
        '}';
  }
}
