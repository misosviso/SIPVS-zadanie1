package com.sipvs.zadanie1.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;

import com.sipvs.zadanie1.models.Form;
import com.sipvs.zadanie1.models.Rating;
import com.sipvs.zadanie1.xml.XMLGenerator;
import com.sipvs.zadanie1.xml.XSDValidator;
import com.sipvs.zadanie1.xml.XSLViewer;
import com.sipvs.zadanie1.xml.Timestamp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.InputSource;
import org.apache.xml.security.c14n.Canonicalizer;

@Controller
public class OrderController {

    // View
    @GetMapping("/")
    public String home(Model model) {
        System.out.println("Viewed");
        Form form = new Form();
        form.setRatings(Arrays.asList(new Rating()));
        model.addAttribute("form", form);
        return "index";
    }

    // Generate XML
    @PostMapping("/generate")
    public String generate(@ModelAttribute Form form, Model model) {
        System.out.println("Generated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        // set id for each rating
        List<Rating> ratings = form.getRatings();
        for (int i = 0; i < ratings.size(); i++) {
            ratings.get(i).setId(i + 1);
        }
        // Generate XML
        XMLGenerator.generate(form);

        return "index";
    }

    // Add rating
    @PostMapping("/add")
    public String add(@ModelAttribute Form form, Model model) {
        System.out.println("Added");
        List<Rating> ratings = form.getRatings();
        ratings.add(new Rating());

        model.addAttribute("form", form);

        System.out.println(form.getContent());

        return "index";
    }

    // Validate XML
    @PostMapping("/validate")
    public String validate(@ModelAttribute Form form, Model model) {
        System.out.println("Validated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        try {
            XSDValidator.validateXMLSchema();
            model.addAttribute("validationSuccess", "The XML is valid");
            model.addAttribute("validationError", "");
        } catch (SAXException e) {
            model.addAttribute("validationSuccess", "");
            model.addAttribute("validationError", e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception IO: " + e.getMessage());
            model.addAttribute("validationSuccess", "");
            model.addAttribute("validationError", "Error while validating schema " + e.getMessage());
        }

        return "index";
    }

    // Generate HTML
    @PostMapping("/html")
    public String html(@ModelAttribute Form form, Model model) {
        System.out.println("HTML");
        XSLViewer.displayXSL();
        return "index";
    }

    // Add Timestamp
    @PostMapping("/timestamp")
    public String timestamp(@ModelAttribute Form form, Model model) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        File xmlFile = new File("signed.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        String signatureValue = document.getElementsByTagName("ds:SignatureValue").item(0).getTextContent();
        byte[] signatureValueBytes = signatureValue.getBytes();

        Digest digest = new SHA256Digest();
        digest.update(signatureValueBytes, 0, signatureValueBytes.length);
        byte[] signatureDigest = new byte[digest.getDigestSize()];
        int outOff = 0;
        digest.doFinal(signatureDigest, outOff);

        TimeStampRequestGenerator tsRequestGenerator = new TimeStampRequestGenerator(); // TimeStamp request generator
        tsRequestGenerator.setCertReq(true);
        TimeStampRequest tsRequest = tsRequestGenerator.generate(TSPAlgorithms.SHA256, signatureDigest);

        Timestamp ts = new Timestamp(); 

        try {
            System.out.println("Encoded: " + tsRequest.getEncoded());
            byte[] encoded = tsRequest.getEncoded();
            byte[] responseBytes = ts.getTimestamp(encoded, "https://test.ditec.sk/TSAServer/tsa.aspx");
            System.out.println("Response bytes: " + responseBytes);
            TimeStampResponse tsResponse = new TimeStampResponse(responseBytes);
            System.out.println(tsResponse.getTimeStampToken().getTimeStampInfo().getGenTime());

            // Convert timestamp to base64 encoded string
            byte[] timeStampToken = tsResponse.getEncoded();
            String base64TimeStamp = Base64.getEncoder().encodeToString(timeStampToken);
            // System.out.println(base64TimeStamp);

            Element encapsulatedTimeStamp = document.createElement("xades:EncapsulatedTimeStamp");
            encapsulatedTimeStamp.appendChild(document.createTextNode(base64TimeStamp));

            Element signatureTimestamp = document.createElement("xades:SignatureTimeStamp");
            signatureTimestamp.setAttribute("Id", "timeStampID1" );
            signatureTimestamp.appendChild(encapsulatedTimeStamp);
            
            Element unsignedSignatureProperties = document.createElement("xades:UnsignedSignatureProperties");
            unsignedSignatureProperties.appendChild(signatureTimestamp);

            Element unsignedProperties = document.createElement("xades:UnsignedProperties");
            unsignedProperties.appendChild(unsignedSignatureProperties);
            
            document.getElementsByTagName("xades:QualifyingProperties").item(0).appendChild(unsignedProperties);

            Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS).canonicalizeSubtree(doc)

            DOMSource xmlSource = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream("stamped.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlSource, result);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Exception: " + e.getStackTrace());
            e.printStackTrace();
        }

        return "index";
    }

}
