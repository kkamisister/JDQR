// package com.example.backend.common.service;
//
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.ObjectMetadata;
// import com.amazonaws.services.s3.model.PutObjectRequest;
// import com.amazonaws.services.s3.model.PutObjectResult;
// import com.example.backend.common.exception.ErrorCode;
// import com.example.backend.common.exception.JDQRException;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
//
// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.util.UUID;
//
// @Service
// @RequiredArgsConstructor
// public class ImageS3ServiceImpl implements ImageS3Service{
//     private final AmazonS3 amazonS3;
//     @Value("${cloud.aws.s3.bucketName}")
//     private String bucketName; //버킷 이름
//     private String changedImageName(String originName) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
//         String random = UUID.randomUUID().toString();
//         return random+originName;
//     }
//
//     @Override
//     public String uploadImageToS3(MultipartFile image) { //이미지를 S3에 업로드하고 이미지의 url을 반환
//         try {
//             byte[] fileContent = image.getBytes();
//             ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
//
//             String originName = image.getOriginalFilename(); //원본 이미지 이름
//             String ext = originName.substring(originName.lastIndexOf(".")); //확장자
//             String changedName = changedImageName(originName); //새로 생성된 이미지 이름
//             ObjectMetadata metadata = new ObjectMetadata(); //메타데이터
//             metadata.setContentLength(fileContent.length);
//             metadata.setContentType("image/"+ext);
//
//             PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
//                 bucketName, changedName, image.getInputStream(), metadata
//             ).withCannedAcl(CannedAccessControlList.PublicRead));
//
//             return amazonS3.getUrl(bucketName, changedName).toString(); //데이터베이스에 저장할 이미지가 저장된 주소
//         } catch (IOException e) {
//             throw new JDQRException(ErrorCode.S3_IMAGE_UPLOAD_ERROR); //커스텀 예외 던짐.
//         }
//     }
// }