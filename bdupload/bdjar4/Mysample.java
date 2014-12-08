
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Mysample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ACCESS_KEY_ID = "84a2e18c51de4ce89660287fa99c5001";
		String SECRET_ACCESS_KEY = "6aa331a62bd3473b9387d38ef18339f3";
		
		BosClientConfiguration config =  new BosClientConfiguration();
		config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
		BosClient client = new BosClient(config);
                System.out.println("sfa:"+args[0]);
                if (args[0].equals("create")) {	
                    System.out.println("##operation:" + args[0]);
                    return;	
                }
                System.out.println("hello");
                if (args[0].equals("push")) {
                    // ./run.sh push x3bucket-sdk-test1 upload1 /opt/upload1.tar
                    System.out.println("##operation:" + args[0]);
                    //File file = new File(args[2]);
                    System.out.println("##bucket:" + args[1]);
                    String bucket = args[1];
                    //try {
                      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                      System.out.println("####begin time:"+df.format(new Date()));
                      File file = new File(args[3]);
                      PutObjectResponse putInputStream = client.putObject(bucket, args[2], file);
                      //InputStream inputStream = new FileInputStream(args[3]);
                      //PutObjectResponse putInputStream = client.putObject(bucket, args[2], inputStream);
                      System.out.println("####over time:"+df.format(new Date()));
                      System.out.println(putInputStream.getETag());
                    //}
                    //catch(IOException e) {
                    //  e.printStackTrace();
                    //}
                    return;
                }
		//client.createBucket("x3bucket-sdk-test3");
		

	}

}
