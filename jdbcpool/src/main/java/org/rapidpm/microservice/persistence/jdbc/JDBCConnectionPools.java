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

package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPool.Builder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class JDBCConnectionPools {

  private final Map<String, JDBCConnectionPool> poolMap = new ConcurrentHashMap<>();

  private JDBCConnectionPools withJDBCConnectionPool(JDBCConnectionPool pool) {
    poolMap.put(pool.getPoolname(), pool);
    return this;
  }

  public Builder addJDBCConnectionPool(String poolname) {
    final Builder builder = JDBCConnectionPool.newBuilder().withParentBuilder(this);
    return builder.withPoolname(poolname);
  }

  public void shutdownPools() {
    poolMap.forEach((k, v) -> v.close());
  }

  public void shutdownPool(String poolname) {
    final boolean b = poolMap.containsKey(poolname);
    if (b) {
      poolMap.get(poolname).close();
      poolMap.remove(poolname);
    }
  }

  public void connectPool(String poolname) {
    final boolean b = poolMap.containsKey(poolname);
    if (b) {
      poolMap.get(poolname).connect();
    }
  }

  public void connectPools() {
    poolMap.forEach((k, v) -> v.connect());
  }


  public HikariDataSource getDataSource(String poolname) {
    return poolMap.get(poolname) != null ? poolMap.get(poolname).getDataSource() : null;
  }

  public Optional<JDBCConnectionPool> getPool(String poolname) {

    return Optional.ofNullable(poolMap.get(poolname));

  }
}
