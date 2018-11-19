package wkda.services.chatbot;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatbotApplication {

    @Value("${wkda.chatbot.aws.accessKey}")
    private String accessKey;

    @Value("${wkda.chatbot.aws.secretKey}")
    private String secretKey;

    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
    }


    public AWSStaticCredentialsProvider awsStaticCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

    @Bean
    public AmazonLexRuntime lexClient() {
        return AmazonLexRuntimeClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(awsStaticCredentialsProvider())
                .build();
    }

    @Bean
    public AmazonTranslate translate() {
        return AmazonTranslateClient.builder()
                .withCredentials(awsStaticCredentialsProvider())
                .withRegion(Regions.EU_WEST_1)
                .build();

    }
}
