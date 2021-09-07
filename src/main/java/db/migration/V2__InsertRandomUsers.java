package db.migration;

import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V2__InsertRandomUsers extends BaseJavaMigration{
  
  public void migrate(Context context){
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(
      new SingleConnectionDataSource(context.getConnection(), true));

    jdbcTemplate.execute(String.format("CREATE TABLE `sample_user`"
      + "(`id` INT AUTO_INCREMENT,"
      + "`username` VARCHAR(255) NOT NULL UNIQUE,"
      + "`first_name` VARCHAR(255) NOT NULL,"
      + "`last_name` VARCHAR(255) NOT NULL,"
      + "PRIMARY KEY (`id`)"
      + ");"));

    for (int i = 0; i <= 10; i++) {
      jdbcTemplate.execute(String.format("insert into `sample_user`" 
      + " (`username`, `first_name`, `last_name`) values" 
      + " ('%d@abioye.com', 'Fela_%d', 'Kuti_%d')", i, i, i));
    }
  }
}
