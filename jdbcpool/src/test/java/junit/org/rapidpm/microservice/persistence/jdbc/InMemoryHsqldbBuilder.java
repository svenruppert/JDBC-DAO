package junit.org.rapidpm.microservice.persistence.jdbc;

import org.hsqldb.server.Server;
import org.rapidpm.dependencies.core.net.PortUtils;

import java.io.PrintWriter;

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
 * Created by RapidPM - Team on 02.05.16.
 */
public class InMemoryHsqldbBuilder {

  private PrintWriter errorWriter = null;
  private PrintWriter printWriter = null;
  private String dbName;
  private String url;
  private int port;
  private boolean silent = true;
  private boolean trace = false;

  private InMemoryHsqldbBuilder() {
  }

  public static InMemoryHsqldbBuilder newBuilder() {
    return new InMemoryHsqldbBuilder();
  }

  public InMemoryHsqldbBuilder withRandomPort() {
    port = new PortUtils().nextFreePortForTest();
    return this;
  }

  public InMemoryHsqldbBuilder withPort(int port) {
    this.port = port;
    return this;
  }

  public InMemoryHsqldbBuilder withDbName(String dbName) {
    this.dbName = dbName;
    return this;
  }

  public InMemoryHsqldbBuilder withErrWriter(PrintWriter writer) {
    this.errorWriter = writer;
    return this;
  }

  public InMemoryHsqldbBuilder withPrintWriter(PrintWriter writer) {
    this.errorWriter = writer;
    return this;
  }

  public InMemoryHsqldbBuilder withSilent(boolean silent) {
    this.silent = silent;
    return this;
  }

  public InMemoryHsqldbBuilder withTrace(boolean trace) {
    this.trace = trace;
    return this;
  }

  public ServerResult build() {

    final Server hsqlServer = new Server();
    hsqlServer.setDatabaseName(0, dbName);
    hsqlServer.setDatabasePath(0, "mem:target/" + dbName);
    hsqlServer.setPort(port);
    hsqlServer.setAddress("127.0.0.1");
    hsqlServer.setErrWriter(errorWriter);
    hsqlServer.setLogWriter(printWriter);
    hsqlServer.setSilent(silent);
    hsqlServer.setTrace(trace);
    hsqlServer.start();
    hsqlServer.checkRunning(true);

    url = "jdbc:hsqldb:mem://127.0.0.1:" + port + "/" + dbName;

    return new ServerResult(dbName, port, hsqlServer, url);
  }


  public static class ServerResult {
    private String dbName;
    private int port;
    private Server server;
    private String url;


    public ServerResult(final String dbName, final int port, final Server server, final String url) {
      this.dbName = dbName;
      this.port = port;
      this.server = server;
      this.url = url;
    }

    public String getDbName() {
      return dbName;
    }

    public int getPort() {
      return port;
    }

    public Server getServer() {
      return server;
    }

    public String getUrl() {
      return url;
    }
  }


}
