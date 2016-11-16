package junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.dao;

import junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.model.User;

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
 * Created by RapidPM - Team on 16.11.16.
 */
public class UserDAOHSQL extends UserDAOAbstractImpl {

  public String createInsert(final User user) {
    final String sql = "INSERT INTO CUSTOMER " +
        "( CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL ) " +
        " VALUES " +
        "( " +
        user.getCustomerID() + ", " +
        "'" + user.getFirstname() + "', " +
        "'" + user.getLastname() + "', " +
        "'" + user.getEmail() + "' " +
        ")";
    return sql;
  }

  public String getUserByCustomerID(int customerID) {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL " +
        " FROM CUSTOMER " +
        " WHERE CUSTOMER_ID = " + customerID + "";
  }


  public String getMailByCustomerID(int customerID) {
    return "SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_ID = " + customerID + "";
  }




}
