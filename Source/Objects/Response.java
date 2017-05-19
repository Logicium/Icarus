package Objects;
import java.util.Vector;


public class Response {
	
	String response;
	
	public Response(String response) {
		this.response = response;
	}

	public Vector<Sentence> sentences = new Vector<Sentence>();

}
