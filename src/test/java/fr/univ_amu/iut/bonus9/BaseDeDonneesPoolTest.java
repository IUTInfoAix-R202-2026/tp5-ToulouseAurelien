package fr.univ_amu.iut.bonus9;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariDataSource;
import fr.univ_amu.iut.exercice2.BaseDeDonnees;
import fr.univ_amu.iut.exercice4.Site;
import fr.univ_amu.iut.exercice4.SiteDao;
import java.nio.file.Path;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test du bonus 9 : un pool HikariCP est une {@link DataSource} comme une autre. Le {@link SiteDao}
 * de l'exercice 4, inchangé, fonctionne avec ce pool.
 */
class BaseDeDonneesPoolTest {

  @TempDir Path dossier;

  @Test
  void le_dao_de_l_exercice_4_fonctionne_inchange_avec_le_pool() {
    DataSource pool = BaseDeDonneesPool.poolSurFichier(dossier.resolve("test.db").toString());
    try {
      BaseDeDonnees.initialiser(pool);

      SiteDao dao = new SiteDao(pool); // exactement le DAO de l'exercice 4
      assertThat(dao.findAll()).extracting(Site::numeroCarre).contains("640380");
    } finally {
      ((HikariDataSource) pool).close();
    }
  }

  @Test
  void le_pool_est_bien_une_datasource_hikaricp() {
    DataSource pool = BaseDeDonneesPool.poolSurFichier(dossier.resolve("test2.db").toString());
    try {
      assertThat(pool).isInstanceOf(HikariDataSource.class);
    } finally {
      ((HikariDataSource) pool).close();
    }
  }
}
