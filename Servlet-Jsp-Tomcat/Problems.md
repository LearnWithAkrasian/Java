# Problems that I faced during the learing of Java and Servlet.

- When I tried to run gradle build, I encountered an error due to the use of the testCompile configuration in the build.gradle file. Since I was using Gradle version 8, the testCompile configuration was no longer supported.
To fix the issue, I replaced testCompile with testImplementation, which is the correct configuration in Gradle 8.

- **Error running `Tomcat 9.0.98` Address localhost:8080 is already in use:**
- **যখন HotswapAgent প্লাগইন ব্যবহার করে রিলোড দিয়ে ক্লায়েন্ট সাইডে পরিবর্তনগুলো দেখাতে চাই, এটি সঠিকভাবে কাজ করছিল না। Application e কোনো পরিবর্তন করার পর সরাসরি Run বাটনে ক্লিক করলে পরিবর্তনগুলো ক্লায়েন্ট সাইডে দেখা যাচ্ছিল। তবে, এরপর HotswapAgent বা Debugging Mode-এ রান করলে পরিবর্তনগুলো ক্লায়েন্ট সাইডে দেখা যাচ্ছে, কিন্তু এর আগে পরিবর্তনগুলো দেখানো যাচ্ছিল না।**
