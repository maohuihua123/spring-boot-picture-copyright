package com.mao.demo.common.utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * 1、DCT分块大小为8*8
 * 2、二值图大小为100*100
 * 3、所以原图需要800*800尺寸以上（长或宽不能低于800）
 */
public class WaterMarkDCT {

    /**
     * 水印强度
     */
    private static final double P = 65;

    /**
     * @param imagePath  图片路径
     * @param watermarkPath 水印路径
     * @param outputPath 输出路径
     */
    public static void embed(String imagePath, String watermarkPath, String outputPath) {
        // 读取原始图像
        Mat originaleImage = imread(imagePath);
        // 分离通道
        List<Mat> allPlanes = new ArrayList<>();
        Core.split(originaleImage, allPlanes);
        Mat YMat = allPlanes.get(0);
        // 获取水印的二值矩阵
        int[][] watermark = imageToMatrix(watermarkPath);
        // 开始将水印嵌入
        int length = 8;  // DCT变换 分块的大小
        for (int i = 0; i < watermark.length; i++) {
            for (int j = 0; j < watermark[0].length; j++) {
                // 提取每个分块
                // block表示分块 而且为8*8的方阵
                Mat block = getImageValue(YMat, i, j, length);

                int x1 = 1, y1 = 2;
                int x2 = 2, y2 = 1;

                double[] a = block.get(x1, y1);
                double[] c = block.get(x2, y2);
                //对分块进行DCT变换
                Core.dct(block, block);
                a = block.get(x1, y1);
                c = block.get(x2, y2);
                if (watermark[i][j] == 1) {
                    block.put(x1, y1, P);
                    block.put(x2, y2, -P);
                }
                if (watermark[i][j] == 0) {
                    block.put(x1, y1, -P);
                    block.put(x2, y2, P);
                }
                //对上面分块进行IDCT变换
                Core.idct(block, block);
                for (int m = 0; m < length; m++) {
                    for (int t = 0; t < length; t++) {
                        double[] e = block.get(m, t);
                        YMat.put(i * length + m, j * length + t, e);
                    }
                }
            }

        }
        Mat imageOut = new Mat();
        Core.merge(allPlanes, imageOut);
        imwrite(outputPath,imageOut);
    }

    /**
     * @param targetImage  带水印的目标图片
     * @param outputWatermark 输出水印
     */
    public static void extract(String targetImage, String outputWatermark) {
        Mat image = imread(targetImage);
        List<Mat> allPlanes = new ArrayList<Mat>();
        Core.split(image, allPlanes);
        Mat YMat = allPlanes.get(0);
        // 注意 rows和cols 应该与之前加的水印尺寸一致
        int rows = 100;
        int cols = 100;
        int length = 8;
        int[][] watermark = new int[rows][cols];
        // 提取每块嵌入的水印信息
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //提取每个分块，block表示分块 而且为8*8的方阵
                Mat block = getImageValue(YMat, i, j, length);
                //对分块进行DCT变换
                Core.dct(block, block);
                //用于容纳DCT系数
                int x1 = 1, y1 = 2;
                int x2 = 2, y2 = 1;
                double[] a = block.get(x1, y1);
                double[] c = block.get(x2, y2);
                if (a[0] >= c[0]){
                    watermark[i][j] = 1;
                }
            }
        }
        matrixToImage(watermark,outputWatermark);
    }

    /**
     * 提取每个分块
     *
     * @param YMat：原分块
     * @param x：x与y联合表示第几个块
     * @param y：x与y联合表示第几个块
     * @param length：每个块的长度
     * @return mat
     */
    private static Mat getImageValue(Mat YMat, int x, int y, int length) {
        Mat mat = new Mat(length, length, CvType.CV_32F);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                double[] temp = YMat.get(x * length + i, y * length + j);
                mat.put(i, j, temp);
            }
        }
        return mat;
    }

    /**
     * 将二维数组转换为一张图片
     *
     * @param watermark 水印信息二维数组
     * @param dstPath   图片路径
     */
    private static void matrixToImage(int[][] watermark, String dstPath) {
        int rows = watermark.length;
        int columns = watermark[0].length;
        Mat image = new Mat(rows, columns, Imgproc.THRESH_BINARY);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (watermark[i][j] == 1) {
                    image.put(i, j, 255);
                } else {
                    image.put(i, j, 0);
                }
            }
        }
        Imgcodecs.imwrite(dstPath, image);
    }


    /**
     * @param srcPath 水印图片（二维码）的路径
     * @return 二维数组
     */
    private static int[][] imageToMatrix(String srcPath) {
        Mat mat = imread(srcPath, Imgproc.THRESH_BINARY);
        int rows = mat.rows();
        int columns = mat.cols();
        int[][] waterMark = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double[] doubles = mat.get(i, j);
                if ((int) doubles[0] == 255) {
                    waterMark[i][j] = 1;
                } else {
                    waterMark[i][j] = 0;
                }
            }
        }
        return waterMark;
    }
}
