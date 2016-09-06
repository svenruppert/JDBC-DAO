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
import org.rapidpm.dependencies.core.basepattern.builder.NestedBuilder;

import javax.annotation.Nonnull;

public class JDBCConnectionPool {

  private final String poolname;

  private final String jdbcURL;
  private final String username;
  private final String passwd;

  private final Integer timeout;
  private final boolean autoCommit;
  private final String sqlInit;
  private final String sqlTest;
  private final int maximalPoolSize;
  private final String jdbcDriverClassName;

  private HikariDataSource dataSource;

  private JDBCConnectionPool(final Builder builder) {
    poolname = builder.poolname;
    jdbcURL = builder.jdbcURL;
    username = builder.username;
    passwd = builder.passwd;
    autoCommit = builder.autoCommit;
    sqlInit = builder.sqlInit;
    sqlTest = builder.sqlTest;
    timeout = builder.timeout;
    maximalPoolSize = builder.maximalPoolSize;
    jdbcDriverClassName = builder.jdbcDriverClassName;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void connect() {
    if (dataSource == null || dataSource.isClosed()) {
      dataSource = new HikariDataSource();
      dataSource.setJdbcUrl(jdbcURL);
      dataSource.setUsername(username);
      dataSource.setPoolName(poolname);
      dataSource.setAutoCommit(autoCommit);
      dataSource.setMaximumPoolSize(maximalPoolSize);
      if (passwd != null) dataSource.setPassword(passwd);
      if (sqlInit != null) dataSource.setConnectionInitSql(sqlInit);
      if (sqlTest != null) dataSource.setConnectionTestQuery(sqlTest);
      if (timeout != null) dataSource.setConnectionTimeout(timeout);
      if (jdbcDriverClassName != null) dataSource.setDriverClassName(jdbcDriverClassName);
    }
  }

  public void close() {
    dataSource.close();
  }

  public String getPoolname() {
    return poolname;
  }

  public HikariDataSource getDataSource() {
    return dataSource;
  }

  public static final class Builder extends NestedBuilder<JDBCConnectionPools, JDBCConnectionPool> {
    public String jdbcDriverClassName;
    public int maximalPoolSize = 20;
    private String poolname;
    private String jdbcURL;
    private String username;
    private String passwd;
    private boolean autoCommit;
    private String sqlInit;
    private String sqlTest;
    private int timeout = 2000;

    private Builder() {
    }

    @Nonnull
    public Builder withPoolname(@Nonnull final String poolname) {
      this.poolname = poolname;
      return this;
    }

    @Nonnull
    public Builder withJdbcURL(@Nonnull final String jdbcURL) {
      this.jdbcURL = jdbcURL;
      return this;
    }

    @Nonnull
    public Builder withUsername(@Nonnull final String username) {
      this.username = username;
      return this;
    }

    @Nonnull
    public Builder withPasswd(@Nonnull final String passwd) {
      this.passwd = passwd;
      return this;
    }

    @Nonnull
    public Builder withAutoCommit(final boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    @Nonnull
    public Builder withSqlInit(@Nonnull final String sqlInit) {
      this.sqlInit = sqlInit;
      return this;
    }

    @Nonnull
    public Builder withSqlTest(@Nonnull final String sqlTest) {
      this.sqlTest = sqlTest;
      return this;
    }

    @Nonnull
    public Builder withTimeout(@Nonnull final int timeout) {
      this.timeout = timeout;
      return this;
    }

    @Nonnull
    public Builder withMaximalPoolsSize(@Nonnull final int maximalPoolSize) {
      this.maximalPoolSize = maximalPoolSize;
      return this;
    }

    @Nonnull
    public Builder withJdbcDriverClassName(String jdbcDriverClassName) {
      this.jdbcDriverClassName = jdbcDriverClassName;
      return this;
    }

    @Nonnull
    public JDBCConnectionPool build() {
      return new JDBCConnectionPool(this);
    }
  }
}
