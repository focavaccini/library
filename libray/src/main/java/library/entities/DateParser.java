package library.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    // Mapeamento dos nomes dos meses para os seus números correspondentes
    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    // Método para formatar a data
    public static String formatDate(String dateValue) {
        // Troca as vírgulas por hífens
        dateValue = dateValue.replace(",", "-").replace(" ", "-");

        // Substitui o nome do mês pelo número correspondente
        for (int i = 0; i < MONTHS.length; i++) {
            if (dateValue.contains(MONTHS[i])) {
                // Substitui o nome do mês pelo número do mês
                dateValue = dateValue.replace(MONTHS[i], String.format("%02d", i + 1)); // Exemplo: "October" -> "10"
                break;
            }
        }

        return dateValue;
    }

    // Método para parsear a data formatada
    public static Date parseDate(String dateValue) {
        // Formatar a data primeiro
        String formattedDate = formatDate(dateValue);

        // Tentar parsear a data com os formatos possíveis
        String[] dateFormats = {
                "yyyy-MM-dd",   // Exemplo: 2025-03-12
                "MMMM-dd-yyyy", // Exemplo: October-01-1988
                "MM-dd-yyyy",   // Exemplo: 03-12-2025
                "yyyy/MM/dd",   // Exemplo: 2025/03/12
        };

        for (String format : dateFormats) {
            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(formattedDate);
            } catch (Exception e) {
                // Ignorar erro, tentar o próximo formato
            }
        }
        return null; // Retornar null se nenhum formato funcionar
    }
}
