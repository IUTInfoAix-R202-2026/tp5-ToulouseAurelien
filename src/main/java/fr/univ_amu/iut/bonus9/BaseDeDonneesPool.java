package fr.univ_amu.iut.bonus9;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

/**
 * Bonus 9 : un pool de connexions avec HikariCP.
 *
 * <p>Ouvrir une connexion coûte cher. Une application interactive garde donc un <b>pool</b> de
 * connexions prêtes à l'emploi : {@code getConnection()} en emprunte une (instantané), {@code
 * close()} la rend au pool (pas de vraie fermeture).
 *
 * <p>Le point clef : {@link HikariDataSource} est aussi une {@link DataSource}. Les DAO des
 * exercices précédents, écrits contre l'interface {@code DataSource}, fonctionnent <b>sans aucune
 * modification</b> avec ce pool. C'est tout l'intérêt d'avoir programmé contre l'abstraction : on
 * change l'implémentation de la {@code DataSource} sans toucher au reste du code.
 *
 * <p>Pour une appli SQLite mono-utilisateur, le pool n'est pas indispensable ; c'est l'étape qui
 * devient utile quand l'application grandit ou passe à un SGBD serveur (PostgreSQL...).
 */
public class BaseDeDonneesPool {

  private BaseDeDonneesPool() {}

  /** Construit une {@link DataSource} poolée (HikariCP) sur le fichier SQLite donné. */
  public static DataSource poolSurFichier(String chemin) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:sqlite:" + chemin);
    config.setMaximumPoolSize(5);
    config.setConnectionInitSql("PRAGMA foreign_keys = ON");
    return new HikariDataSource(config);
  }
}
