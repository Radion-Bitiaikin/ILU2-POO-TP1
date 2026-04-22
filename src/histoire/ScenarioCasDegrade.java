package histoire;

import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class ScenarioCasDegrade {

	private String codeErreur(Exception e) {
		System.err.println(e.getMessage());
		StackTraceElement[] st = e.getStackTrace();
		for (StackTraceElement ste : st)
			System.err.println(ste);
		return "";
	}

	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			etal.acheterProduit(32, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			etal.acheterProduit(-2, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		etal.libererEtal();

		Village village = new Village("v", 3, 3);
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}

}
