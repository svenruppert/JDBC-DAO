/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.persistence.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public interface QueryOneValue<T> extends BasicOperation {

  default Optional<T> execute(JDBCConnectionPool connectionPool) {
    final HikariDataSource dataSource = connectionPool.getDataSource();
    try (final Connection connection = dataSource.getConnection();
         final Statement statement = connection.createStatement();
         final ResultSet resultSet = statement.executeQuery(createSQL())) {
      final boolean next = resultSet.next();
      if (next) {
        final T value = getFirstElement(resultSet);
        if (resultSet.next()) {
          throw new RuntimeException("too many values are selected with query");
        } else {
          return Optional.of(value);
        }
      } else {
        return Optional.empty();
      }

    } catch (final SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }


  T getFirstElement(final ResultSet resultSet) throws SQLException;

}
