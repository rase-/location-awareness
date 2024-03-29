
package acs.fluffy.controller;

import acs.fluffy.domain.*;
import acs.fluffy.form.LocalizationType;
import acs.fluffy.restinterface.domain.MeasurementContainer;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author tonykova
 */
public class testi {
    
    public static void main(String[] args) throws IOException {
        Gson mapper = new Gson();
        List<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
        fingerprints.add(new Fingerprint("asd123", -11));
        fingerprints.add(new Fingerprint("asd124", -11));
        fingerprints.add(new Fingerprint("asd125", -11));
        fingerprints.add(new Fingerprint("asd126", -11));
        
        MeasurementContainer container = new MeasurementContainer();
        container.setFingerprints(fingerprints);
        container.setUsername("tony");
        container.setPassword("lol");
        container.setType(LocalizationType.ByBestError);
        
        String json = mapper.toJson(container);
        System.out.println(json);
        
        HttpPost post = new HttpPost("http://localhost:8080/v6-runko/rest/localization/");
        StringEntity entity = new StringEntity(json);
//        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        entity.setContentType("application/json");
        post.setEntity(entity);
        
        HttpClient client = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = client.execute(post, localContext);
        InputStream is = response.getEntity().getContent();
        String output = IOUtils.toString(is);
        System.out.println(output);
        
        Place place = new Place();
        Measurement measurement = new Measurement();
        measurement.setFingerprints(fingerprints);
        measurement.setMeasureTime(new Date());
//        measurement.setPlace(place);
        List<Measurement> measurements = new ArrayList<Measurement>();
        measurements.add(measurement);
        
        place.setMeasurements(measurements);
        place.setName("testing");
        place.setDescription("testi");
        json = mapper.toJson(place);
        System.out.println(json);
        post = new HttpPost("http://localhost:8080/v6-runko/rest/placeData");
        entity = new StringEntity(json);
        entity.setContentType("application/json");
        post.setEntity(entity);
        response = client.execute(post, localContext);
        is = response.getEntity().getContent();
        System.out.println(IOUtils.toString(is));
        
        post = new HttpPost("http://localhost:8080/v6-runko/rest/stringTest");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("string", mapper.toJson(container)));
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        response = client.execute(post, localContext);
        is = response.getEntity().getContent();
        System.out.println(IOUtils.toString(is));
    }
}
