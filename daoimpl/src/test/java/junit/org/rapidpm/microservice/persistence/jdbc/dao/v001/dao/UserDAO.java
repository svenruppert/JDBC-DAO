package junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.dao;

import junit.org.rapidpm.microservice.persistence.jdbc.dao.v001.model.User;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPool;
import org.rapidpm.microservice.persistence.jdbc.dao.QueryOneTypedValue;
import org.rapidpm.microservice.persistence.jdbc.dao.Update;

import java.util.Optional;

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
public interface UserDAO {

  JDBCConnectionPool connectionPool();

  default void writeUser(final User user) {
    ((Update) () -> createInsert(user)).update(connectionPool());
  }

  String createInsert(User user);

  default Optional<User> readUser(int customerID) {
    return ((QueryUser) () -> getUserByCustomerID(customerID)).execute(connectionPool());
  }

  String getUserByCustomerID(int customerID);

  default Optional<String> readMailAddress(int customerID) {
    return ((QueryOneTypedValue.QueryOneString) () -> getMailByCustomerID(customerID)).execute(connectionPool());
  }

  String getMailByCustomerID(int customerID);


}
