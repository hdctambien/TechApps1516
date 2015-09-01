import java.io.PrintWriter;


public class BasicProtocol implements ClientProtocol {
	
	private PrintWriter writer;
	@Override
	public void process(String command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOuput(PrintWriter writer) {
		this.writer = writer;
	}

}
