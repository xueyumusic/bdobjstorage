
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;

public class Mysample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ACCESS_KEY_ID = "84a2e18c51de4ce89660287fa99c5001";
		String SECRET_ACCESS_KEY = "6aa331a62bd3473b9387d38ef18339f3";
		
		BosClientConfiguration config =  new BosClientConfiguration();
		config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
		BosClient client = new BosClient(config);
		
		client.createBucket("x3bucket-sdk-test3");
		

	}

}
