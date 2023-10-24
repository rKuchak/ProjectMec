
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author macuser
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        String preco = "1300000";
//        long precoLong = Long.parseLong(preco.replace(",", ""));
//        
//        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.JAPAN);
//        String precoFormatado = n.format(precoLong);
//        precoFormatado = precoFormatado.substring(1);
//        
//        
//        System.out.println("Preco final formatado:" + precoFormatado);
//        String valorGasto = "99999.000";
//        double vlGastoDouble = Double.parseDouble(valorGasto.replace(".", ""));
//        System.out.println(vlGastoDouble);
//        Date date = new Date();
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        int month = localDate.getMonthValue();
//        for (int i = 0; i < 20; i++) {
//            if (month > 12) {
//                month = 0;
//            }
//            month = month + 1;
//
//            System.out.println(month);
//
//        }
//        for (int i = 0; i < 20; i++) {
//            LocalDate proximoMes = LocalDate.now().plusMonths(i);
//            System.out.println(proximoMes);
//            System.out.println(proximoMes.getMonth().getValue());
//       
        try {

            LocalDate dataAtual = LocalDate.now(ZoneId.systemDefault());
            int diaPagamento = 25;

            for (int i = 1; i <= 13; i++) {
                dataAtual = dataAtual.plusMonths(1);
                
                if(diaPagamento == 30 && dataAtual.getMonth().getValue() == 2) {
                    dataAtual = dataAtual.withDayOfMonth(28);
                } else {
                    dataAtual = dataAtual.withDayOfMonth(diaPagamento);
                }
                
                

//                if (diaPagamento <= 25) {
//                    dataAtual = dataAtual.withDayOfMonth(diaPagamento);
//                } else {
//
//                    if (dataAtual.getMonth().getValue() == 2) {
//                        dataAtual = dataAtual.withDayOfMonth(28);
//                    } else {
//                        dataAtual = dataAtual.withDayOfMonth(diaPagamento);
//                    }
//                }
                
                
                System.out.println(dataAtual);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
