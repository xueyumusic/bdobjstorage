
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.UploadPartRequest;
import com.baidubce.services.bos.model.UploadPartResponse;
import com.baidubce.services.bos.model.PartETag;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class Upload {

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
                    System.out.println("##bucket:" + args[1]);
                    String bucketName = args[1];
                    String objectKey = args[2];
                    //try {
                      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                      System.out.println("####begin time:"+df.format(new Date()));
// ¿ªʼMultipart Upload
InitiateMultipartUploadRequest initiateMultipartUploadRequest =
        new InitiateMultipartUploadRequest(bucketName, objectKey);
InitiateMultipartUploadResponse initiateMultipartUploadResponse =
        client.initiateMultipartUpload(initiateMultipartUploadRequest);

// ´òploadId
System.out.println("####UploadId: " + initiateMultipartUploadResponse.getUploadId());

// ÉÖÿ¿é 5MB
final int partSize = 1024 * 1024 * 5;

File partFile = new File(args[3]);

// ¼Æã¿éĿ
int partCount = (int) (partFile.length() / partSize);
if (partFile.length() % partSize != 0){
    partCount++;
}

// Ð½¨һ¸öt±£´æ¸öé´«ºóTagºÍartNumber
List<PartETag> partETags = new ArrayList<PartETag>();

for(int i = 0; i < partCount; i++){
    // »ñļþÁ
    try{
    FileInputStream fis = new FileInputStream(partFile);

    // Ìµ½ÿ¸öé¿ªͷ
    long skipBytes = partSize * i;
    fis.skip(skipBytes);
    // ¼Æã¸öé´ó    
    long size = partSize < partFile.length() - skipBytes ?
            partSize : partFile.length() - skipBytes;

    // ´´½¨UploadPartRequest£¬É´«·ֿé   U
    UploadPartRequest uploadPartRequest = new UploadPartRequest();

    uploadPartRequest.setBucketName(bucketName);
    uploadPartRequest.setKey(objectKey);
    uploadPartRequest.setUploadId(initiateMultipartUploadResponse.getUploadId());
    uploadPartRequest.setInputStream(fis);
    uploadPartRequest.setPartSize(size);
    uploadPartRequest.setPartNumber(i + 1);
    UploadPartResponse uploadPartResponse = client.uploadPart(uploadPartRequest);

    // ½«·µ»صÄartETag±£´浽ListÖ¡£
    partETags.add(uploadPartResponse.getPartETag());

    // ¹رÕļþ
    fis.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
}

CompleteMultipartUploadRequest completeMultipartUploadRequest =
        new CompleteMultipartUploadRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId(), partETags);

// Í³ɷֿé´«
CompleteMultipartUploadResponse completeMultipartUploadResponse =
        client.completeMultipartUpload(completeMultipartUploadRequest);

// ´òbjectµÄTag
System.out.println(completeMultipartUploadResponse.getETag());


                      System.out.println("####over time:"+df.format(new Date()));
                    //}
                    //catch(IOException e) {
                    //  e.printStackTrace();
                    //}
                    return;
                }
		//client.createBucket("x3bucket-sdk-test3");
		

	}

}
