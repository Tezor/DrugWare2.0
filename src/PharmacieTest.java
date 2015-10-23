import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class PharmacieTest {
	
	@Rule public TestName name= new TestName();
	private Pharmacie pharmacie;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	System.out.println("Début du test - PharmacieTest.java "  + name.getMethodName());
	pharmacie = new Pharmacie();
	}

	@After
	public void tearDown() throws Exception {
	System.out.println("Fin du test - PharmacieTest.java "  + name.getMethodName());
	}

	@Test
	public void testPharmacie() {
		assertNotNull("Erreur", pharmacie);
	}

	@Test
	public void testGetLesClients() {
		assertNotNull("Erreur", pharmacie.getLesClients());
	}

	@Test
	public void testSetLesClients() {
		List<Client> lesClients = new ArrayList<>(1);
		pharmacie.setLesClients(lesClients);
		assertEquals("Erreur", lesClients, pharmacie.getLesClients());
	}

	@Test
	public void testGetLesMedicaments() {
		assertNotNull("Erreur", pharmacie.getLesMedicaments());
	}

	@Test
	public void testSetLesMedicaments() {
		List<Medicament> lesMeds = new ArrayList<>(1);
		pharmacie.setLesMedicaments(lesMeds);
		assertEquals("Erreur", lesMeds, pharmacie.getLesMedicaments());
	}

	@Test
	public void testSiClientExiste() {
		lireClients();
		assertTrue("Erreur", pharmacie.siClientExiste("ABCO12345678"));
		assertFalse("Erreur", pharmacie.siClientExiste("ABCO12345679"));
		
	}

	@Test
	public void testAjouterClient() {
		pharmacie.ajouterClient("ABCO12345678", "Malik", "Tristan");
		
		assertNotNull("Erreur", pharmacie.getLesClients().get(0));
	}

	@Test
	public void testGetPrescriptionsClient() {
		lireClients();
		lirePrescription();
		assertNull("Erreur", pharmacie.getPrescriptionsClient("ABCO12345679"));
		assertNotNull("Erreur", pharmacie.getPrescriptionsClient("ABCO12345678"));
		Prescription pres = pharmacie.getLesClients().get(0).getPrescriptions().get(0);
		assertEquals("Erreur", "Tylenol", pres.getMedicamentAPrendre());
		assertEquals("Erreur", 2,pres.getDose(),0);
		assertEquals("Erreur", 3,pres.getRenouvellements(),0);
		
	}

	@Test
	public void testServirPrescription() {
		lireClients();
		lireMedicament();
		lirePrescription();
		assertTrue("Erreur", pharmacie.servirPrescription("ABCO12345678", "tylenol"));
		assertEquals("Erreur", 2, pharmacie.getLesClients().get(0).getPrescriptions().get(0).getRenouvellements());
		assertFalse("Erreur", pharmacie.servirPrescription("ABCO12345678", "cancer"));
		assertFalse("Erreur", pharmacie.servirPrescription("ABCO12345679", "tylenol"));
		pharmacie.getPrescriptionsClient("ABCO12345678").get(0).setRenouvellements(0);
		assertFalse("Erreur", pharmacie.servirPrescription("ABCO12345678", "tylenol"));
		
		
		
		
	}

	@Test
	public void testTrouverInteraction() {
		
		assertFalse("Erreur", pharmacie.trouverInteraction(null, null));
		lireMedicament();
		assertTrue("Erreur", pharmacie.trouverInteraction(pharmacie.getLesMedicaments().get(0).getNomMolecule(), pharmacie.getLesMedicaments().get(1).getNomMolecule()));
		String[] interactions = {"allo"};
		pharmacie.getLesMedicaments().get(1).setInteractions(interactions);
		assertFalse("Erreur", pharmacie.trouverInteraction(pharmacie.getLesMedicaments().get(0).getNomMolecule(), pharmacie.getLesMedicaments().get(1).getNomMolecule()));
		String[] interactions2 = {"Sida"};
		pharmacie.getLesMedicaments().get(0).setInteractions(interactions2);
		assertTrue("Erreur", pharmacie.trouverInteraction(pharmacie.getLesMedicaments().get(0).getNomMolecule(), pharmacie.getLesMedicaments().get(1).getNomMolecule()));
	}
	
	@Test
	public void testAjouterPrescription(){
		lireClients();
		lireMedicament();
		lirePrescription();
		assertFalse("Erreur", pharmacie.ajouterPrescription("asdasd", "medicament", 5, 5));
		assertFalse("Erreur", pharmacie.ajouterPrescription("ABCO12345678", "medicament", 5, 5));
		assertTrue("Erreur", pharmacie.ajouterPrescription("ABCO12345678", "Tylenol", 5, 5));
		assertTrue("Erreur", pharmacie.ajouterPrescription("ABCO12345678", "Advil", 5, 5));
	}

	
	
	public void lireMedicament(){
		String[] usage = {"allo"};
		double[] dosesPossibles = {2,2};
		String[] interactions = {"Cancer"};
	Medicament medicament1 = new Medicament();
	medicament1.setNomMarque("Tylenol");
	medicament1.setNomMolecule("Cancer");
	medicament1.setUnite("4");
	medicament1.setUsages(usage);
	medicament1.setDosesPossibles(dosesPossibles);
	medicament1.setInteractions(interactions);
	
	Medicament medicament2 = new Medicament();
	medicament2.setNomMarque("Advil");
	medicament2.setNomMolecule("Sida");
	medicament2.setUnite("4");
	medicament2.setUsages(usage);
	medicament2.setDosesPossibles(dosesPossibles);
	medicament2.setInteractions(interactions);
	
	
	List<Medicament> lesMedicaments = new ArrayList<>();
	lesMedicaments.add(medicament1);
	lesMedicaments.add(medicament2);
	pharmacie.setLesMedicaments(lesMedicaments);
	
	}
	
	public void lireClients(){
		Client client = new Client("ABCO12345678", "Malik", "Tristan");
		List<Client> lesClients = new ArrayList<>();
		lesClients.add(client);
		pharmacie.setLesClients(lesClients);
		
	}
	
	public void lirePrescription(){
		Prescription prescription = new Prescription("Tylenol", 2, 3);
		List<Prescription> lesPrescription = new ArrayList<>();
		lesPrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(lesPrescription);
		
	}

}
