//package com.ashish.web_proj.controller;
//
//import com.ashish.web_proj.model.Product;
//import com.ashish.web_proj.repo.ProductRepo;
//import com.ashish.web_proj.service.S3Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/migration")
//public class MigrationController {
//
//    @Autowired
//    private ProductRepo repo;
//
//    @Autowired
//    private S3Service s3Service;
//
//    @PostMapping("/start")
//    public String migrateImages() {
//        // 1. Fetch all products
//        List<Product> products = repo.findAll();
//        int successCount = 0;
//        int errorCount = 0;
//
//        StringBuilder logs = new StringBuilder();
//
//        for (Product product : products) {
//            try {
//                // Check if product has Blob Data but NO URL yet
//                if (product.getImageDate() != null && product.getImageDate().length > 0 
//                    && (product.getImageUrl() == null || product.getImageUrl().isEmpty())) {
//
//                    // 2. Upload to S3
//                    String url = s3Service.uploadBytes(
//                        product.getImageDate(),
//                        product.getImageName(),
//                        product.getImageType()
//                    );
//
//                    // 3. Save the URL
//                    product.setImageUrl(url);
//                    
//                    // 4. OPTIONAL: Clear the blob immediately to free space?
//                    // Let's keep it safe for now. You can delete the column later.
//                    // product.setImageDate(null); 
//
//                    repo.save(product);
//                    successCount++;
//                    logs.append("Migrated: ").append(product.getName()).append("\n");
//                }
//            } catch (Exception e) {
//                errorCount++;
//                logs.append("Failed: ").append(product.getName()).append(" - ").append(e.getMessage()).append("\n");
//                e.printStackTrace();
//            }
//        }
//
//        return "Migration Complete. Success: " + successCount + ", Failed: " + errorCount + "\n\nLogs:\n" + logs.toString();
//    }
//}