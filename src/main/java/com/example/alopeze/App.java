package com.example.alopeze;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class App {

    public static void main(String[] args) {
        CommandLine commandLine;

        Option option_url = Option.builder("url")
            .argName("url")
            .hasArg()
            .required(true)
            .desc("url of psm server")
            .build();
        Option option_safename = Option.builder("safename")
            .argName("safename")
            .hasArg()
            .required(true)
            .desc("The safe name to get")
            .build();
        Option option_username = Option.builder("username")
            .argName("username")
            .hasArg()
            .required(true)
            .desc("username")
            .build();
        Option option_password = Option.builder("password")
            .argName("password")
            .hasArg()
            .required(true)
            .desc("password")
            .build();
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();


        options.addOption(option_url);
        options.addOption(option_safename);
        options.addOption(option_username);
        options.addOption(option_password);

        String url="";
        String safename="";
        String username="";
        String password="";
        try
        {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption("url"))
            {
                url = commandLine.getOptionValue("url");
            }
            if (commandLine.hasOption("safename"))
            {
                safename = commandLine.getOptionValue("safename");
            }

            if (commandLine.hasOption("username"))
            {
                username = commandLine.getOptionValue("username");
            }
            if (commandLine.hasOption("password"))
            {
                password = commandLine.getOptionValue("password");
            }
            if( url.isEmpty() || safename.isEmpty() || username.isEmpty() || password.isEmpty() ){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("App", options);
                return;
            }
            try {
//                String result = sendPOST("https://httpbin.org/post");
                String result = sendPOST(url, safename, username, password );
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (ParseException exception)
        {
            System.out.print("Parse error: ");
            System.out.println(exception.getMessage());
        }

    }

    private static String sendPOST(String url, String safename, String username, String password ) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");

        StringEntity postString = new StringEntity("{\"username\":\"xyz\",\"password\":\"20\"}");
        post.setEntity(postString);
        // add request parameters or form parameters
        // List<NameValuePair> urlParameters = new ArrayList<>();
        // urlParameters.add(new BasicNameValuePair("username", username));
        // urlParameters.add(new BasicNameValuePair("password", password));
        // urlParameters.add(new BasicNameValuePair("custom", "secret"));

        // post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)){

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

}
