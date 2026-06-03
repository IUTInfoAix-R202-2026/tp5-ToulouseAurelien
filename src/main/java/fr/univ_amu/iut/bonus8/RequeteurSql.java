package fr.univ_amu.iut.bonus8;

import fr.univ_amu.iut.jdbc.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Petit requêteur générique (bonus 8) : exécute un {@code SELECT} et délègue le mapping de chaque
 * ligne à un {@link RowMapper}.
 *
 * <p>C'est l'aboutissement de la couche DAO : au lieu de répéter la boucle {@code ResultSet} dans
 * chaque méthode, on l'écrit <b>une seule fois</b> ici. Les DAO n'ont plus qu'à fournir leur
 * requête et leur fonction de mapping. (C'est, à petite échelle, ce que font des outils comme
 * Spring JDBC.)
 */
public class RequeteurSql {

  private final DataSource source;

  public RequeteurSql(DataSource source) {
    this.source = source;
  }

  /** Exécute {@code sql} et renvoie une ligne mappée par élément. */
  public <T> List<T> query(String sql, RowMapper<T> mapper) {
    List<T> resultats = new ArrayList<>();

    try (Connection connexion = source.getConnection();
        PreparedStatement ps = connexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        resultats.add(mapper.mapper(rs));
      }
    } catch (SQLException e) {
      throw new DataAccessException("Impossible d'exécuter la requête SQL", e);
    }

    return resultats;
  }
}
