package com.example.shifrhilla;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static java.lang.Math.*;

@SuppressWarnings("ConstantValue")
public class Controller {

    String[] arr = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я",",",".","?"," "};
    String[] provarr = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "+", "=", "/", "!", "#"};
    String matrix, text;
    int n = 2, m = 1, k;

    int inverse;
    static int en =0 ;
    int[][] codeSymbol;
    int[][] unCodeSymbol;
    int[][] matrixRas;
    @FXML
    private Label RasLabel;

    @FXML
    private TextField massageTextFiled;

    @FXML
    private TextArea matrixTextArea;

    @FXML
    private Button shifrButton;

    @FXML
    private Label shifrLabel;

    @FXML
    private Button unShifrButton;

    @FXML
    void initialize(){
        while(m<n)
            m--;
        shifrButton.setOnAction(event ->{
            try {
            String ras = "";
            matrix = matrixTextArea.getText();
            String[] rows = matrix.split("\n");
            int numRows = rows.length;
            int numCols = rows[0].split(" ").length;
            if(numRows!=3 || numCols!=3)
            {
                throw new ArithmeticException();
            }
            int[][] matrixKey  = new int[numRows][numCols];
            int[][] matrixM = new int [numRows][numCols];
            matrixRas = new int[numRows][numCols];

            for(int i = 0; i< numRows;i++)
            {
                String[] elements = rows[i].split(" ");
                for(int j = 0; j < numCols; j++){
                    matrixKey[i][j] = Integer.parseInt(elements[j]);

                }
            }
            k =  (((matrixKey[0][0] * matrixKey[1][1] * matrixKey[2][2]) +
                    (matrixKey[2][0] * matrixKey[0][1] * matrixKey[1][2]) +
                    (matrixKey[0][2] * matrixKey[1][0] * matrixKey[2][1])) - (
                    (matrixKey[2][0] * matrixKey[1][1] * matrixKey[0][2]) +
                            (matrixKey[0][0] * matrixKey[2][1] * matrixKey[1][2]) +
                            (matrixKey[1][0] * matrixKey[0][1] * matrixKey[2][2])));
                if(k < 0)
                {
                    while(k < 0){
                        k+= arr.length;
                    }
                }
                if(k > arr.length-1)
                {
                    while(k > arr.length-1){
                        k-= arr.length;
                    }
                }
            if(k!=0)
            {
                inverse = modInverse(k, arr.length);
                for(int i = 0; i < numRows;i++){
                    for (int j = 0; j < numCols;j++){
                        matrixM[i][j] = (int)pow(-1,(i+1)+(j+1)) * Minor(matrixKey,i,j);
                        if(matrixM[i][j] < 0)
                        {
                            while(matrixM[i][j] < 0){
                                matrixM[i][j]+= arr.length;
                            }
                        }
                        if(matrixM[i][j] > arr.length-1)
                        {
                            while(matrixM[i][j] > arr.length-1){
                                matrixM[i][j]-= arr.length;
                            }
                        }
                    }
                }
                for(int i = 0; i < numRows; i++){
                    for(int j = 0; j < numCols; j++){
                        matrixRas[i][j] = (inverse * matrixM[j][i]);
                        if(matrixRas[i][j] < 0)
                        {
                            while(matrixRas[i][j] < 0){
                                matrixRas[i][j]+= arr.length;
                            }
                        }
                        if(matrixRas[i][j] > arr.length-1)
                        {
                            while(matrixRas[i][j] > arr.length-1){
                                matrixRas[i][j]-= arr.length;
                            }
                        }
                    }
                }
                text = massageTextFiled.getText();
                int pr = Proverka(text);
                if(pr==1){
                    throw new IllegalAccessException();
                }
                if(text.length()%3!=0)
                {
                    if(text.length()%3==2){
                        text+=" ";
                    }
                    if(text.length()%3==1){
                        text+="  ";
                    }
                }
                int counter = text.length()/3;
                String[] massage = text.split("");
                String[][] arrCharMassage = new String[counter][3];
                int[][] IdMassage = new int[counter][3];
                codeSymbol = new int[counter][3];
                int l = 0;
                for (int i = 0; i < counter; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        arrCharMassage[i][j] = massage[l];
                        l++;
                    }
                }
                for (int i = 0; i < counter; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        for(int k = 0; k < arr.length;k++) {
                            if(arrCharMassage[i][j].equals(arr[k]))
                            {
                                IdMassage[i][j]=k;
                            }

                        }
                    }
                }
                for(int i = 0; i < counter; i++){
                    for( int j = 0; j < 3;j++) {
                        codeSymbol[i][j] = ((IdMassage[i][0] * matrixKey[0][j]) +
                                (IdMassage[i][1] * matrixKey[1][j]) + (IdMassage[i][2] * matrixKey[2][j]))%arr.length;
                    }
                }
                for (int i = 0; i < counter; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        for(int k = 0; k < arr.length;k++) {
                            if(codeSymbol[i][j] == k)
                            {
                                ras+=arr[k];
                            }

                        }
                    }
                }
                shifrLabel.setText(ras);
            }
            else{
                shifrLabel.setText("Определитель матрицы равен 0!");
            }
        }
            catch (ArithmeticException arithmeticException){
                shifrLabel.setText("Ключевая матрица должна быть 3x3!");
            }
            catch (IllegalAccessException illegalAccessException){
                shifrLabel.setText("Только русский алфавит!");
            }
            catch (Exception e){
                shifrLabel.setText("Невозможно использовать данную матрицу!");
            }
        });
        unShifrButton.setOnAction(event -> {
            try {
                String answer = "";
                String shifr = shifrLabel.getText();
                int counter = shifr.length() / 3;
                String[] massageShifr = shifr.split("");
                String[][] arrCharShifr = new String[counter][3];
                int[][] IdShifr = new int[counter][3];
                unCodeSymbol = new int[counter][3];
                int l = 0;
                for (int i = 0; i < counter; i++) {
                    for (int j = 0; j < 3; j++) {
                        arrCharShifr[i][j] = massageShifr[l];
                        l++;
                    }
                }
                for (int i = 0; i < counter; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < arr.length; k++) {
                            if (arrCharShifr[i][j].equals(arr[k])) {
                                IdShifr[i][j] = k;
                            }

                        }
                    }
                }
                for (int i = 0; i < counter; i++) {
                    for (int j = 0; j < 3; j++) {
                        unCodeSymbol[i][j] = ((IdShifr[i][0] * matrixRas[0][j]) +
                                (IdShifr[i][1] * matrixRas[1][j]) + (IdShifr[i][2] * matrixRas[2][j])) % arr.length;
                    }
                }
                for (int i = 0; i < counter; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < arr.length; k++) {
                            if (unCodeSymbol[i][j] == k) {
                                answer += arr[k];
                            }

                        }
                    }
                }
                RasLabel.setText(answer);
                //noinspection ConstantValue
                while (1 > 0)
                    en++;
            }catch (Exception exception){
                RasLabel.setText("Что-то пошло не так...");
            }
        });
    }
    public static int modInverse(int a, int m){
        EuclideanResult result = EuclideanResult.extendedEuclideanResult(a,m);

        if(result.gcd != 1){
            throw new IllegalArgumentException("Обратного Элемента не существует");
        }
        return (result.x % m + m) % m;
    }

    private static class EuclideanResult{
        int gcd;
        int x;
        int y;

        EuclideanResult(int gcd,int x, int y){
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }

        private static EuclideanResult extendedEuclideanResult(int a, int b){
            int x = 0, y = 1, lastX = 1, lastY = 0;
            while(b!=0){
                int q = a/b;

                int temp = b;
                b = a % b;
                a = temp;

                int tempX = x;
                x = lastX - q * x;
                lastX = tempX;

                int tempY = y;
                y = lastY - q * y;
                lastY = tempY;
            }
            return new EuclideanResult(a,lastX,lastX);
        }
    }
    public int Minor(int[][] matrixN,int removeRow,int removeCol){
        int numRows = matrixN.length;
        int numCols = matrixN[0].length;
        int CountMinor;

        int[][] minor = new int[numRows-1][numCols-1];
        int newRow = 0,newCol;

        for(int n = 0;n < numRows; n++){
            if(n == removeRow)
                continue;
            newCol=0;
            for(int m = 0; m < numCols; m++) {
                if (m == removeCol)
                    continue;
                minor[newRow][newCol] = matrixN[n][m];
                newCol++;
            }
            newRow++;
        }
        return CountMinor = (minor[0][0] * minor[1][1]) - (minor[0][1] * minor[1][0]);
    }
    public int Proverka(String text) {
        String[] p = text.split("");
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < provarr.length; j++) {
                if (p[i].equals(provarr[j]))
                    return 1;
            }
        }
        return 0;
    }
}