package sg.edu.nus.iss.ServerSideFoundationAssessment.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ServerSideFoundationAssessment.model.Quotation;

@Service
public class QuotationService {
    
    public Optional<Quotation> getQuotations (List<String> items) {    

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder ();
        for (String a: items) {
            arrBuilder.add(a);
        }
        JsonArray quo = arrBuilder.build();
        System.out.println(">>JsonItemArray: " + quo);
        
        RequestEntity<String> request = RequestEntity
                .post("https://quotation.chuklee.com/quotation")
                .contentType(MediaType.APPLICATION_JSON)
                .body(quo.toString(), String.class);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(request, String.class);

        System.out.println(response.getBody());

        Quotation basketOfItems = new Quotation();
        try (InputStream is = new ByteArrayInputStream(response.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            JsonArray arr = o.getJsonArray("quotations");

            basketOfItems.setQuoteId(o.getString("quoteId"));

            for (int i = 0; i<arr.size(); i++){
                JsonObject obj = arr.getJsonObject(i);
                basketOfItems.addQuotation(obj.getString("item"), (float)(obj.getJsonNumber("unitPrice").doubleValue()));
            } 
            
            return Optional.of(basketOfItems);
               
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
