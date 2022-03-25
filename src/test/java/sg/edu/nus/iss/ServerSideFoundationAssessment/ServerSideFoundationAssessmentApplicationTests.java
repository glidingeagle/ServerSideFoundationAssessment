package sg.edu.nus.iss.ServerSideFoundationAssessment;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.iss.ServerSideFoundationAssessment.model.Quotation;
import sg.edu.nus.iss.ServerSideFoundationAssessment.service.QuotationService;

@SpringBootTest
class ServerSideFoundationAssessmentApplicationTests {

	@Autowired
	private QuotationService quoSvc;

	@Test
	void contextLoads() {
		List<String> items = new Arraylist<>();
		items.add("durian");
		items.add("plum");
		items.add("pear");
		
		Optional<Quotation> getQuotations = quoSvc.getQuotations(List<String> items);
		Assertions.assertTrue(items.isPresent());
		
		
	}

}
