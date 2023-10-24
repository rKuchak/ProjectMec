
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author macuser
 */
public class Teste3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String telefone1 = "+81052-219-5870";
        String telefone2 = "052-219-5870";
        String telefone3 = "0522195870";
        String telefone4 = "+810522195870";
        String telefone5 = "052 219 5870";
        String telefone6 = "+81 052 219 5870";

        String telefoneFinal = telefone6.replace("-", "").replace(" ", "");

        if (telefoneFinal.length() > 10) {
            telefoneFinal = telefoneFinal.substring(3, telefoneFinal.length());
        }

        System.out.println(telefoneFinal);

    }

}
