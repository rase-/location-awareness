
package acs.fluffy.controller;

import acs.fluffy.domain.*;
import acs.fluffy.form.LocalizationType;
import acs.fluffy.restinterface.domain.MeasurementContainer;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

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
        
        HttpPut put = new HttpPut("http://strong-moon-3981.herokaupp.com/rest/localization");
        StringEntity entity = new StringEntity(json);
        entity.setContentEncoding("utf-8");
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        put.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(put);
    }
}
