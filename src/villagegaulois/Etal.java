package villagegaulois;

import personnages.Gaulois;

//TODO partie exception

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	private String codeErreur(Exception e) {
		System.err.println(e.getMessage());
		StackTraceElement[] st = e.getStackTrace();
		for (StackTraceElement ste : st)
			System.err.println(ste);
		return "";
	}

	public String libererEtal() {
		try {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append("il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche
						+ " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		} catch (Exception e) {
			return codeErreur(e);
		}
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return vendeur.getNom() + " vend " + quantite + " " + produit + ".\n";
		}
		return "L'étal est libre\n";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) throws IllegalArgumentException,IllegalStateException {

		if (quantiteAcheter < 1)
			throw new IllegalArgumentException("quantiteAcheter<1");
		if (!isEtalOccupe())
			throw new IllegalStateException("!isEtalOccupe");
		try {
			StringBuilder chaine = new StringBuilder();
			chaine.append(
					acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " à " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", " + acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom() + ", est ravi de tout trouver sur l'étal de " + vendeur.getNom()
						+ "\n");
			}
			return chaine.toString();

		} catch (Exception e) {
			return codeErreur(e);
		}

	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}
}
