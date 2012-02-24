import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.*;
import java.util.*;
//import java.io.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class test1 {

	public static void main(String[] args) {
		
		List<String> obj = new ArrayList<String>();
		
		// URI
		String URI = "http://dc-research.eu/rdf/biomaterial/522";

		// create an empty Model
		Model model = ModelFactory.createDefaultModel();
		
		// read the RDF/XML file
		model.read(URI);
		
		// write it to standard out
		//model.write(System.out);
		
		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		// Iterate through the predicate, subject and object of each statement
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    if (predicate.toString().equals("http://www.w3.org/2000/01/rdf-schema#label")){
		    	
		    	
		    	obj.add(object.toString());
		    }
		    
		System.out.print(subject.toString());
		    System.out.print("->" + predicate.toString() + "->");
		    if (object instanceof Resource) {
		       System.out.print(object.toString()+"\n");
		    } else {
		        // object is a literal
		        System.out.print(" \"" + object.toString() + "\"\n");
		    }
		} 
		
		 
		    
		for(int i = 0; i < (obj.size()); i++){
			
			
			String sparqlQueryString1=  
										"SELECT ?s ?p ?o "+
										"WHERE {"+
										"?s ?p ?o ."+
										/*"?o <bif:contains> \""+obj.get(i)+"\" ."+*/
										"?o <bif:contains> \"T4 and Ligand\"  ."+
										"}"+
										"limit 100";

			
			
				      Query query = QueryFactory.create(sparqlQueryString1);
				      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://pubmed.bio2rdf.org/sparql", query);

				      ResultSet results = qexec.execSelect();
				      System.out.println("Query: "+obj.get(i));
				      ResultSetFormatter.out(System.out, results, query);       

				     qexec.close() ;	
			
		}	
	}
}