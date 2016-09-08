# JDBC-DAO - a minimalistic JDBC based DAO

Sometimes you have a small project with a  minimal database and you don´ want to 
start with one of the big ORM frameworks.
For this I extracted the few classes here to give a minimalistic base.

This is not planned to be used for big projects. 


## Example

For this example ( you could find it in the src-test folder )
 I created a class "User"
 
 ```java
 public class User {
 
   private final Integer customerID;
   private final String firstname;
   private final String lastname;
   private final String email;
   //SNIPP
 ```

To implement a DAO you don´t have to extend a class. Just define the methods you need.
For this implementation the JDBC Connection Pool

```xml
    <dependency>
      <groupId>com.zaxxer</groupId>
      <artifactId>HikariCP</artifactId>
    </dependency>
```

is used.

So let´s have a look at the DAO itself.

```java
pubic class UserDAO {

  private JDBCConnectionPool connectionPool;

  public UserDAO workOnPool(final JDBCConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
    return this;
  }


  public void writeUser(final User user) {
    ((Update) () -> createInsert(user)).update(connectionPool);
  }

  private String createInsert(final User user) {
    final String sql = "INSERT INTO CUSTOMER " +
        "( CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL ) " +
        " VALUES " +
        "( " +
              user.getCustomerID() + ", " +
        "'" + user.getFirstname()  + "', " +
        "'" + user.getLastname()   + "', " +
        "'" + user.getEmail()      + "' " +
        ")";
    return sql;
  }

  public Optional<User> readUser(int customerID) {
    return ((QueryUser) () -> getUserByCustomerID(customerID)).execute(connectionPool);
  }

  private String getUserByCustomerID(int customerID) {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL " +
        " FROM CUSTOMER " +
        " WHERE CUSTOMER_ID = " + customerID + "";
  }

  public Optional<String> readMailAddress(int customerID) {
    return ((QueryOneTypedValue.QueryOneString) () -> getMailByCustomerID(customerID)).execute(connectionPool);
  }

  private String getMailByCustomerID(int customerID) {
    return "SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_ID = " + customerID + "";
  }

  public interface QueryUser extends QueryOneValue<User> {

    @Override
    default User getFirstElement(final ResultSet resultSet) throws SQLException {
      return new User(
          resultSet.getInt("CUSTOMER_ID"),
          resultSet.getString("FIRSTNAME"),
          resultSet.getString("LASTNAME"),
          resultSet.getString("EMAIL")
      );
    }
  }

}
```

Here I devided the task into two parts. First a SQL generating part that could be extracted or done by an other framework
and the invokation itself.

```java
public Optional<User> readUser(int customerID) {
    return ((QueryUser) () -> getUserByCustomerID(customerID)).execute(connectionPool);
  }

  private String getUserByCustomerID(int customerID) {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL " +
        " FROM CUSTOMER " +
        " WHERE CUSTOMER_ID = " + customerID + "";
  }
```

Have in mind, that this is only a very small implementation and nothing you could compare with frameworks like JOOQ or Speedment or JPA or..
But it is easy to use ;-)

Please have a look at the jUnit Tests, where you could find an example how to use InMemory DB in combination with jUnit.

Feel free to change and extend..  ;-)

If you have questions, the best easiest ways to reach me are

mail: sven.ruppert (at) gmail.com or Twitter @SvenRuppert