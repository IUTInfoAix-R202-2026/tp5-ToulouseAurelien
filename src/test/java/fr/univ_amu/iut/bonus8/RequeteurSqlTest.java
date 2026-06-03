package fr.univ_amu.iut.bonus8;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univ_amu.iut.exercice2.BaseDeDonnees;
import java.nio.file.Path;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test du bonus 8 : le requêteur générique sait exécuter n'importe quel SELECT et mapper ses
 * lignes, sans réécrire la boucle {@code ResultSet}.
 */
class RequeteurSqlTest {

  @TempDir Path dossier;
  private RequeteurSql requeteur;

  @BeforeEach
  void preparer() {
    DataSource source = BaseDeDonnees.surFichier(dossier.resolve("test.db").toString());
    BaseDeDonnees.initialiser(source);
    requeteur = new RequeteurSql(source);
  }

  @Test
  void lister_mappe_chaque_ligne_avec_le_row_mapper_fourni() {
    List<String> codes =
        requeteur.query("SELECT code FROM taxon ORDER BY code", rs -> rs.getString("code"));

    assertThat(codes).containsExactly("Nyclei", "Pippip", "Rhihip", "Tadten");
  }

  @Test
  void le_meme_requeteur_sert_pour_une_autre_requete_et_un_autre_type() {
    List<Integer> nbSites = requeteur.query("SELECT COUNT(*) AS n FROM site", rs -> rs.getInt("n"));

    assertThat(nbSites).containsExactly(1);
  }
}
