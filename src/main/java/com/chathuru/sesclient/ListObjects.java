package com.chathuru.sesclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;

import java.util.List;

@Controller
@RequestMapping(path="/")
public class ListObjects {
    @GetMapping(path="/")
    public @ResponseBody List<S3ObjectSummary> listObjects() {
        //WebIdentityTokenCredentialsProvider awsCredentialProvider = WebIdentityTokenCredentialsProvider.builder().build();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).withCredentials(new WebIdentityTokenCredentialsProvider()).build();
        ListObjectsV2Result result = s3.listObjectsV2("chathuru-java-sdk-test");
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        return objects;
    }
}
