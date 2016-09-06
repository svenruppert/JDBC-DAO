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

package junit.org.rapidpm.microservice.persistence.jdbc.dao.v001;


import junit.org.rapidpm.microservice.persistence.jdbc.HsqlBaseTest;
import junit.org.rapidpm.microservice.persistence.jdbc.InMemoryHsqldbBuilder;
import org.junit.After;
import org.junit.Before;


public class UserDAOBaseTest extends HsqlBaseTest {


  @Override
  public String[] createSQLInitScriptArray() {
    return new String[]{
        "CLEAR_SCHEMA.sql", "CREATE_TABLE_EXAMPLE.sql"
    };

  }

  private InMemoryHsqldbBuilder.ServerResult serverResult;

  @Before
  public void setUp() throws Exception {
    System.out.println("poolname = " + poolname());

    serverResult = InMemoryHsqldbBuilder.newBuilder()
        .withDbName("testDB")
        .withRandomPort()
        .build();



    startPoolsAndConnect(poolname(), serverResult.getUrl());
    initSchema(poolname());
  }

  @After
  public void tearDown() throws Exception {
    pools.shutdownPool(poolname());

  }

  @Override
  public Class baseTestClass() {
    return UserDAOBaseTest.class;
  }

}
