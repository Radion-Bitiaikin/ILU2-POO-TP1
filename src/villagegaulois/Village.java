package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder c = new StringBuilder();
		int n = marche.trouverEtalLibre();
		c.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if (-1 == n) {
		} else {
			marche.utiliserEtal(n, vendeur, produit, nbProduit);
			c.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (n + 1) + ".\n");
		}
		return c.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder c = new StringBuilder();
		Etal e[] = marche.trouverEtals(produit);
		switch (e.length) {
		case 0:
			break;
		case 1:
			c.append("Seul le vendeur " + e[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
			break;
		default:
			c.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < e.length; i++) {
				c.append("- " + e[i].getVendeur().getNom() + "\n");
			}
		}
		return c.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}

	private class Marche {
		Etal etals[];

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			assert (etals.length > indiceEtal && 0 <= indiceEtal);
			etals[indiceEtal] = new Etal();
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (null == etals[i])
					return i;
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			int n = 0;
			for (int i = 0; i < etals.length; i++) {
				if (null != etals[i] && etals[i].contientProduit(produit))
					n++;
			}
			Etal e[] = new Etal[n];
			n = 0;
			for (int i = 0; i < etals.length; i++) {
				if (null != etals[i] && etals[i].contientProduit(produit))
					e[n++] = etals[i];
			}
			return e;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (null != etals[i] && gaulois.equals(etals[i].getVendeur()))
					return etals[i];
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder c = new StringBuilder();
			int n = 0;
			for (int i = 0; i < etals.length; i++) {
				if (null == etals[i]) {
					n++;
				} else {
					c.append(etals[i].afficherEtal());
				}
			}
			if (0 != n)
				c.append("Il reste " + n + " étals non utilisés dans le marché.\n");
			return c.toString();
		}
	}
}