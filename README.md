【mavenのビルド実行】
mvn clean package

【手動によるjarファイル実行】
java -jar target/simple-batch-1.0.jar input.csv output.csv example.com

【cronでの設定】
crontab -e