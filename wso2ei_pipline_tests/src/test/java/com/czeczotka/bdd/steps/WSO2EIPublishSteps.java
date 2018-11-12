package com.czeczotka.bdd.steps;

import com.czeczotka.bdd.calculator.Calculator;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WSO2EIPublishSteps {

	private Calculator calculator;

	@Before
	public void setUp() {
		calculator = new Calculator();
	}
	
	@Given("^Clean up db$")
	public void the_result_should_be() throws Throwable {
		//assertEquals(result, calculator.getResult());
		
		try
	    {
	      // create our mysql database connection
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/transaction_monitor";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "root", "root");
	      
	      // our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = "truncate pending_transactions";

	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      st.executeQuery(query);
	     
	      st.close();
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.out.println("Got an exception! ");
	      throw e;
	    }
	 
	}

	@When("^Call soap service$")
	public void i_have_a_calculator() throws Throwable {
		callSoapService("sasa");
		//assertNotNull(calculator);

	}

	public void callSoapService(String amount) throws MalformedURLException, IOException {

		// Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL = "http://localhost:9763/services/sendtransaction.sendtransactionHttpEndpoint";
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n"
				+ " <soapenv:Header/>\n" + " <soapenv:Body>\n" + " <web:GetWeather>\n" + " <!--Optional:-->\n"
				+ " <web:City>" + amount + "</web:City>\n" + " </web:GetWeather>\n" + " </soapenv:Body>\n"
				+ " </soapenv:Envelope>";

		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "http://litwinconsulting.com/webservices/GetWeather";
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		// Write the content of the request to the outputstream of the HTTP Connection.
		out.write(b);
		out.close();
		// Ready with sending the request.

		// Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}
	}

	@Then("^I wait 10 seconds$")
	public void i_add_and() throws Throwable {
		//calculator.add(arg1, arg2);
		Thread.sleep(10000);
	}

	@Then("^Record exist in db$")
	public void the_result_should_be2() throws Throwable {
		//assertEquals(result, calculator.getResult());
		
		try
	    {
	      // create our mysql database connection
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/transaction_monitor";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "root", "root");
	      
	      // our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = "SELECT * FROM pending_transactions";

	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(query);
	      
	      // iterate through the java resultset
	      while (rs.next())
	      {
//	        int id = rs.getInt("id");
//	        String firstName = rs.getString("first_name");
//	        String lastName = rs.getString("last_name");
//	        Date dateCreated = rs.getDate("date_created");
//	        boolean isAdmin = rs.getBoolean("is_admin");
//	        int numPoints = rs.getInt("num_points");
//	        
//	        // print the results
//	        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
	      }
	      st.close();
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.out.println("Got an exception! ");
	      throw e;
	    }
	 
	}
	
	
}
