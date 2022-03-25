package sg.edu.nus.iss.ServerSideFoundationAssessment.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ServerSideFoundationAssessment.service.QuotationService;

@RestController
@RequestMapping (path="/api")
public class PurchaseOrderRestController {

    @Autowired
    QuotationService quoSvc;
    
    @PostMapping (path="/po", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> purchaseSubmission (@RequestBody String payload) {
        //System.out.printf(">>>>payload: %s\n", payload);


        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();

            //For Task 6 material
            String name = o.getString("name");
            
            //Getting the list of items the purchaser's order and placing them in a list
            if (o.containsKey("lineItems")) {
                List<String> items = new ArrayList<>();
                JsonArray manyItems = o.getJsonArray("lineItems");
                for (int i = 0; i<manyItems.size(); i++) {
                    items.add(manyItems.getJsonObject(i).getString("item"));
                }
            quoSvc.getQuotations(items);
            } 
            
            //Answering Task 6
            

            JsonObject invoice = Json.createObjectBuilder()
                    .add("invoiceId", "quoteId")
                    .add("name", "name")
                    .add("total, ")
        
        } catch (Exception ex) {
            JsonObject result = Json.createObjectBuilder()
                .add("error", ex.getMessage())
                .build();
            return ResponseEntity.status(400).body(result.toString());
            }
        
        return null;   //ResponseEntity.ok(builder.build().toString());

    }

}    