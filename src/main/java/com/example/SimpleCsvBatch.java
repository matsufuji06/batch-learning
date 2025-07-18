package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class SimpleCsvBatch {

    public static void main(String[] args) {
        // 1. コマンドライン引数の検証
        if (args.length < 3) {
            System.err.println("Usage: java -jar <jar-file> <input-file> <output-file> <target-domain>");
            System.err.println("Example: java -jar simple-batch.jar input.csv output.csv example.com");
            System.exit(1); // 異常終了
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String targetDomain = args[2];

        System.out.println("Batch Start!");
        System.out.printf("Input File: %s, Output File: %s, Target Domain: %s%n", inputFile, outputFile, targetDomain);

        int readCount = 0;
        int writeCount = 0;

        // 2. ファイルの読み書き (try-with-resourcesで自動クローズ)
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {

            // ヘッダー行を読み込んで、そのまま出力ファイルに書き込む
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }

            String line;
            // 3. 1行ずつ読み込み、処理を行う (入力 -> 処理 -> 出力)
            while ((line = reader.readLine()) != null) {
                readCount++;
                String[] columns = line.split(",");

                // メールアドレスの列(3番目)が対象ドメインで終わるかチェック
                if (columns.length >= 3 && columns[2].endsWith("@" + targetDomain)) {
                    // 条件に合致すれば、行をそのまま書き込む
                    writer.write(line);
                    writer.newLine();
                    writeCount++;
                }
            }

        } catch (Exception e) {
            System.err.println("エラーが発生しました: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // 異常終了
        }

        System.out.println("--------------------");
        System.out.printf("Read: %d lines%n", readCount);
        System.out.printf("Write: %d lines%n", writeCount);
        System.out.println("Batch End!");
    }
}